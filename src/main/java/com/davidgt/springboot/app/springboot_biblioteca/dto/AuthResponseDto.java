package com.davidgt.springboot.app.springboot_biblioteca.dto;

import lombok.Getter;

@Getter
public class AuthResponseDto {

    private String jwtToken;

    public AuthResponseDto(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
