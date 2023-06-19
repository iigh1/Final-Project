package com.example.finalproject.Repository;

import com.example.finalproject.Model.MyService;
import com.example.finalproject.Model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderRepository extends JpaRepository<Provider,Integer> {

    Provider findProviderById(Integer id);
    
    List<Provider> findProviderByField(String field);

}
