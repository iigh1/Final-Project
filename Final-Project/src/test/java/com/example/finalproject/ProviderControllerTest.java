package com.example.finalproject;


import com.example.finalproject.Controller.CustomerController;
import com.example.finalproject.Controller.ProviderController;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Model.Provider;
import com.example.finalproject.Service.ProviderService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ProviderController.class , excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class ProviderControllerTest {


    @MockBean
    ProviderService providerService;
    @Autowired
    MockMvc mockMvc;

    Provider provider1, provider2, provider3;
    MyUser myUser;

    List<Provider> providers, providerList;


    @BeforeEach
    void setUp() {

        myUser = new MyUser(null, "user2", "1234", "provider", null, null);
        provider1 = new Provider(null, "Maha", "provider1", "1234", "ma@gmail.com", "0555555555", "Makeup", null, "mahaArt", null, null, null);
        provider2 = new Provider(null, "Maha", "provider1", "1234", "ma@gmail.com", "0555555555", "Makeup", null, "mahaArt", null, null, null);
        provider3 = new Provider(null, "Maha", "provider1", "1234", "ma@gmail.com", "0555555555", "Makeup", null, "mahaArt", null, null, null);

        providers= Arrays.asList(provider1);
        providerList=Arrays.asList(provider2);
    }

    @Test
    public void GetAllProviders() throws Exception {
        Mockito.when(providerService.getAll()).thenReturn(providers);
        mockMvc.perform(get("/api/v1/provider/get"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }


    @Test
    public void GetProvider() throws Exception {
        Mockito.when(providerService.getProvider(myUser, myUser.getId())).thenReturn(provider1);
        mockMvc.perform(get("/api/v1/provider/get-provider"))
                .andExpect(status().isOk());
    }

}
