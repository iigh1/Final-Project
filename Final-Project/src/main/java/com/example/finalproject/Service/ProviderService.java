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

    public void updateProvider(Integer providerId,Provider provider){
        Provider oldProvider = providerRepository.findProviderById(providerId);
        if (oldProvider == null) {
            throw new ApiException("wrong id");
        }
        else if(oldProvider.getMyUser().getId()!=providerId){
            throw new ApiException("Sorry , You do not have the authority to update this Customer!");
        }
        oldProvider.setPassword(provider.getPassword());
        oldProvider.setEmail(provider.getEmail());
        oldProvider.setField(provider.getField());
        oldProvider.setInstegramAccount(provider.getInstegramAccount());
        oldProvider.setPhoneNumber(provider.getPhoneNumber());

        providerRepository.save(oldProvider);
    }

    public void deleteProvider(Integer id){
        Provider oldProvider=providerRepository.findProviderById(id);

        if (oldProvider == null) {
            throw new ApiException("wrong id");
        }
        providerRepository.delete(oldProvider);
    }

}
