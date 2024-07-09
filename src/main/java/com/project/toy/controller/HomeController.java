package com.project.toy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
	
	@GetMapping("/page/home/main")
	public String showMain() {
		return "page/home/main";
	}
	
	@GetMapping("/")
	public String showRoot() {
		return "redirect:/page/home/main";
	}
	
}