package com.example.blog.service;

import com.example.blog.dto.StringResponse;
import com.example.blog.exception.InvalidStringException;
import org.springframework.stereotype.Service;

@Service
public class StringService {

    public StringResponse repeatString(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidStringException("잘못된 요청입니다. 문자열을 입력해주세요.");
        }
        return new StringResponse(value, value);
    }
}
