package com.example.finalproject.Controller;

import com.example.finalproject.DTO.ReviewDTO;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Model.Review;
import com.example.finalproject.Service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/get")
    public ResponseEntity getAll(){
        return ResponseEntity.status(200).body(reviewService.getAll());
    }

    @GetMapping("/get-review/{id}")
    public ResponseEntity getReview(@AuthenticationPrincipal MyUser user,@PathVariable Integer id){
        return ResponseEntity.status(200).body(reviewService.getReview(user, id));
    }

    @PostMapping("/add")
    public ResponseEntity addReview(@AuthenticationPrincipal MyUser user, @Valid @RequestBody ReviewDTO dto){
        reviewService.addReview(user, dto);
        return ResponseEntity.status(200).body("Review added");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateReview(@AuthenticationPrincipal MyUser user, @Valid @RequestBody Review review, @PathVariable Integer id){
        reviewService.updateReview(user, id, review);
        return ResponseEntity.status(200).body("Review updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteReview(@AuthenticationPrincipal MyUser user, @PathVariable Integer id){
        reviewService.deleteReview(user, id);
        return ResponseEntity.status(200).body("Review deleted");
    }

    @GetMapping("/get-reviews/{providerId}")
    public ResponseEntity getReviewsOfProvider(@AuthenticationPrincipal MyUser user,@PathVariable Integer providerId){
        return ResponseEntity.status(200).body(reviewService.getReviewsOfProvider(user,providerId));
    }
}
