package com.example.finalproject.Repository;

import com.example.finalproject.Model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request,Integer> {

    Request findRequestById(Integer id);

    @Query("select r from Request r where r.status=?1 and r.provider.id = ?2")
    List<Request> findRequestsByStatus(String status, Integer id);

    @Query("select r from Request r where (r.status=?1 or r.status=?2) and r.customer.id = ?3")
    List<Request> findActiveRequestsByStatusAndCustomer(String status1,String status2, Integer id);

    @Query("select r from Request r where (r.status=?1 or r.status=?2 or r.status=?3) and r.customer.id = ?4")
    List<Request> findCompletedRequestsByStatusAndCustomer(String status1,String status2,String status3, Integer id);

    @Query("select r from Request r where (r.status=?1 or r.status=?2 or r.status=?3) and r.provider.id = ?4")
    List<Request> findCompletedRequestsByStatusAndProvider(String status1,String status2,String status3, Integer id);
}
