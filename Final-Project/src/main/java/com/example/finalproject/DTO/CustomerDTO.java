package com.example.finalproject.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDTO {

    private String name;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;

//    private Integer points;
//    private Double wallet;
//    private boolean loyalty = false;

}
