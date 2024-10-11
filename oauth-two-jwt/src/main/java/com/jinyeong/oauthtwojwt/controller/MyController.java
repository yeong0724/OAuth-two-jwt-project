package com.jinyeong.oauthtwojwt.controller;

import com.jinyeong.oauthtwojwt.domain.MyResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @GetMapping("/my")
    public ResponseEntity<MyResponse> myAPI() {
        MyResponse myResponse = MyResponse.builder().name("Jin-Yeong").age(34).build();
        return ResponseEntity.ok(myResponse);
    }
}