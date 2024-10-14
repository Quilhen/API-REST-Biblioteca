package com.davidgt.springboot.app.springboot_biblioteca.dto;


public class AuthResponseDto {

    private String jwtToken;

    public AuthResponseDto(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    
}
