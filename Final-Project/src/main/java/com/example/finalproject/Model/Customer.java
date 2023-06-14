package com.example.finalproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer {


    @Id
    private Integer id;

    @NotEmpty(message = "name can't be empty")
    @Size(min = 2)
    @Column(columnDefinition = "varchar(20) not null")
    private String name;

    @NotEmpty(message = "username can't be empty")
    @Column(columnDefinition = "varchar(20) not null")
    private String username;
//    @NotEmpty(message = "password can't be empty")
//    @Column(columnDefinition = "varchar(20) not null")
    private String password;
//    @NotEmpty(message = "email can't be empty")
//    @Email
//    @Column(columnDefinition = "varchar(40) not null unique")
    private String email;
    @NotEmpty(message = "phone number can't be empty")
    @Column(columnDefinition ="varchar(10)")
    private String phoneNumber;

    private Integer points;


    private boolean loyalty = false;


    @OneToOne
    @MapsId // PK
    @JsonIgnore
    private MyUser myUser;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "customer")
    private Set<Request> requestSet;



}
