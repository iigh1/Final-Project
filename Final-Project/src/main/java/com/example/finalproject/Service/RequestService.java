package com.example.finalproject.Service;

import com.example.finalproject.ApiException.ApiException;
import com.example.finalproject.Model.MyService;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Model.Request;
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

    public List<Request> getAll(){
        return requestRepository.findAll();
    }

    public void addRequest(MyUser user, Request request, Integer serviceId){
        MyService service = myServiceRepository.findMyServiceById(serviceId);
        if (service == null || !user.getRole().equalsIgnoreCase("customer"))
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
        if (req == null || req.getCustomer() != user.getCustomer())
            throw new ApiException("Invalid");
        req.setDate(request.getDate());
        req.setOccasion(request.getOccasion());
        req.setTime(request.getTime());
        requestRepository.save(req);
    }

    public void deleteRequest(MyUser user, Integer id){
        Request req = requestRepository.findRequestById(id);
        if (req == null || req.getCustomer() != user.getCustomer() || !req.getStatus().equalsIgnoreCase("new"))
            throw new ApiException("Invalid");
        requestRepository.delete(req);
    }
}
