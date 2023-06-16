package com.example.finalproject.Controller;

import com.example.finalproject.DTO.ProviderDTO;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Model.Provider;
import com.example.finalproject.Service.ProviderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/provider")
@AllArgsConstructor
public class ProviderController {

    private final ProviderService providerService;

    @GetMapping("/get")
    public ResponseEntity getAll(){
        return ResponseEntity.status(200).body(providerService.getAll());
    }

    @GetMapping("/get-provider")
    public ResponseEntity getProvider(@AuthenticationPrincipal MyUser myUser, Integer providerId){
        return ResponseEntity.status(200).body(providerService.getProvider(myUser, providerId));
    }

    @PostMapping("/register")
    public ResponseEntity addProvider(@Valid  @RequestBody ProviderDTO dto){
        providerService.addProvider(dto);
        return ResponseEntity.status(200).body("provider register");
    }
    @PutMapping("/update")
    public ResponseEntity updateProvider(@AuthenticationPrincipal MyUser myUser,@Valid @RequestBody Provider provider){
        providerService.updateProvider(myUser.getId(),provider);
        return ResponseEntity.status(200).body("provider updated");
    }
    @DeleteMapping("/delete")
    public ResponseEntity deleteProvider(@AuthenticationPrincipal MyUser myUser){
        providerService.deleteProvider(myUser.getId());
        return ResponseEntity.status(200).body("provider deleted");
    }
    @PutMapping("/discount/{amount}")
    public ResponseEntity discount(@AuthenticationPrincipal MyUser myUser,@PathVariable  Integer amount){
        providerService.discount(myUser,amount);
        return ResponseEntity.status(200).body("discount successful");
    }
    @GetMapping("/get-request")
    public ResponseEntity getRequest(@AuthenticationPrincipal MyUser myUser){
       return ResponseEntity.status(200).body(providerService.getRequest(myUser));

    }

    @GetMapping("/get-service")
    public ResponseEntity getService(@AuthenticationPrincipal MyUser myUser, Integer providerId){
        return ResponseEntity.status(200).body(providerService.getService(myUser, providerId));
    }

    @GetMapping("/get-complete")
    public ResponseEntity getCompletedRequest(@AuthenticationPrincipal MyUser myUser){
        return ResponseEntity.status(200).body(providerService.getCompletedRequest(myUser));

    }
    @GetMapping("/get-new")
    public ResponseEntity getNewRequest(@AuthenticationPrincipal MyUser myUser){
        return ResponseEntity.status(200).body(providerService.getNewRequest(myUser));
    }
    @PutMapping("/accept-request/{requestId}")
    public ResponseEntity acceptRequest(@AuthenticationPrincipal MyUser myUser,@PathVariable Integer requestId){
        providerService.acceptRequest(myUser,requestId);
        return ResponseEntity.status(200).body("accepted Request");
    }
    @PutMapping("/reject-request/{requestId}")
    public ResponseEntity rejectRequest(@AuthenticationPrincipal MyUser myUser,@PathVariable Integer requestId){
        providerService.acceptRequest(myUser,requestId);
        return ResponseEntity.status(200).body("rejected Request");
    }
}
