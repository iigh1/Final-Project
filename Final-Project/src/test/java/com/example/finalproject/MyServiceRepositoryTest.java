package com.example.finalproject;

import com.example.finalproject.Model.MyService;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Model.Provider;
import com.example.finalproject.Repository.MyServiceRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MyServiceRepositoryTest {
    @Autowired
    MyServiceRepository myServiceRepository;

    MyService service1, service2, service3;

    MyUser user;

    Provider provider;

    List<MyService> serviceList;

    @BeforeEach
    void setUp(){
        //user = new MyUser(null,"username","password","PROVIDER",null,null);
        //provider = new Provider(null, "provider1","provider","password","pro@gmail.com","1111111111","Makeup","@pro1",0.0,user,null,null);
        service1 = new MyService(null,"service1",null,"Makeup",150.0,null,null,null);
        service2 = new MyService(null,"service2",null,"Makeup",150.0,null,null,null);
        service3 = new MyService(null,"service3",null,"Makeup",150.0,null,null,null);
    }

    @Test
    public void findMyServiceById (){
        myServiceRepository.save(service1);
        service2 = myServiceRepository.findMyServiceById(service1.getId());
        Assertions.assertThat(service2).isEqualTo(service1);
    }

    @Test
    public void  findMyServicesByProvider(){
        myServiceRepository.save(service1);
        myServiceRepository.save(service2);
        myServiceRepository.save(service3);
        serviceList = myServiceRepository.findMyServicesByProvider(user.getId());
        Assertions.assertThat(serviceList.get(0)).isEqualTo(service1);
    }

    @Test
    public void findMyServicesByCategory(){
        myServiceRepository.save(service1);
        myServiceRepository.save(service2);
        myServiceRepository.save(service3);
        serviceList = myServiceRepository.findMyServicesByCategory(service1.getCategory());
        Assertions.assertThat(serviceList.get(0).getCategory()).isEqualTo(service1.getCategory());
    }
}

