package com.example.finalproject;

import com.example.finalproject.DTO.CustomerDTO;
import com.example.finalproject.Model.Customer;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Model.Provider;
import com.example.finalproject.Repository.*;
import com.example.finalproject.Service.CustomerService;
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

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {


    @InjectMocks
    CustomerService customerService;


    @Mock
    AuthRepository authRepository;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    MyServiceRepository myServiceRepository;
    @Mock
    ProviderRepository providerRepository;
    @Mock
    RequestRepository requestRepository;
    @Mock
    ReviewRepository reviewRepository;

    MyUser myUser;


    Customer customer1, customer2, customer3;
    CustomerDTO customerDTO;
    List<Customer> customers;


    @BeforeEach
    void setUp() {

        myUser = new MyUser(null,"user1","1234","customer",null,null);
        customer1 = new Customer(null,"sara","sara","1234","sar@Gmail.com","0555555555",null,null);
        customer2 = new Customer(null,"sara","sara","1234","sar@Gmail.com","0555555555",null,null);
        customer3 = new Customer(null,"sara","sara","1234","sar@Gmail.com","0555555555",null,null);


        customers = new ArrayList<>();

        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);
    }

    @Test
    public void getCustomers() {
        when(customerRepository.findAll()).thenReturn(customers);
        List<Customer> customers1 = customerService.getAllCustomers();
        Assertions.assertEquals(customers1, customers);
        Assertions.assertEquals(3, customers.size());
        Mockito.verify(customerRepository, times(1)).findAll();
    }
    @Test
    public void getCustomerByIdTest() {
        when(customerRepository.findCustomerById(myUser.getId())).thenReturn(customer1);
        Customer customer = customerService.getCustomer(myUser);
        Assertions.assertEquals(customer,customer1);
        Mockito.verify(customerRepository, times(1)).findCustomerById(myUser.getId());
    }


//    @Test
//    public void addCustomerTest(){
//        when(authRepository.findMyUserById(myUser.getId())).thenReturn(myUser);
//        customerService.registerCustomer(customerDTO);
//        verify(authRepository,times(1)).findMyUserById(myUser.getId());
//        verify(customerRepository,times(1)).save(customer1);
//    }

    @Test
    public void updateCustomerTest(){
        when(customerRepository.findCustomerById(customer1.getId())).thenReturn(customer1);
        customerService.updateCustomer(customer1.getId(),customer2);
        verify(customerRepository,times(1)).findCustomerById(customer1.getId());
    }

    @Test
    public void deleteCustomerTest(){
        when(customerRepository.findCustomerById(customer1.getId())).thenReturn(customer1);
        customerService.deleteCustomer(customer1.getId());
        verify(customerRepository,times(1)).findCustomerById(customer1.getId());

    }
}

