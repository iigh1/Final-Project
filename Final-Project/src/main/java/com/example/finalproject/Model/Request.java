package com.example.finalproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date date;

    @Column(name="time")
    private LocalDateTime time;

    @NotEmpty(message = "Occasion is required")
    @Column(columnDefinition = "varchar(20)")
    private String occasion;

    @Pattern(regexp = "\\b(?:New|Accepted|inProgress|Completed)\\b",message = "Status Not Valid")
    @Column(columnDefinition = "varchar(20) not null check(Status='New' or Status='Accepted' " +
            "or Status='inProgress' or Status='Completed')")
    private String status;

    @NotNull(message = "Price is required")
    @Positive
    private Double price;

    @ManyToOne
    @JoinColumn(name = "request_id",referencedColumnName = "id")
    @JsonIgnore
    private MyService myService;

}
