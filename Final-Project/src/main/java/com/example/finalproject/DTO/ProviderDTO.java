package com.example.finalproject.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProviderDTO {

    private String name;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String field;
    private String instagramAccount;
    
}
