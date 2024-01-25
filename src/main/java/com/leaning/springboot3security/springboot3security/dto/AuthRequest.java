package com.leaning.springboot3security.springboot3security.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

    private String userName;
    private String password;
}
