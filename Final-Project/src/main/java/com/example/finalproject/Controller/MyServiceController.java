package com.example.finalproject.Controller;

import com.example.finalproject.Model.MyService;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Service.MyServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/service")
public class MyServiceController {

    private final MyServiceService myServiceService;

    @GetMapping("/get")
    public ResponseEntity getAll(){
        return ResponseEntity.status(200).body(myServiceService.getAll());
    }

    @GetMapping("/get-services")
    public ResponseEntity getServices(@AuthenticationPrincipal MyUser user){
        return ResponseEntity.status(200).body(myServiceService.getServices(user));
    }

    @PostMapping("/add")
    public ResponseEntity addService(@AuthenticationPrincipal MyUser user, @Valid @RequestBody MyService service){
        myServiceService.addService(user, service);
        return ResponseEntity.status(200).body("Service added");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateService(@AuthenticationPrincipal MyUser user, @Valid @RequestBody MyService service, @PathVariable Integer id){
        myServiceService.updateService(user, service, id);
        return ResponseEntity.status(200).body("Service updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteService(@AuthenticationPrincipal MyUser user, @PathVariable Integer id){
        myServiceService.deleteService(user, id);
        return ResponseEntity.status(200).body("Service deleted");
    }

    @GetMapping("/get-category/{category}")
    public ResponseEntity getServicesByCategory(@AuthenticationPrincipal MyUser user, @PathVariable String category){
        return ResponseEntity.status(200).body(myServiceService.getServicesByCategory(user.getId(), category));
    }

    @GetMapping("/get-price/{price}")
    public ResponseEntity getServicesByPrice(@AuthenticationPrincipal MyUser user, @PathVariable Double price){
        return ResponseEntity.status(200).body(myServiceService.getServicesByPrice(user.getId(), price));
    }

    @GetMapping("/get-rating/{rate}")
    public ResponseEntity getServicesByRate(@AuthenticationPrincipal MyUser user, @PathVariable Double rate){
        return ResponseEntity.status(200).body(myServiceService.getServicesByRate(user.getId(), rate));
    }
}
