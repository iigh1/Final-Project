package com.example.finalproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private Integer wallet;

    @OneToOne
    @MapsId // PK
    @JsonIgnore
    private MyUser myUser;
    
    //Nada
    @ManyToMany(mappedBy = "providerSet")
    private Set<MyService> myServices;

}
