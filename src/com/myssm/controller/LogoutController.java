package com.myssm.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("logout")
@Scope(value = "prototype")
public class LogoutController extends BaseController {

	@RequestMapping("/index")
	public String index() {
		Subject currentMember = SecurityUtils.getSubject();
		currentMember.logout();
		return "login/index";
	}

	
}
