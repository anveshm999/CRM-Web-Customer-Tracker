package com.anvesh.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.anvesh.springdemo.dao.CustomerDAO;
import com.anvesh.springdemo.entity.Customer;
import com.anvesh.springdemo.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	//need to inject customer service 
	@Autowired
	public CustomerService customerService;
	

	@GetMapping("/list")
	public String listCustomers(Model theModel){
		
		
		//get customers from DAO
		List<Customer> customers = customerService.getCustomers();
		
		//add customers list into the model
		
		theModel.addAttribute("customers",customers);
		
		return "list-customer"; // this will redirect to list-customers.jsp
		
	}
	
	
	@GetMapping("/showFormForAdd")   //when a new customer is added, this will be called and redirected to HTML page for adding
	public String showFormForAdd(Model theModel){
		
		//create model attribute to bind form data
		Customer theCustomer = new Customer();
		
		theModel.addAttribute("customer", theCustomer );
		
		
		return "customer-form";
	}
	
	
	@PostMapping("/saveCustomer") // form action from JSP
	public String saveCustomer(@ModelAttribute("customer") Customer customer){
		
		//save the customer using our service
		customerService.saveCustomer(customer);
		
		return "redirect:/customer/list";
		
	}
	
	@GetMapping("/showFormForUpdate")   //pre-populating the form
	public String showFormForUpdate(@RequestParam("customerId") int theId,Model theModel){
		
		//get the customer form the service
		
		Customer customer = customerService.getCustomer(theId);
		
		//set customer as a model attribute to pre-populate the form
		theModel.addAttribute("customer",customer); // model attribute mapped in JSP in form tag - in background, customer.getfirstname() is called
		
		//send over to form
		
		
		return "customer-form";
	}
	
	@GetMapping("/showFormForDelete")   //deleting the customer
	public String showFormForDelete(@RequestParam("customerId") int theId,Model theModel){
		
		//get the customer form the service
		
		customerService.deleteCustomer(theId);
		return "redirect:/customer/list";
	}
	
	
	
	
}
