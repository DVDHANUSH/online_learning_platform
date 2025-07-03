package com.order.service.controllers;
import com.order.service.OrderDetail;
import com.order.service.OrderRepo;
import com.order.service.dtos.OrderRequestDto;
import com.order.service.dtos.PaymentVerifyDto;
import com.order.service.entities.Order;
import com.order.service.services.OrderService;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/orders")
@CrossOrigin("*")
public class OrderController {
    private OrderService orderService;
    private OrderRepo orderRepo;
    @Autowired
    private StreamBridge streamBridge;
    public OrderController(OrderService orderService, OrderRepo orderRepo){
        this.orderService = orderService;
        this.orderRepo = orderRepo;
    }
    @PostMapping("/verify")
    public ResponseEntity<String> verifyPayment(@RequestBody PaymentVerifyDto paymentVerifyDto) throws RazorpayException {
       boolean b = orderService.verifyPayment(paymentVerifyDto.getRazorpayPaymentId(), paymentVerifyDto.getRazorpayOrderId(), paymentVerifyDto.getRazorpaySignature());

       if(b){
           Order order = orderRepo.findByRazorpayOrderId(paymentVerifyDto.getRazorpayOrderId());
           OrderDetail orderDetail= new OrderDetail();
           orderDetail.setCourseId(order.getCourseId());

           orderDetail.setOrderPaymentStatus(true);
           orderDetail.setUserId(order.getUserId());
           orderDetail.setEmail(order.getUserName());
           orderCreatedNotification(new OrderDetail());
           return ResponseEntity.ok("Order Verified");
       }
       else{
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment not verified");
       }
    }
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequestDto orderRequestDto)throws RazorpayException {
        Order order = orderService.createOrder(orderRequestDto);
        return ResponseEntity.ok(order);
    }
    private void orderCreatedNotification(OrderDetail orderDetail) {
        // logic to send notification service
       boolean send = streamBridge.send("orderCreatedEvent-out-0", orderDetail);
       if(send){
           System.out.println("Event has been successfully sent");
       }
    }
}