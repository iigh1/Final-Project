package com.example.finalproject.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProviderDTO {

//    private Integer user_id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String field;
//    private Double rating;
    private String instagramAccount;
//    private Integer wallet;
}
