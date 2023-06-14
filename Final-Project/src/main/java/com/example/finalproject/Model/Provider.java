package com.example.finalproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Provider {

    @Id
    private Integer id;

    @NotEmpty(message = "name can't be empty")
    @Column(columnDefinition = "varchar(20) not null")
    private String name;
    @NotEmpty(message = "username can't be empty")
    @Column(columnDefinition = "varchar(20) not null")
    private String username;
    @NotEmpty(message = "password can't be empty")
    @Column(columnDefinition = "varchar(20) not null")
    private String password;
    @NotEmpty(message = "email can't be empty")
    @Email(message = "invalid email",regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    @Column(columnDefinition = "varchar(40) not null unique")
    private String email;
    @NotEmpty(message = "phone number can't be empty")
    @Column(columnDefinition ="varchar(10)")
    private String phoneNumber;


    @NotEmpty(message = "field can't be empty")
    @Column(columnDefinition = "varchar(20) not null check(field='makeup' or field='hairStyle' or field='spa' or field='design' or field='photograph')")
    private String field;

    @Positive
    @Min(value = 1)
    @Max(value = 5)
    private Double rating;

    @NotEmpty(message = "instegramAccount can't be empty")
    @Column(columnDefinition = "varchar(30) not null")
    private String instegramAccount;



    @OneToOne
    @MapsId // PK
    @JsonIgnore
    private MyUser myUser;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "provider")
    private Set<Request> requestSet;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "provider")
    private  Set<MyService> serviceSet;

}
