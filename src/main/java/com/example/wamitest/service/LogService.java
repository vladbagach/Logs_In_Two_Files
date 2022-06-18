package com.example.wamitest.service;

import com.example.wamitest.dto.LogDto;

import java.io.IOException;
import java.util.List;


public interface LogService{

    List<LogDto> findLogs() throws IOException;
}
