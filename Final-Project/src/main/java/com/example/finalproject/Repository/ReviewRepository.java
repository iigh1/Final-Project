package com.example.finalproject.Repository;

import com.example.finalproject.Model.MyService;
import com.example.finalproject.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer> {

    Review findReviewById(Integer id);


    @Query("select r from Review r where r.myService.provider.id = ?1")
    List<Review> getReviewsByProvider(Integer id);



}
