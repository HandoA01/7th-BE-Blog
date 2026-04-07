package com.example.blog.controller;

import com.example.blog.dto.StringRequest;
import com.example.blog.dto.StringResponse;
import com.example.blog.service.StringService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/string")
public class StringController {

    private final StringService stringService;

    public StringController(StringService stringService) {
        this.stringService = stringService;
    }

    @PostMapping("/repeat")
    public StringResponse repeatString(@RequestBody StringRequest request) {
        return stringService.repeatString(request.getValue());
    }
}
