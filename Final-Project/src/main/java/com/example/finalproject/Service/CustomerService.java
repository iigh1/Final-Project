package com.example.finalproject.Service;


import com.example.finalproject.ApiException.ApiException;
import com.example.finalproject.DTO.CustomerDTO;
import com.example.finalproject.Model.Customer;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Repository.AuthRepository;
import com.example.finalproject.Repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AuthRepository authRepository;


    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public void registerCustomer(CustomerDTO dto){
    String hash=new BCryptPasswordEncoder().encode(dto.getPassword());
    dto.setPassword(hash);
    MyUser myUser=new MyUser(null, dto.getUsername(), dto.getPassword(),"CUSTOMER",null,null);

    Customer customer=new Customer(myUser.getId(), dto.getName(), myUser.getUsername(), myUser.getPassword(), dto.getEmail(), dto.getPhoneNumber(), 0,false,myUser,null);
    myUser.setCustomer(customer);
    customerRepository.save(customer);
    authRepository.save(myUser);
}

    public void updateCustomer(Integer customerId,Customer customer,Integer auth){
        Customer oldCustomer= customerRepository.findCustomerById(customerId);
        MyUser myUser = authRepository.findMyUserById(auth);
        if (oldCustomer==null){
            throw new ApiException("can't updated,wrong id");
        }

        oldCustomer.setName(customer.getName());
        oldCustomer.setUsername(customer.getUsername());
        oldCustomer.setEmail(customer.getEmail());
        oldCustomer.setPhoneNumber(customer.getPhoneNumber());
        oldCustomer.setPoints(customer.getPoints());
        oldCustomer.setLoyalty(customer.isLoyalty());
        customerRepository.save(oldCustomer);
    }
    public void deleteCustomer(Integer id){

        Customer customer1= customerRepository.findCustomerById(id);
        if (customer1==null){
            throw new ApiException("wrong id");
        }
        customerRepository.delete(customer1);

    }

}
