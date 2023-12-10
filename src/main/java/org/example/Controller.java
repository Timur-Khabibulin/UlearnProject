package org.example;

import com.vk.api.sdk.objects.users.responses.GetResponse;
import org.example.parser.Parser;
import org.example.db.repositories.StudentRepository;
import org.example.vk.VKService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class Controller {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private Parser parser;
    @Autowired
    private VKService vkService;

    @GetMapping
    public void doWork() {
        System.out.println("Start parsing");
        parser.parse("basicprogramming.csv");
        System.out.println("End parsing");
        System.out.println("Start saving");
        studentRepository.saveAll(parser.getStudents());
        System.out.println("End saving");
        System.out.println("Start vk");
        getDataFromVkAndSave();
        System.out.println("End vk");
    }

    private void getDataFromVkAndSave() {
        vkService.getGroupMembers("basicprogrammingrtf2023")
                .ifSuccess(users -> vkService.getUsers(users)
                        .ifSuccess(this::saveUsersDataFromVk)
                        .ifFailure(e -> System.out.println(e.getMessage())))
                .ifFailure(e ->
                        System.out.println(e.getMessage())
                );
    }

    private void saveUsersDataFromVk(List<GetResponse> users) {
        users.forEach(user -> studentRepository
                .findBySurnameAndName(
                        user.getLastName(),
                        user.getFirstName()
                ).forEach(student -> {
                    var country = user.getCountry();
                    var city = user.getCity();
                    student.setSex(user.getSex());
                    student.setCountry(country == null ? null : country.getTitle());
                    student.setCity(city == null ? null : city.getTitle());
                    studentRepository.save(student);
                }));
    }
}
