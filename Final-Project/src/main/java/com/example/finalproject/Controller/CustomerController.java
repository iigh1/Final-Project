package com.example.finalproject.Controller;


import com.example.finalproject.DTO.CustomerDTO;
import com.example.finalproject.Model.Customer;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


    @GetMapping("/get")
    public ResponseEntity getAll(){
        return ResponseEntity.status(200).body(customerService.getAllCustomers());
    }

    @PostMapping("/register")
    public ResponseEntity addCustomer(@RequestBody CustomerDTO dto){
        customerService.registerCustomer(dto);
        return ResponseEntity.status(200).body("Customer added");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateCustomer(@AuthenticationPrincipal MyUser myUser,@RequestBody Customer customer,@PathVariable Integer id){
        customerService.updateCustomer(myUser.getId(),customer,id);
        return ResponseEntity.status(200).body("Customer updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCustomer(@PathVariable Integer id){
        customerService.deleteCustomer(id);
        return ResponseEntity.status(200).body("Customer deleted");
    }
}
