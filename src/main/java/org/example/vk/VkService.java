package org.example.vk;

import com.vk.api.sdk.objects.users.responses.GetResponse;
import org.example.db.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VkService implements Runnable {
    @Autowired
    private VkApi vkApi;
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public void run() {
        System.out.println("Start vk");
        getDataFromVkAndSave();
        System.out.println("End vk");
    }

    private void getDataFromVkAndSave() {
        vkApi.getGroupMembers("basicprogrammingrtf2023")
                .onSuccess(users -> vkApi.getUsers(users)
                        .onSuccess(this::saveUsersDataFromVk)
                        .onFailure(e -> System.out.println(e.getMessage()))
                ).onFailure(e ->
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
                    var schools = user.getSchools();
                    student.setSex(user.getSex());
                    student.setCountry(country == null ? null : country.getTitle());
                    student.setCity(city == null ? null : city.getTitle());
                    student.setSchool(schools == null || schools.isEmpty() ? null : schools.get(0).getName());
                    studentRepository.save(student);
                }));
    }
}
