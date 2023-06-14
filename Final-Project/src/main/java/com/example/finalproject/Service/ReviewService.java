package com.example.finalproject.Service;

import com.example.finalproject.ApiException.ApiException;
import com.example.finalproject.DTO.ReviewDTO;
import com.example.finalproject.Model.Customer;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Model.Request;
import com.example.finalproject.Model.Review;
import com.example.finalproject.Repository.AuthRepository;
import com.example.finalproject.Repository.RequestRepository;
import com.example.finalproject.Repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RequestRepository requestRepository;
    private final AuthRepository authRepository;


    public List<Review> getAll(){
        return reviewRepository.findAll();
    }

    public List<Review> getReviewsOfProvider(Integer id, Integer providerId){
        MyUser user = authRepository.findMyUserById(id);
        if (user == null)
            throw new ApiException("Invalid");
        return reviewRepository.getReviewsByProvider(providerId);
    }

    public void addReview(MyUser user, ReviewDTO dto){
        Request request = requestRepository.findRequestById(dto.getRequestId());
        Customer customer = request.getCustomer();
        Customer c = user.getCustomer();
        if (c != customer)
            throw new ApiException("Unable");
        Review review = new Review(null, dto.getContent(), dto.getRating(), request,request.getMyService());
        reviewRepository.save(review);
        request.setReview(review);
        request.getMyService().getReviews().add(review);
    }

    public void updateReview(MyUser user, Integer id, Review review){
        Review r = reviewRepository.findReviewById(id);
        Customer customer = user.getCustomer();
        if(r == null || !user.getRole().equalsIgnoreCase("customer") || r.getRequest().getCustomer() != customer){
            throw new ApiException("Not found");
        }
        r.setContent(review.getContent());
        r.setRating(review.getRating());
        reviewRepository.save(r);
    }

    public void deleteReview(MyUser user, Integer id){
        Review r = reviewRepository.findReviewById(id);
        Customer customer = user.getCustomer();
        if(r == null || !user.getRole().equalsIgnoreCase("customer") || r.getRequest().getCustomer() != customer){
            throw new ApiException("Not found");
        }
        reviewRepository.delete(r);
    }
}
