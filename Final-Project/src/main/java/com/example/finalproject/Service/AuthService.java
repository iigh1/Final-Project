package com.example.finalproject.Service;

import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Repository.AuthRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthService {


    private final AuthRepository authRepository;


    public List<MyUser> getAllUser(){
        return authRepository.findAll();
    }

    public void register(MyUser myUser){
        String hash=new BCryptPasswordEncoder().encode(myUser.getPassword()); //ENCRYPT
        myUser.setPassword(hash);
        myUser.setRole("CUSTOMER");
        authRepository.save(myUser);
    }


    //as provider
//    public void registerAsProvider(MyUser myUser){
//        String hash=new BCryptPasswordEncoder().encode(myUser.getPassword()); //ENCRYPT
//        myUser.setPassword(hash);
//        myUser.setRole("PROVIDER");
//        authRepository.save(myUser);
//    }

}
