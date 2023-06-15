package com.example.finalproject.Repository;

import com.example.finalproject.Model.MyService;
import com.example.finalproject.Model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyServiceRepository extends JpaRepository<MyService,Integer> {

    MyService findMyServiceById (Integer id);

    @Query("select s from MyService s where s.provider = ?1")
    List<MyService> findMyServicesByProvider(Provider provider);

    List<MyService> findMyServicesByCategory(String category);

    @Query("select s from MyService s where s.price = ?1 or s.price > ?1")
    List<MyService> findMyServicesByPrice(Double price);

    @Query("select s from MyService s where s.rating = ?1 or s.rating > ?1")
    List<MyService> findMyServicesByRating(Double rate);

}
