package com.example.finalproject;

import com.example.finalproject.Model.Review;
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

    List<Review> reviews;

    @BeforeEach
    void setUp(){
        review1 = new Review(null, "review1", 4.0, null,null);
        review2 = new Review(null, "review2", 4.0, null,null);
        review3 = new Review(null, "review3", 4.0, null,null);
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
        reviewRepository.save(review3);
        reviews = reviewRepository.getReviewsByProvider(review1.getMyService().getProvider().getId());
        Assertions.assertThat(reviews.get(0).getMyService().getProvider().getId()).isEqualTo(review1.getMyService().getProvider().getId());
    }
}
