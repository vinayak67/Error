package com.cts.controller;

import java.security.Principal;
import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cts.entity.CheckupRequest;
import com.cts.entity.Customer;
import com.cts.entity.Doctor;
import com.cts.entity.Services;
import com.cts.service.CustomerService;
import com.cts.service.UserLoginService;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	UserLoginService userLoginService;
	
	@GetMapping("/allCustomers")
	public String allCustomers(Model model) {
		List<Customer> list=customerService.allCustomer();
		model.addAttribute("list",list);
		return "allcustomers";
	}
	
	@GetMapping("/updateCustomer")
	public String updateCustomer(Principal principal,Model model){
		String username= principal.getName();
		Customer customer= customerService.getCustomer(username);
		model.addAttribute("customer",customer);
		return "updateCustomer";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer customer){
		System.out.println("in save");
		customerService.registerUser(customer);
		return "redirect:/customer/servicelist";
	}
	
	@PostMapping("/saveAdminCustomer")
	public String saveAdminCustomer(@ModelAttribute("customer") Customer customer) {
		customerService.registerUser(customer);
		return "redirect:/customer/allCustomers";
	}
	
	@GetMapping("/updateCustomerAdmin")
	public String updateCustomer(Model model,@RequestParam("id") int id) {
		Customer customer= customerService.getCustomerById(id);
		model.addAttribute("customer",customer);
		return "admincustomerupdate";
	} 
	
	@GetMapping("/viewcustomerdetailse")
	public String viewcustomerdetailse(@RequestParam("id") int id,Model model) {
		System.out.println(id+"Error is here");
		Customer customer= customerService.getCustomerById(id);
		model.addAttribute("customer",customer);
		return "admincustomer";
	}
	
	@GetMapping("/servicelist")
	public String serviceList(Model model,Principal principal) {
		
		String username= principal.getName();
		List<Services> list = customerService.serviceList();
		Customer customer = customerService.getCustomer(username);
		System.out.println("service"+list);
		model.addAttribute("customer", customer);
		model.addAttribute("service",list);
		return "CustomerHome";
	}
	
	@GetMapping("/doctorList")
public String doctorList(Model model) {
		
		List<Doctor> list = customerService.doctorList();
		System.out.println("doctor"+list);
		model.addAttribute("doctor",list);
		return "doctorList";
	}
	
	@GetMapping("/viewMakeRequests")
	public String makeRequest(Model model){
		return "requestForm";
		
	}
	
	@PostMapping("/saveRequest")
	public String saveRequest(@RequestParam("doctorid") String doctorid, @RequestParam("appointmentdate") Date date,
			@RequestParam("medicare") int serviceid,Principal principal){
		String username= principal.getName();
		Customer customer = customerService.getCustomer(username);
		int cid= customer.getId();
		CheckupRequest checkupRequest= new CheckupRequest(cid,doctorid,serviceid,date);
		customerService.saveRequest(checkupRequest);
		return "redirect:/customer/servicelist";
	}
	
	
	@GetMapping("/viewRequest")
	public String viewDoctorRequests(Principal principal,Model theModel)
	{
		/*Doctor  doctor =doctorService.getDoctorRequests(Id);*/
		String username = principal.getName();
		List<CheckupRequest> list =customerService.getRequest(username);
		System.out.println(list);
		theModel.addAttribute("requestlist",list);
		
		return "customerRequest";
	
	}
	
	
	@GetMapping("/viewReports")
	public String viewReports(Model theModel,Principal principal)
	{
		String username = principal.getName();
		Customer customer=customerService.getCustomer(username);
		int id= customer.getId();
		List<CheckupRequest> results = customerService.getReports(id);
		System.out.println(results+"reports~~~~~");
		theModel.addAttribute("requests",results);
	 
		return "customerReports";
	
	}
}
