package com.example.finalproject.Service;

import com.example.finalproject.ApiException.ApiException;
import com.example.finalproject.Model.Customer;
import com.example.finalproject.Model.MyService;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Model.Request;
import com.example.finalproject.Repository.CustomerRepository;
import com.example.finalproject.Repository.MyServiceRepository;
import com.example.finalproject.Repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;
    private final MyServiceRepository myServiceRepository;
    private final CustomerRepository customerRepository;

    public List<Request> getAll(){
        return requestRepository.findAll();
    }

    public void addRequest(MyUser user, Request request, Integer serviceId){
        MyService service = myServiceRepository.findMyServiceById(serviceId);
        if (service == null)
            throw new ApiException("Invalid");
        request.setCustomer(user.getCustomer());
        request.setStatus("New");
        request.setMyService(service);
        request.setPrice(service.getPrice());
        request.setProvider(service.getProvider());
        requestRepository.save(request);
    }

    public void updateRequest(MyUser user, Request request, Integer id){
        Request req = requestRepository.findRequestById(id);
        if (req == null || req.getCustomer() != user.getCustomer() || !req.getStatus().equalsIgnoreCase("new"))
            throw new ApiException("Invalid");
        req.setDate(request.getDate());
        req.setOccasion(request.getOccasion());
        requestRepository.save(req);
    }

    public void deleteRequest(MyUser user, Integer id){
        Request req = requestRepository.findRequestById(id);
        if (req == null || req.getCustomer() != user.getCustomer() || !req.getStatus().equalsIgnoreCase("new"))
            throw new ApiException("Invalid");
        requestRepository.delete(req);
    }



    public void gift(MyUser user, Integer requestId, Integer customerID){
        Request req = requestRepository.findRequestById(requestId);
        Customer customer = customerRepository.findCustomerById(customerID);
        if (req == null || customer == null || req.getCustomer().getId() != user.getId() || req.getStatus().equalsIgnoreCase("cancel"))
            throw new ApiException("Invalid");
        req.setGiftedCustomer(customerID);
        requestRepository.save(req);
    }
}
