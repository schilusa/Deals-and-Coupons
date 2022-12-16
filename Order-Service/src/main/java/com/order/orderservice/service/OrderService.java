package com.order.orderservice.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import com.order.orderservice.model.Order;
import com.order.orderservice.model.Payment;
import com.order.orderservice.model.Products;
import com.order.orderservice.repository.OrderRepository;



@Service
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	String url1 = "http://ProductsService/products" ;
	public List<Products> getAlldetails()
	{
	    Products[] products=restTemplate.getForObject(url1+"/list",Products[].class);
	    return (Arrays.asList(products));
	}
	
	String url2 = "http://Payment/payments" ;
	public List<Payment> getAllPayment()
	{
	   Payment[] payment=restTemplate.getForObject(url2+"/list",Payment[].class);
	   return (Arrays.asList(payment));
	}
		   
	
	
	private static final Logger LOGGER=LoggerFactory.getLogger(OrderService.class);

	public List<Order> getAllOrders()   
	{  
	List<Order> orders = new ArrayList<Order>();  
	orderRepository.findAll().forEach(orders1 -> orders.add(orders1));  
	return orders;  
	}  
	

	public List<Order> getOrdersById(int userId)   
	{  
	return  orderRepository.findByUserId(userId);
	
	}  
	

	public Order saveOrUpdate(Order orders)   
	{  
		return orderRepository.save(orders); 
		
	} 
	public void delete(String userId)   
	{  
		orderRepository.deleteById(userId);  
	}  

	public void update(Order orders, String userId)   
	{  
		orderRepository.save(orders);  
	} 
	
	public List<Order> getOrderByStatus(String orderStatus){
		return orderRepository.findByOrderStatus(orderStatus);
	}
	
	
	public Order updateOrderDetails(Order order) {
//		String em = "";
//		List<Address> order1 = order.getAddress();
//		for(int i = 0;i<order1.size();i++) {
//			em = order1.get(i).getEmail();
//			System.out.println(order1.get(i).getEmail());
//		}
//		 String email = em;
//		 System.out.println(email);
		 
		 String s = order.getOrderStatus();
		 System.out.println(s);
		 
//		 if(order.getOrderStatus().equalsIgnoreCase("cancelled")) {
//			 String html = "Sorry!,"+order.getUserId()+ "Your Order has been cancelled";
//			 emailSenderService.sendEmail(email, "Verified Order", html);
//		 }else {
//			 String html ="Your Order has been verified and shipped";
//				emailSenderService.sendEmail(email, "Verified Order", html);
//		 }
		
		
		return orderRepository.save(order);
	}
	
	public String deleteOrderByOrderId(int orderId) {
		orderRepository.deleteByOrderId(orderId);
		return "Successfully Deleted";
	}
	public List<Payment> getAllPayments() {
		return null;
	}


	public List<com.order.orderservice.model.Products> getAllProducts() {
		// TODO Auto-generated method stub
		return null;
	}
	

}