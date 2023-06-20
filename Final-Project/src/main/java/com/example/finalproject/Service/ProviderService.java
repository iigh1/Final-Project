package com.example.finalproject.Service;

import com.example.finalproject.ApiException.ApiException;
import com.example.finalproject.DTO.CustomerDTO;
import com.example.finalproject.DTO.ProviderDTO;
import com.example.finalproject.Model.*;
import com.example.finalproject.Repository.AuthRepository;
import com.example.finalproject.Repository.MyServiceRepository;
import com.example.finalproject.Repository.ProviderRepository;
import com.example.finalproject.Repository.RequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.method.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class ProviderService {

    private final ProviderRepository providerRepository;
    private final AuthRepository authRepository;
    private final MyServiceRepository myServiceRepository;
    private final RequestRepository requestRepository;

    public List<Provider> getAll(){
        return providerRepository.findAll();
    }

    public Provider getProvider(MyUser user){
        Provider provider = providerRepository.findProviderById(user.getId());
        if (provider == null )
            throw new ApiException("Invalid");
        return provider;
    }

    public String getProviderForCustomer(MyUser user, Integer providerId){
        Provider provider = providerRepository.findProviderById(providerId);
        if (provider == null || user.getRole().equalsIgnoreCase("provider"))
            throw new ApiException("Invalid");
        return "Name: "+provider.getName()+"\n"+"Rate: "+provider.getRating()+"\n"+"Instagram Account: "+provider.getInstagramAccount()+"\n";
    }
    
    public void addProvider(ProviderDTO dto) {

        String hash=new BCryptPasswordEncoder().encode(dto.getPassword());
        dto.setPassword(hash);
        MyUser myUser=new MyUser(null, dto.getUsername(), dto.getPassword(),"PROVIDER",null,null);
        Provider provider = new Provider(null, dto.getName(), dto.getUsername(), dto.getPassword(), dto.getEmail(), dto.getPhoneNumber(), dto.getField(), dto.getInstagramAccount(),null,myUser,null,null);
        myUser.setProvider(provider);
        providerRepository.save(provider);
        authRepository.save(myUser);
    }

    public void updateProvider(Integer providerId,Provider provider){
        Provider oldProvider = providerRepository.findProviderById(providerId);
        if (oldProvider == null) {
            throw new ApiException("wrong id");
        }
        else if(oldProvider.getId() != providerId){
            throw new ApiException("Sorry , You do not have the authority to update this Provider!");
        }
        oldProvider.setName(provider.getName());
        oldProvider.setPassword(provider.getPassword());
        oldProvider.setEmail(provider.getEmail());
        oldProvider.setField(provider.getField());
        oldProvider.setPhoneNumber(provider.getPhoneNumber());
        oldProvider.setInstagramAccount(provider.getInstagramAccount());
        oldProvider.setRating(provider.getRating());

        providerRepository.save(oldProvider);
    }

    public void deleteProvider(Integer id){
        Provider oldProvider=providerRepository.findProviderById(id);

        if (oldProvider == null ) {
            throw new ApiException("wrong id");
        }
        providerRepository.delete(oldProvider);
    }
    public void discount(MyUser myUser, Integer amount){
        Provider provider = providerRepository.findProviderById(myUser.getId());
        List<MyService> services= myServiceRepository.findMyServicesByProvider(provider.getId());
        if (provider==null || services.size() == 0){
            throw new ApiException("invalid");
        }
        for ( MyService s :services){
            s.setPrice(s.getPrice()-(s.getPrice()*amount/100));
            myServiceRepository.save(s);
        }
    }

    public Set<Request> getRequest(MyUser myUser){
        Provider p = providerRepository.findProviderById(myUser.getId());
        if (p==null){
            throw new ApiException("invalid");
        }
        return p.getRequestSet();
    }

    public Set<MyService> getService(MyUser myUser){
        Provider p=providerRepository.findProviderById(myUser.getId());
        if (  p == null){
            throw new ApiException("invalid");
        }
        return p.getServiceSet();
    }

    public List<Request> getCompletedRequest(MyUser myUser){
        Provider provider = providerRepository.findProviderById(myUser.getId());
        if (provider==null){
            throw new ApiException("invalid");
        }
        return requestRepository.findCompletedRequestsByStatusAndCustomer("Completed","Canceled","Rejected", myUser.getId());
    }
    public List<Request> getNewRequest(MyUser myUser){
        Provider provider = providerRepository.findProviderById(myUser.getId());
        if (provider==null){
            throw new ApiException("invalid");
        }
        return requestRepository.findRequestsByStatus("new", provider.getId());
    }

    public List<Request> getActiveRequest(MyUser myUser){
        Provider provider = providerRepository.findProviderById(myUser.getId());
        if (provider==null){
            throw new ApiException("invalid");
        }
        return requestRepository.findRequestsByStatus("Active", provider.getId());
    }

    public void acceptRequest(MyUser myUser, Integer requestId){
        Provider provider=providerRepository.findProviderById(myUser.getId());
        Request r =  requestRepository.findRequestById(requestId);
        if (provider==null || r==null || r.getProvider()!=provider){
            throw new ApiException("invalid");
        }
        r.setStatus("Accepted");
        requestRepository.save(r);
    }

    public void rejectRequest(MyUser myUser, Integer requestId){
        Provider provider=providerRepository.findProviderById(myUser.getId());
        Request r =  requestRepository.findRequestById(requestId);
        if (provider==null || r==null || r.getProvider()!=provider){
            throw new ApiException("invalid");
        }
        r.setStatus("Rejected");
        requestRepository.save(r);
    }

    public void completeRequest(MyUser myUser, Integer requestId){
        Provider provider=providerRepository.findProviderById(myUser.getId());
        Request r =  requestRepository.findRequestById(requestId);
        if (provider==null || r==null || r.getProvider()!=provider){
            throw new ApiException("invalid");
        }
        r.setStatus("Completed");
        requestRepository.save(r);
    }
    public List<Provider> getProviderByField(String field){

        return providerRepository.findProviderByField(field);
    }

}
