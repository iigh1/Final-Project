package com.example.finalproject;


import com.example.finalproject.Controller.CustomerController;
import com.example.finalproject.Controller.RequestController;
import com.example.finalproject.Model.Customer;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Model.Request;
import com.example.finalproject.Service.CustomerService;
import com.example.finalproject.Service.RequestService;
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
@WebMvcTest(value = RequestController.class , excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class RequestControllerTest {

    @MockBean
    RequestService requestService;
    @Autowired
    MockMvc mockMvc;

    Request request1, request2, request3;
    MyUser myUser;

    List<Request> requests, requestList;


    @BeforeEach
    void setUp() {
        myUser=new MyUser(1,"user1","1234","customer",null,null);

        request1 = new Request(null,"2023-6-10","New","wedding",150.0,null,null,null,null,null);
        request2 = new Request(null,"2023-6-10","New","wedding",150.0,null,null,null,null,null);
        request3 = new Request(null,"2023-6-10","New","wedding",150.0,null,null,null,null,null);

        requests= Arrays.asList(request1);
        requestList=Arrays.asList(request2);

    }

    @Test
    public void GetAllRequests() throws Exception {
        Mockito.when(requestService.getAll()).thenReturn(requests);
        mockMvc.perform(get("/api/v1/request/get"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }
}