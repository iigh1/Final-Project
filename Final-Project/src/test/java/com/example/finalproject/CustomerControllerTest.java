package com.example.finalproject;


import com.example.finalproject.Controller.CustomerController;
import com.example.finalproject.Model.Customer;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.RequestEntity.delete;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = CustomerController.class , excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class CustomerControllerTest {

    @MockBean
    CustomerService customerService;
    @Autowired
    MockMvc mockMvc;

    Customer customer1, customer2, customer3;
    MyUser myUser;

    List<Customer> customers,customerList;

    @BeforeEach
    void setUp() {
        myUser=new MyUser(1,"user1","1234","customer",null,null);
        customer1 = new Customer(1,"Maha","user1","1234","mm@gmail.com","0555555555",null,null);
        customer2 = new Customer(1,"Maha","user1","1234","mm@gmail.com","0555555555",null,null);
        customer3 = new Customer(1,"Maha","user1","1234","mm@gmail.com","0555555555",null,null);
        customers= Arrays.asList(customer1);
        customerList=Arrays.asList(customer2);

    }

    @Test
    public void GetAllCustomer() throws Exception {
        Mockito.when(customerService.getAllCustomers()).thenReturn(customers);
        mockMvc.perform(get("/api/v1/customer/get"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void GetCustomer() throws Exception {
        Mockito.when(customerService.getCustomer(myUser)).thenReturn(customer1);
        mockMvc.perform(get("/api/v1/customer/get-customer"))
                .andExpect(status().isOk());
    }

//    @Test
//    public void testAddCustomer() throws  Exception {
//        mockMvc.perform(post("/api/v1/customer")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content( new ObjectMapper().writeValueAsString(customer1)))
//                .andExpect(status().isOk());
//
//    }

//    @Test
//    public void DeleteCustomer() throws Exception{
//        mockMvc.perform(delete("api/v1/customer"),customer1.getId())
//                .andExpect(status().isOk());
//    }


}
