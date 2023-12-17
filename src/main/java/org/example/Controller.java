package org.example;

import org.example.charts.ChartService;
import org.example.parser.ParserService;
import org.example.vk.VkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class Controller implements Runnable {

    @Autowired
    private ParserService parserService;
    @Autowired
    private VkService vkService;
    @Autowired
    private ChartService chartService;

    @Override
    public void run() {
        parserService.run();
        vkService.run();
        chartService.run();
    }
}
