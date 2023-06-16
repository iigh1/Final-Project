package com.example.finalproject.Service;

import com.example.finalproject.ApiException.ApiException;
import com.example.finalproject.Model.MyService;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Model.Provider;
import com.example.finalproject.Repository.MyServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyServiceService {

    private final MyServiceRepository myServiceRepository;

    public List<MyService> getAll(){
        return myServiceRepository.findAll();
    }

    public List<MyService>  getServices(Integer id){
        return myServiceRepository.findMyServicesByProvider(id);
    }

    public void addService(MyUser user, MyService myService){
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

    public List<Provider> getServicesByCategory(MyUser user, String category){
        if (user.getRole().equalsIgnoreCase("provider"))
            throw new ApiException("Invalid");
          List<MyService> services = myServiceRepository.findMyServicesByCategory(category);
          List<Provider> providers = new ArrayList<>();
        for (MyService s : services) {
            providers.add(s.getProvider());
        }
        return providers;
    }

    public List<Provider> sortServicesByPrice(String category){
        List<MyService> services = myServiceRepository.findMyServicesByCategory(category);
        List<Provider> providers = new ArrayList<>();
        Collections.sort(services,(o1, o2) -> (int) (o1.getPrice() - o2.getPrice()));
        for (MyService s : services) {
            providers.add(s.getProvider());
        }
        return providers;
    }

    public List<Provider> sortServicesByRate(String category){
        List<MyService> services = myServiceRepository.findMyServicesByCategory(category);
        List<Provider> providers = new ArrayList<>();
        Collections.sort(services,(o1, o2) -> (int) (o2.getRating() - o1.getRating()));
        for (MyService s : services) {
            providers.add(s.getProvider());
        }
        return providers;
    }

    public List<MyService> sortServicesOfProvider(Integer providerId){
        List<MyService> services = myServiceRepository.findMyServicesByProvider(providerId);
        Collections.sort(services,(o1, o2) -> (int) (o2.getRating() - o1.getRating()));
        return services;
    }

}
