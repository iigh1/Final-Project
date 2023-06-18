package com.example.finalproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Date is required")
    private String date;

    @NotEmpty(message = "Occasion is required")
    @Column(columnDefinition = "varchar(20)")
    private String occasion;

    @Pattern(regexp = "\\b(?:New|Accepted|Rejected|Canceled|Completed)\\b",message = "Status Not Valid")
    @Column(columnDefinition = "varchar(20) check(Status='New' or Status='Accepted' " +
            "or Status='canceled' or Status='Completed')")
    private String status;

    @Positive
    private Double price;

    @ManyToOne
    @JoinColumn(name = "request_id",referencedColumnName = "id")
    @JsonIgnore
    private MyService myService;


    @OneToOne(cascade = CascadeType.ALL,mappedBy = "request")
    @PrimaryKeyJoinColumn
    private Review review;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    @JsonIgnore
    private Provider provider;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

}


