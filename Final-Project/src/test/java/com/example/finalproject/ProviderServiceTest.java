package com.example.finalproject;


import com.example.finalproject.Model.Customer;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Model.Provider;
import com.example.finalproject.Repository.*;
import com.example.finalproject.Service.ProviderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProviderServiceTest {


    @InjectMocks
    ProviderService providerService;
    @Mock
    ProviderRepository providerRepository;
    @Mock
    RequestRepository requestRepository;
    @Mock
    ReviewRepository reviewRepository;

    MyUser myUser;

    Provider provider1, provider2, provider3;


    List<Provider> providers;

    @BeforeEach
    void setUp() {

        myUser = new MyUser(null,"user2","1234","provider",null,null);
        provider1 = new Provider(null,"Maha","provider1","1234","ma@gmail.com","0555555555","Makeup",null,"mahaArt",null,null,null);
        provider2 = new Provider(null,"Maha","provider1","1234","ma@gmail.com","0555555555","Makeup",null,"mahaArt",null,null,null);
        provider3 = new Provider(null,"Maha","provider1","1234","ma@gmail.com","0555555555","Makeup",null,"mahaArt",null,null,null);


        providers = new ArrayList<>();

        providers.add(provider1);
        providers.add(provider2);
        providers.add(provider3);
    }

    @Test
    public void getProviders() {
        when(providerRepository.findAll()).thenReturn(providers);
        List<Provider> providers1 = providerService.getAll();
        Assertions.assertEquals(providers1,providers);
        Assertions.assertEquals(3, providers.size());
        Mockito.verify(providerRepository, times(1)).findAll();
    }
}
