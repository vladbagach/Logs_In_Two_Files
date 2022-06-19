package com.example.wamitest.controller;

import com.example.wamitest.model.Singular;
import com.example.wamitest.service.SingularServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Value
@AllArgsConstructor
@RestController
@RequestMapping("/api/")
public class SingularController {
    SingularServiceImpl service;

    @RequestMapping("/users")
    public List<Singular> getUsersWithTheLeastTime(){
        return service.getUsersWithTheLeastTime();
    }
}
