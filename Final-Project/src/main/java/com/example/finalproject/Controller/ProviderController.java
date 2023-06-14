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

    @PostMapping("/register")
    public ResponseEntity addProvider(@RequestBody ProviderDTO dto){
          providerService.addProvider(dto);
        return ResponseEntity.status(200).body("provider register");
    }
    @PutMapping("/updated/{id}")
    public ResponseEntity updateProvider(@AuthenticationPrincipal MyUser myUser, @RequestBody Provider provider,@PathVariable Integer id){
        providerService.updateProvider(myUser.getId(),provider,id);
        return ResponseEntity.status(200).body("provider updated");
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProvider(@AuthenticationPrincipal MyUser myUser,@PathVariable Integer id){
        providerService.deleteProvider(id);
        return ResponseEntity.status(200).body("provider deleted");
    }
}
