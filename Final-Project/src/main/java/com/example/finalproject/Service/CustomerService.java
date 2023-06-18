package com.example.finalproject.Service;


import com.example.finalproject.ApiException.ApiException;
import com.example.finalproject.DTO.CustomerDTO;
import com.example.finalproject.Model.Customer;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Model.Provider;
import com.example.finalproject.Model.Request;
import com.example.finalproject.Repository.AuthRepository;
import com.example.finalproject.Repository.CustomerRepository;
import com.example.finalproject.Repository.RequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AuthRepository authRepository;
    private final RequestRepository requestRepository;

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer getCustomer(MyUser myUser){
        return customerRepository.findCustomerById(myUser.getId());
    }
    public void registerCustomer(CustomerDTO dto){
        String hash=new BCryptPasswordEncoder().encode(dto.getPassword());
        dto.setPassword(hash);
        MyUser myUser=new MyUser(null, dto.getUsername(), dto.getPassword(),"CUSTOMER",null,null);

        Customer customer=new Customer(myUser.getId(), dto.getName(), myUser.getUsername(), myUser.getPassword(), dto.getEmail(), dto.getPhoneNumber(),myUser,null);
        myUser.setCustomer(customer);
        customerRepository.save(customer);
        authRepository.save(myUser);
    }

    public void updateCustomer(Integer customerId,Customer customer){
        Customer oldCustomer= customerRepository.findCustomerById(customerId);
        if (oldCustomer==null){
            throw new ApiException("can't updated,invalid");
        }
        oldCustomer.setName(customer.getName());
        oldCustomer.setUsername(customer.getUsername());
        oldCustomer.setEmail(customer.getEmail());
        oldCustomer.setPhoneNumber(customer.getPhoneNumber());
        customerRepository.save(oldCustomer);
    }
    public void deleteCustomer(Integer id){

        Customer customer1= customerRepository.findCustomerById(id);
        if (customer1==null){
            throw new ApiException("Invalid");
        }
        customerRepository.delete(customer1);

    }

    public void cancelRequest(Integer customerId, Integer requestId){
        Customer customer = customerRepository.findCustomerById(customerId);
        Request request = requestRepository.findRequestById(requestId);
        if(customer == null || request == null || request.getCustomer() != customer)
            throw new ApiException("Invalid");
        request.setStatus("Canceled");
        requestRepository.save(request);
    }

    public Set<Request> getRequestsOfCustomer(Integer id){
        Customer customer = customerRepository.findCustomerById(id);
        if (customer == null)
            throw new ApiException("Invalid");
        return customer.getRequestSet();
    }

    public List<Request> getActiveRequest(MyUser myUser){
        Customer customer = customerRepository.findCustomerById(myUser.getId());
        if (customer==null){
            throw new ApiException("invalid");
        }
        return requestRepository.findActiveRequestsByStatusAndCustomer("New","Accepted", myUser.getId());
    }
    public List<Request> getCompleteRequest(MyUser myUser){
        Customer customer = customerRepository.findCustomerById(myUser.getId());
        if (customer==null){
            throw new ApiException("invalid");
        }
        return requestRepository.findCompletedRequestsByStatusAndCustomer("Rejected","Canceled","Completed", myUser.getId());
    }
}