package com.example.finalproject.Service;

import com.example.finalproject.ApiException.ApiException;
import com.example.finalproject.DTO.CustomerDTO;
import com.example.finalproject.DTO.ProviderDTO;
import com.example.finalproject.Model.Customer;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Model.Provider;
import com.example.finalproject.Repository.AuthRepository;
import com.example.finalproject.Repository.ProviderRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.method.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProviderService {

    private final ProviderRepository providerRepository;
    private final AuthRepository authRepository;

    public List<Provider> getAll(){
        return providerRepository.findAll();
    }

    public void addProvider(ProviderDTO dto) {

        String hash=new BCryptPasswordEncoder().encode(dto.getPassword());
        dto.setPassword(hash);
        MyUser myUser=new MyUser(null, dto.getUsername(), dto.getPassword(),"CUSTOMER",null,null);
        Provider provider = new Provider(myUser.getId(), dto.getName(), dto.getUsername(), dto.getPassword(), dto.getEmail(), dto.getPhoneNumber(), dto.getField(),0.0, dto.getInstegramAccount(),myUser,null,null);
        myUser.setProvider(provider);
        providerRepository.save(provider);
        authRepository.save(myUser);
    }

    public void updateProvider(Integer providerId,Provider provider,Integer auth){
        Provider oldProvider = providerRepository.findProviderById(providerId);
        MyUser myUser=authRepository.findMyUserById(auth);
        if (oldProvider == null) {
            throw new ApiException("wrong id");
        }
        else if(oldProvider.getMyUser().getId()!=auth){
            throw new ApiException("Sorry , You do not have the authority to update this Customer!");
        }
        oldProvider.setName(provider.getName());
        oldProvider.setPassword(provider.getPassword());
        oldProvider.setEmail(provider.getEmail());
        oldProvider.setField(provider.getField());
        oldProvider.setPhoneNumber(provider.getPhoneNumber());
        oldProvider.setInstegramAccount(provider.getInstegramAccount());
        oldProvider.setRating(provider.getRating());

        providerRepository.save(oldProvider);
    }

    public void deleteProvider(Integer id){
        Provider oldProvider=providerRepository.findProviderById(id);

        if (oldProvider == null) {
            throw new ApiException("wrong id");
        }
        providerRepository.delete(oldProvider);
    }
    public void discount(MyUser myUser, Integer amount){
        Provider provider = myUser.getProvider();
        if (provider==null){
            throw new ApiException("invalid");
        }
        Set<MyService> services=myUser.getProvider().getServiceSet();
        for ( MyService s :services){
            s.setPrice(s.getPrice()-(s.getPrice()*amount/100));
            myServiceRepository.save(s);
        }
    }

    public Set<Request> getRequest(MyUser myUser){
        Provider p =myUser.getProvider();
        if (p==null){
            throw new ApiException("invalid");
        }
        return p.getRequestSet();
    }

    public Set<MyService> getService(MyUser myUser, Integer providerId){
        Provider p=myUser.getProvider();
        if (myUser.getRole().equalsIgnoreCase("provider") || myUser.getId() != providerId){
            throw new ApiException("invalid");
        }
        return p.getServiceSet();
    }

    public List<Request> getCompletedRequest(MyUser myUser){
        Provider provider = myUser.getProvider();
        if (provider==null){
            throw new ApiException("invalid");
        }
        List<Request> requests = new ArrayList<>();
        Set<Request> requestSet = provider.getRequestSet();
        for (Request r : requestSet){
            if (r.getStatus().equalsIgnoreCase("completed"));
                requests.add(r);
        }
        return requests;
    }
    public List<Request> getNewRequest(MyUser myUser){
        Provider provider = myUser.getProvider();
        if (provider==null){
            throw new ApiException("invalid");
        }
        List<Request> requests = new ArrayList<>();
        Set<Request> requestSet = provider.getRequestSet();
        for (Request r : requestSet){
            if (r.getStatus().equalsIgnoreCase("new"));
            requests.add(r);
        }
        return requests;
    }

    public void acceptRequest(MyUser myUser, Integer requestId){
        Provider provider=myUser.getProvider();
        Request r =  requestRepository.findRequestById(requestId);
        if (provider==null || r==null || r.getProvider()!=provider){
            throw new ApiException("invalid");
        }
        r.setStatus("accept");
        requestRepository.save(r);
    }

    public void rejectRequest(MyUser myUser, Integer requestId){
        Provider provider=myUser.getProvider();
        Request r =  requestRepository.findRequestById(requestId);
        if (provider==null || r==null || r.getProvider()!=provider){
            throw new ApiException("invalid");
        }
        r.setStatus("reject");
        requestRepository.save(r);
    }

}
