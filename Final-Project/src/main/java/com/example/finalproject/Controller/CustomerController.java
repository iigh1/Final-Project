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
    @GetMapping("/get-customer")
    public ResponseEntity getCustomer(@AuthenticationPrincipal MyUser myUser){
        return ResponseEntity.status(200).body(customerService.getCustomer(myUser));
    }

    @PostMapping("/register")
    public ResponseEntity addCustomer(@Valid@RequestBody CustomerDTO dto){
        customerService.registerCustomer(dto);
        return ResponseEntity.status(200).body("Customer added");
    }

    @PutMapping("/update")
    public ResponseEntity updateCustomer(@AuthenticationPrincipal MyUser myUser,@Valid@RequestBody Customer customer){
        customerService.updateCustomer(myUser.getId(),customer);
        return ResponseEntity.status(200).body("Customer updated");
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteCustomer(@AuthenticationPrincipal MyUser myUser){
        customerService.deleteCustomer(myUser.getId());
        return ResponseEntity.status(200).body("Customer deleted");
    }
    @PutMapping("/cancel-request/{requestId}")
    public ResponseEntity cancelRequest(@AuthenticationPrincipal MyUser myUser,@Valid@PathVariable Integer requestId){
        customerService.cancelRequest(myUser.getId(),requestId);
        return ResponseEntity.status(200).body("Cancel Request");
    }

    @GetMapping("/get-requests")
    public ResponseEntity getRequestsOfCustomer(@AuthenticationPrincipal MyUser myUser){
        return ResponseEntity.status(200).body(customerService.getRequestsOfCustomer(myUser.getId()));
    }

    @GetMapping("/get-active")
    public ResponseEntity getActiveRequest(@AuthenticationPrincipal MyUser myUser){
        return ResponseEntity.status(200).body(customerService.getActiveRequest(myUser));
    }

    @GetMapping("/get-completed")
    public ResponseEntity getCompleteRequest(@AuthenticationPrincipal MyUser myUser){
        return ResponseEntity.status(200).body(customerService.getCompleteRequest(myUser));
    }
}