package com.myssm.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("index")
@Scope(value = "prototype")
public class IndexController extends BaseController {

	@RequestMapping("/index")
	public String index() {		
		return "index/index";
	}	
}
