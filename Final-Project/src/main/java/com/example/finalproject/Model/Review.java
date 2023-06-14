package com.example.finalproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
    public class Review {

        @Id
        private Integer id;

        @NotEmpty(message = "Content is required")
        @Column(columnDefinition = "varchar(60)")
        private String content;

        @Positive
        @Min(value = 1)
        @Max(value = 5)
        private Double rating;

        @OneToOne
        @MapsId
        @JsonIgnore
        private Request request;

        @ManyToOne
        @JoinColumn(name = "service_id",referencedColumnName = "id")
        @JsonIgnore
        private MyService myService;
    }

