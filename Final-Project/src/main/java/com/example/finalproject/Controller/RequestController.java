package com.example.finalproject.Controller;

import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Model.Request;
import com.example.finalproject.Service.RequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/request")
public class RequestController {

    private final RequestService requestService;

    @GetMapping("/get")
    public ResponseEntity getAll(){
        return ResponseEntity.status(200).body(requestService.getAll());
    }

    @PostMapping("/add/{serviceId}")
    public ResponseEntity addRequest(@AuthenticationPrincipal MyUser user, @Valid @RequestBody Request request, @PathVariable Integer serviceId){
        requestService.addRequest(user, request, serviceId);
        return ResponseEntity.status(200).body("Request added");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateRequest(@AuthenticationPrincipal MyUser user, @Valid @RequestBody Request request, @PathVariable Integer id){
        requestService.updateRequest(user, request, id);
        return ResponseEntity.status(200).body("Request updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteRequest(@AuthenticationPrincipal MyUser user,@PathVariable Integer id){
        requestService.deleteRequest(user, id);
        return ResponseEntity.status(200).body("Request canceled");
    }

    @PutMapping("/gift/{requestId}/{customerId}")
    public ResponseEntity gift(@AuthenticationPrincipal MyUser user,@PathVariable Integer requestId,@PathVariable Integer customerID){
        requestService.gift(user, requestId, customerID);
        return ResponseEntity.status(200).body("Gift has been sent");
    }
}
