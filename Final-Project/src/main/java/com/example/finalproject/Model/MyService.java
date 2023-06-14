package com.example.finalproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
public class MyService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Name is required")
    @Size(min = 2)
    @Column(columnDefinition = "varchar(20) check ( length(name) > 2 )")
    private String name;

    @NotNull(message = "Rating is required")
    @Positive
    @Min(value = 1)
    @Max(value = 5)
    private Double rating;

    @Pattern(regexp = "\\b(?:Makeup|HairStyle|Spa|Photograph|Design)\\b",message = "Category Not Valid")
    @Column(columnDefinition = "varchar(20) not null check(category='Makeup' or category='HairStyle' " +
            "or category='Spa' or category='Photograph' or category='Design')")
    private String Category;

    @NotNull(message = "Price is required")
    @Positive
    private Double price;

    @NotNull(message = "Duration is required")
    @Positive
    private Integer duration;

    @ManyToMany
    @JsonIgnore
    private Set<Provider> providerSet;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "myService")
    private Set<Request> requestSet;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "myService")
    private Set<Review> reviews;
}
