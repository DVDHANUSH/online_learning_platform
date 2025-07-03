package com.order.service;
import com.order.service.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface OrderRepo extends JpaRepository<Order,Long> {
    Order findByRazorpayOrderId(String razorPayId);
}