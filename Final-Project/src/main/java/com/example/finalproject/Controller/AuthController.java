package com.example.finalproject.Controller;


import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;


    @GetMapping("/get")
    public ResponseEntity getAllUsers(){
        List<MyUser> myUserList=authService.getAllUser();
        return ResponseEntity.status(200).body(myUserList);
    }

    @PostMapping("/admin")
    public ResponseEntity admin(){

        return ResponseEntity.status(200).body("welcome admin");
    }

    @PostMapping("/login")
    public ResponseEntity login(){

        return ResponseEntity.status(200).body("login");
    }

    @PostMapping("/logout")
    public ResponseEntity logout(){

        return ResponseEntity.status(200).body("logout");
    }
    @PostMapping("/customer")
    public ResponseEntity customer(){
        return ResponseEntity.status(200).body("welcome Customer");
    }

    @PostMapping("/provider")
    public ResponseEntity provider(){return ResponseEntity.status(200).body("Welcome Provider");}
}
