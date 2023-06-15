package com.example.finalproject.Service;

import com.example.finalproject.ApiException.ApiException;
import com.example.finalproject.Model.MyService;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Model.Provider;
import com.example.finalproject.Repository.AuthRepository;
import com.example.finalproject.Repository.MyServiceRepository;
import com.example.finalproject.Repository.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyServiceService {

    private final MyServiceRepository myServiceRepository;
    private final AuthRepository authRepository;

    public List<MyService> getAll(){
        return myServiceRepository.findAll();
    }

    public List<MyService>  getServices(MyUser user){
        return myServiceRepository.findMyServicesByProvider(user.getProvider());
    }

    public void addService(MyUser user, MyService myService){
        if (!user.getRole().equalsIgnoreCase("provider"))
            throw new ApiException("Invalid");
        myService.setProvider(user.getProvider());
        myServiceRepository.save(myService);
    }

    public void updateService(MyUser user, MyService myService, Integer id){
        Provider provider = user.getProvider();
        MyService service= myServiceRepository.findMyServiceById(id);
        if(service == null || provider == null || service.getProvider() != provider){
            throw new ApiException("Invalid");
        }

        service.setName(myService.getName());
        service.setDuration(myService.getDuration());
        service.setPrice(myService.getPrice());
        myServiceRepository.save(service);
    }

    public void deleteService(MyUser user, Integer id){
        Provider provider = user.getProvider();
        MyService service= myServiceRepository.findMyServiceById(id);
        if(service == null || provider == null || service.getProvider() != provider){
            throw new ApiException("Invalid");
        }
        myServiceRepository.delete(service);
    }

    public List<MyService> getServicesByCategory(Integer id, String category){
        MyUser user = authRepository.findMyUserById(id);
        if (user == null)
            throw new ApiException("Invalid");
        return myServiceRepository.findMyServicesByCategory(category);
    }

    public List<MyService> getServicesByPrice(Integer id, Double price){
        MyUser user = authRepository.findMyUserById(id);
        if (user == null)
            throw new ApiException("Invalid");
        return myServiceRepository.findMyServicesByPrice(price);
    }

    public List<MyService> getServicesByRate(Integer id, Double rate){
        MyUser user = authRepository.findMyUserById(id);
        if (user == null)
            throw new ApiException("Invalid");
        return myServiceRepository.findMyServicesByRating(rate);
    }

}
