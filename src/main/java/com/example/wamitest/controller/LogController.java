package com.example.wamitest.controller;

import com.example.wamitest.dto.LogDto;
import com.example.wamitest.service.impl.LogServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class LogController {
    private final LogServiceImpl service;

    public LogController(LogServiceImpl service) {
        this.service = service;
    }

    @RequestMapping("/result1")
    public List<LogDto> usersWithTheLeastTimeSpent(){
        return service.usersWithTheLeastTimeSpent();
    }

    @RequestMapping("/result2")
    public List<LogDto> findAllStartLogs(){
        return service.findStartLogs();
    }

    @RequestMapping("/result3")
    public List<LogDto> findAllFinishLogs(){
        return service.findFinishLogs();
    }

    @RequestMapping("/result4")
    public List<String> allCommonIdOfTwoFiles(){
        return service.commonIdOfTwoFiles();
    }

    @RequestMapping("/result5")
    public List<LogDto> allCommonIdOfTwoFile(){
        return service.newListEqualInSizeToFinishLog();
    }
}
