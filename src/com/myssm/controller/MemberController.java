package com.myssm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myssm.entity.Member;
import com.myssm.service.MemberService;
import com.myssm.util.DateUtil;
import com.myssm.util.ExcelExport;
import com.myssm.util.ExcelImport;
import com.myssm.util.MD5Util;
import com.myssm.util.MailUtil;
import com.myssm.util.Page;
import com.myssm.util.StringUtil;

@Controller
@RequestMapping("member")
@Scope(value = "prototype")
public class MemberController extends BaseController {
	// 缺少@Resource这个这个memberService一直报空指针，折腾一下午 2016.8.21
	@Resource
	private MemberService memberService;
	private static final Logger logger = LoggerFactory
			.getLogger(MemberController.class);

	@RequestMapping("/index")
	public String index(HttpServletRequest req, Model model) throws Exception {

		Map<String, Object> params = new HashMap<String, Object>();
		// 添加查询条件
		if (StringUtil.isNotNull(req.getParameter("user"))) {
			params.put("user", req.getParameter("user"));
		}
		// 获取总条数
		Long totalCount = memberService.pageCounts(params);
		// 设置分页对象
		Page page = executePage(req, totalCount);
		// 如排序
		if (page.isSort()) {
			params.put("orderName", page.getSortName());
			params.put("descAsc", page.getSortState());
		} else {
			// 没有进行排序,默认排序方式
			params.put("orderName", "uid");
			params.put("descAsc", "asc");
		}
		// 压入查询参数:开始条数与每页条数
		params.put("startIndex",
				page.getBeginIndex() < 0 ? 0 : page.getBeginIndex());
		// params.put("endIndex", page.getgetEndinIndex());
		params.put("pageSize", page.getEveryPage());

		// 查询集合
		List<Member> list = memberService.pageList(params);
		model.addAttribute("list", list);
		return "member/index";

	}

	@RequestMapping("/add")
	public String add(Member member, Model model) {
		// 目录区分大小写
		
		return "member/form";
	}

	@RequestMapping("/edit")
	public String edit(Member m, Model model) throws Exception {

		Member member = memberService.queryMember(m.getUid());

		model.addAttribute("Member", member);
		// 目录区分大小写
		return "member/form";

	}
	/**
	 * 
	 * @title  
	 * @description  
	 * @date  2016-12-5 下午12:37:01 
	 * @author  LW
	 * @modifydate  2016-12-5 下午12:37:01 
	 * @modifier  LW
	 * @param member
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/update")
	public String update(Member member) throws Exception {
		
		if (StringUtil.isNotNull(member.getPassword())) {
			member.setPassword(MD5Util.MD5(member.getPassword()));
		} 
		
		if (StringUtil.isNotNull(member.getUid())) {
			memberService.updateMember(member);
		} else {
			memberService.saveMember(member);
		}

		return "redirect:/member/index";
	}
		
	@RequestMapping("/check")
	public void check(HttpServletRequest req, HttpServletResponse res) throws IOException {
		// 目录区分大小写

		String flag = "true";
		Map<String, Object> params = new HashMap<String, Object>();
		// 添加查询条件
		if (StringUtil.isNotNull(req.getParameter("user"))) {
			params.put("user", req.getParameter("user"));
		}
		// 获取总条数
		Long totalCount = memberService.checkMember(params);
		
		if (totalCount>0){
			flag = "false";
		}
		res.setContentType("text/html");
		res.getWriter().print(flag);
	}
	@RequestMapping("/del")
	public String del(Member member) throws Exception {

		memberService.deleteMember(member.getUid());
		return "forward:/member/index";
	}

	@RequestMapping("/export")
	public String export(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 查询集合
		List<Member> list = memberService.getMemberList();
		String title = DateUtil.getFileName();
		HttpSession session = req.getSession();
		session.setAttribute("title", title);
		String[] headers = { "用户ID","用户名","头像","性别","生日","电话","qq","邮箱","密码","注册时间" };
		String fileName = java.net.URLEncoder.encode(title, "utf-8");
		res.reset();
		res.setCharacterEncoding("utf-8");
		res.setContentType("application/vnd.ms-excel");
		res.addHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

		try {
			ExcelExport<Member> excel = new ExcelExport<Member>();
			excel.export(1, title, headers, list, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@RequestMapping("/import1")
	public String import1(HttpServletRequest req, Model model) throws Exception {
		String path = "";
		if (StringUtil.isNotNull(req.getSession().getAttribute("title"))) {
			path = "C:\\Users\\LW\\Downloads\\" + req.getSession().getAttribute("title").toString() + ".xlsx";
		}
		
		Map<String, String> header = new HashMap<String, String>();
		header.put("用户ID", "uid");
		header.put("用户名", "user");
		header.put("头像", "head");
		header.put("性别", "sex");
		header.put("生日", "birthday");
		header.put("电话", "phone");
		header.put("qq", "qq");
		header.put("邮箱", "email");
		header.put("密码", "password");
		header.put("注册时间", "t");
		
		ExcelImport export = new ExcelImport(header);
		export.init(new FileInputStream(new File(path)));
		List<Member> list = export.bindToModels(Member.class, false);
		if (export.hasError()) {
			System.out.println(export.getError().toString());
		}
		for(Member m : list){
			m.setUid(null);
			memberService.saveMember(m);
		}
		// 目录区分大小写
		return "forward:/member/index";
	}

	@RequestMapping("/mail")
	public String mail() {
		String title = "邮件主题";// 所发送邮件的标题
		String from = "";// 从那里发送
		String sendTo[] = {};// 发送到那里
		// 邮件的文本内容，可以包含html标记则显示为html页面
		String content = "mail test!!!!!!<br><a href=#>aaa</a>";
		// 所包含的附件，及附件的重新命名
		String fileNames[] = {};
		String smtp = "smtp.163.com"; // 设置发送邮件所用到的smtp
		String servername = "";
		String serverpaswd = "";
		try {

			MailUtil.sendmail(smtp, servername, serverpaswd, title, from,
					sendTo, content, fileNames, "text/html;charset=gb2312");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "forward:/member/index";
	}

}
