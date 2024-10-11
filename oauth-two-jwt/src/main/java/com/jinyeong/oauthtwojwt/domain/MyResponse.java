package com.jinyeong.oauthtwojwt.domain;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyResponse {
    private String name;

    private int age;
}