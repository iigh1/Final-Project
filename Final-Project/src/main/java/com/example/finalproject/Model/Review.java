package com.example.finalproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    private Integer id;

    @NotEmpty(message = "Content is required")
    @Column(columnDefinition = "varchar(60)")
    private String content;

    @NotNull(message = "Rating is required")
    @Positive
    @Min(value = 1)
    @Max(value = 5)
    private Double rating;

    @OneToOne
    @MapsId
    @JsonIgnore
    private Request request;

    @ManyToOne
    @JoinColumn(name = "request_id",referencedColumnName = "id")
    @JsonIgnore
    private MyService myService;
}
