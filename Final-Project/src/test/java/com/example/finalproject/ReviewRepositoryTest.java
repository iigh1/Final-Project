package com.example.finalproject;

import com.example.finalproject.Model.*;
import com.example.finalproject.Repository.ReviewRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReviewRepositoryTest {
    @Autowired
    ReviewRepository reviewRepository;

    Review review1, review2, review3;


    Request request1,request2;

    MyService myService;

    MyUser user;

    Provider provider;

    List<Review> reviews;

    @BeforeEach
    void setUp(){
        //user = new MyUser(null,"username","password","PROVIDER",null,null);
        //provider = new Provider(null, "provider1","provider","password","pro@gmail.com","1111111111","Makeup","@pro1",0.0,user,null,null);
        //myService = new MyService(null,"makeup",0.0,"Makeup",140.0,provider,null,null);
        request1 = new Request(null,"09-09-2022","wedding","New",140.0,null,null,null,null,null);
        request2 = new Request(null,"09-09-2022","wedding","New",140.0,null,null,null,null,null);
        review1 = new Review(null, "review1", 4.0, request1,null);
        review2 = new Review(null, "review2", 4.0, request2,null);
        //review3 = new Review(null, "review3", 4.0, request,null);
    }

    @Test
    public void findReviewById(){
        reviewRepository.save(review1);
        review2 = reviewRepository.findReviewById(review1.getId());
        Assertions.assertThat(review2).isEqualTo(review1);
    }

    @Test
    public void getReviewsByProvider(){
        reviewRepository.save(review1);
        reviewRepository.save(review2);
        //reviewRepository.save(review3);
        reviews = reviewRepository.getReviewsByProvider(review1.getMyService().getProvider().getId());
        Assertions.assertThat(reviews.get(0).getMyService().getProvider().getId()).isEqualTo(review1.getMyService().getProvider().getId());
    }
}
