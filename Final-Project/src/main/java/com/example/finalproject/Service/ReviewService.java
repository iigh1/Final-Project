package com.example.finalproject.Service;

import com.example.finalproject.ApiException.ApiException;
import com.example.finalproject.DTO.ReviewDTO;
import com.example.finalproject.Model.*;
import com.example.finalproject.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RequestRepository requestRepository;
    private final ProviderRepository providerRepository;
    private final MyServiceRepository myServiceRepository;
    private final CustomerRepository customerRepository;

    public List<Review> getAll(){
        return reviewRepository.findAll();
    }

    public Review getReview(MyUser user,Integer id){
        Review review = reviewRepository.findReviewById(id);
        Customer customer = user.getCustomer();
        if (review == null || customer != review.getRequest().getCustomer())
            throw new ApiException("Not found");
        return review;
    }
    public void addReview(MyUser user, ReviewDTO dto){
        Request request = requestRepository.findRequestById(dto.getRequestId());
        if (request == null )
            throw new ApiException("Request not found");
        Customer customer = request.getCustomer();
        Customer c = customerRepository.findCustomerById(user.getId());
        Provider provider = request.getProvider();
        MyService service = request.getMyService();
        if (c != customer)
            throw new ApiException("Unable");
        Review review = new Review(null, dto.getContent(), dto.getRating(), request,request.getMyService());
        request.setReview(review);
        service.getReviews().add(review);
        Set<Review> reviews = service.getReviews();
        if (reviews.size() > 1) {
            service.setRating(calculateRate(reviews));
            provider.setRating(calculateRate(getReviewsOfProvider(user,provider.getId())));
        }
        else {
            service.setRating(dto.getRating());
            provider.setRating(dto.getRating());
        }
        reviewRepository.save(review);
        myServiceRepository.save(service);
        providerRepository.save(provider);
    }

    public void updateReview(MyUser user, Integer id, Review review){
        Review r = reviewRepository.findReviewById(id);
        Customer customer = user.getCustomer();
        if(r == null  || r.getRequest().getCustomer() != customer){
            throw new ApiException("Not found");
        }
        r.setContent(review.getContent());
        r.setRating(review.getRating());
        reviewRepository.save(r);
    }

    public void deleteReview(MyUser user, Integer id){
        Review r = reviewRepository.findReviewById(id);
        Customer customer = user.getCustomer();
        if(r == null || r.getRequest().getCustomer() != customer){
            throw new ApiException("Not found");
        }
        reviewRepository.delete(r);
    }

    public List<Review> getReviewsOfProvider(MyUser user, Integer providerId){
        Provider provider = providerRepository.findProviderById(providerId);
        if (provider == null || (user.getProvider() != null && user.getProvider() != provider))
            throw new ApiException("Invalid");
        return reviewRepository.getReviewsByProvider(providerId);
    }

    public Double calculateRate(Set<Review> reviews) {
        Integer count1 = 0, count2 = 0, count3 = 0, count4 = 0, count5 = 0;
        for (Review re : reviews) {
            if (re.getRating() == 1)
                count1++;
            else if (re.getRating() == 2)
                count2++;
            else if (re.getRating() == 3)
                count3++;
            else if (re.getRating() == 4)
                count4++;
            else if (re.getRating() == 5)
                count5++;
        }
        Double rate = (double) (((1 * count1) + (2 * count2) + (3 * count3) + (4 * count4) + (5 * count5)) / reviews.size());
        return rate;
    }
    public Double calculateRate(List<Review> reviews) {
        Integer count1 = 0, count2 = 0, count3 = 0, count4 = 0, count5 = 0;
        for (Review re : reviews) {
            if (re.getRating() == 1)
                count1++;
            else if (re.getRating() == 2)
                count2++;
            else if (re.getRating() == 3)
                count3++;
            else if (re.getRating() == 4)
                count4++;
            else if (re.getRating() == 5)
                count5++;
        }
        Double rate = (double) (((1 * count1) + (2 * count2) + (3 * count3) + (4 * count4) + (5 * count5)) / reviews.size());
        return rate;
    }
}
