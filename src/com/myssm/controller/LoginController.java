package com.myssm.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.codehaus.xfire.XFire;
import org.codehaus.xfire.XFireFactory;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.myssm.entity.Member;
import com.myssm.service.MemberService;
import com.myssm.util.MD5Util;
import com.myssm.xfire.IBankService;

@Controller
@RequestMapping("login")
@Scope(value = "prototype")
public class LoginController extends BaseController {
	// 缺少@Resource这个这个MemberService一直报空指针，折腾一下午 2016.8.21
	@Resource
	private MemberService memberService;
	private static final Logger logger = LoggerFactory
			.getLogger(MemberController.class);
	/*@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private ManagementService managerService;
	@Autowired
	private IdentityService identityService;
	@Autowired
	private FormService formService;
*/
	@RequestMapping("/index")
	public String index() {
		return "login/index";
	}

	
	@RequestMapping("/login")
	public String login(Member member, Model model, HttpServletRequest req) {

		try {
			UsernamePasswordToken token = new UsernamePasswordToken(
					member.getUser(), MD5Util.MD5(member.getPassword()));

			Subject currentMember = SecurityUtils.getSubject();
			if (!currentMember.isAuthenticated()) {
				// 使用shiro来验证
				token.setRememberMe(true);
				currentMember.login(token);// 验证角色和权限
				HttpSession session = req.getSession();
				session.setAttribute("name", member.getUser());
			}
			//testTransaction();
			
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return "login/index";
		}
		return "index/index";
	}

	

	@RequestMapping("login/reg")
	public String reg(HttpServletRequest req) throws Exception {

		memberService.regMember(req.getParameter("member"),
				req.getParameter("passwd"), req.getParameter("qq"));

		return "login/index";

	}

	

	// 以下为测试方法
	private void testXfireLocal() throws Exception {
		// xfire本地调用

		int s = callWebService(1, 2);
		System.out.println(s);

	}

	public int callWebService(int a, int b) throws Exception {
		// Create a metadata of the service 创建一个service的元数据
		Service serviceModel = new ObjectServiceFactory()
				.create(IBankService.class);

		// Create a proxy for the deployed service 为XFire获得一个代理工厂那个对象
		XFire xfire = XFireFactory.newInstance().getXFire();
		XFireProxyFactory factory = new XFireProxyFactory(xfire);

		// 得到一个服务的本地代理
		String serviceUrl = "http://127.0.0.1:8080/MySSM/services/Banking";

		IBankService client = null;

		try {
			client = (IBankService) factory.create(serviceModel, serviceUrl);

		} catch (Exception e) {
			System.out.println("WsClient.callWebService():Exception:"
					+ e.toString());

		}

		// invoke the service 调用服务 返回状态结果
		int serviceResponse = 0;
		try {
			serviceResponse = client.c(a, b);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return serviceResponse;
	}

	private void testXfireRemote() throws Exception {
		// xfire远程调用 XmlSchema JAR包冲突 去掉旧的 不能用此方法调用
		// XmlSchema-1.4.7.jar（旧）和xmlschema-core-2.2.1.jar（新）

		org.codehaus.xfire.client.Client client = new org.codehaus.xfire.client.Client(
				new URL("http://127.0.0.1:8080/MySSM/services/Banking?wsdl"));
		Object[] result = client.invoke("c", new Object[] { 3, 4 });
		System.out.println(result[0]);

	}

	private void testCxf() throws Exception {
		// cxf 版本太高,2.0可以使用XmlSchema-1.4 ,2.7使用xmlschema-core-2.2
		// 不依赖服务器端接口来完成调用的，也就是不仅仅能调用Java的接口

		JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory
				.newInstance();
		org.apache.cxf.endpoint.Client client = (org.apache.cxf.endpoint.Client) clientFactory
				.createClient("http://127.0.0.1:8080/MySSM/webservice/HelloWorld?wsdl");
		Object[] result = client.invoke("say", new Object[] { "lw" });
		System.out.println(result[0]);
	}

	private void testActiviti() {

		/*// 面试题目和答案
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("姓名", "程序员");
		variables.put("职务", "高级软件工程师");
		variables.put("语言", "Java/C#");
		variables.put("操作系统", "Window,Linux，unix，Aix");
		variables.put("工作地点", "苏州高新技术软件园");

		// start process instance
		// 获取创建一个实例
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(
				"DeveloperWorkExam", variables);
		assert (pi != null);

		List<Execution> executions = runtimeService.createExecutionQuery()
				.list();
		assert (executions.size() == 1);
		// 执行开发技术知识面试业务
		Execution execution = runtimeService.createExecutionQuery()
				.singleResult();
		runtimeService.setVariable(execution.getId(), "type", "receiveTask");
		runtimeService.signal(execution.getId());

		executions = runtimeService.createExecutionQuery().list();
		assert (executions.size() == 1);
		// 执行人事面试业务
		execution = executions.get(0);
		runtimeService.setVariable(execution.getId(), "oper", "录用此人....");
		runtimeService.signal(execution.getId());*/
	}

	private void testSendMQ() {
		// activemq-all-5.10.0.jar和 slf4j-log4j12-1.7.7.jar冲突 删掉后者
		// startMQ bin\win64\activemq.bat
		// testPort cmd netstat -an|find "61616"
		// http://127.0.0.1:8161/admin/queues.jsp
		// ConnectionFactory ：连接工厂，JMS 用它创建连接
		ConnectionFactory connectionFactory; // Connection ：JMS 客户端到JMS
		// Provider 的连接
		Connection connection = null; // Session： 一个发送或接收消息的线程
		Session session; // Destination ：消息的目的地;消息发送给谁.
		Destination destination; // MessageProducer：消息发送者
		MessageProducer producer; // TextMessage message;
		// 构造ConnectionFactory实例对象，此处采用ActiveMq的实现jar
		connectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");
		try { // 构造从工厂得到连接对象
			connection = connectionFactory.createConnection();
			// 启动
			connection.start();
			// 获取操作连接
			session = connection.createSession(Boolean.TRUE,
					Session.AUTO_ACKNOWLEDGE);
			// 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
			destination = session.createQueue("FirstQueue");
			// 得到消息生成者【发送者】
			producer = session.createProducer(destination);
			// 设置不持久化，此处学习，实际根据项目决定
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			// 构造消息，此处写死，项目就是参数，或者方法获取
			sendMessage(session, producer);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != connection)
					connection.close();
			} catch (Throwable ignore) {
			}
		}
	}

	public static void sendMessage(Session session, MessageProducer producer)
			throws Exception {
		for (int i = 1; i <= 5; i++) {
			TextMessage message = session.createTextMessage("ActiveMq 发送的消息"
					+ i);
			// 发送消息到目的地方

			System.out.println("发送消息：" + "ActiveMq 发送的消息" + i);
			producer.send(message);
		}
	}

	private void testReceiveMQ() {

		// ConnectionFactory ：连接工厂，JMS 用它创建连接
		ConnectionFactory connectionFactory;
		// Connection ：JMS 客户端到JMS Provider 的连接
		Connection connection = null;
		// Session： 一个发送或接收消息的线程
		Session session;
		// Destination ：消息的目的地;消息发送给谁.
		Destination destination;
		// 消费者，消息接收者
		MessageConsumer consumer;
		connectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");
		try {
			// 构造从工厂得到连接对象
			connection = connectionFactory.createConnection();
			// 启动
			connection.start();
			// 获取操作连接
			session = connection.createSession(Boolean.FALSE,
					Session.AUTO_ACKNOWLEDGE);
			// 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
			destination = session.createQueue("FirstQueue");
			consumer = session.createConsumer(destination);
			while (true) {
				// 设置接收者接收消息的时间，为了便于测试，这里谁定为100s
				TextMessage message = (TextMessage) consumer.receive(100000);
				if (null != message) {
					System.out.println("收到消息" + message.getText());
				} else {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != connection)
					connection.close();
			} catch (Throwable ignore) {
			}
		}		
	}

    public void testTransaction(){  
        List<Member> members = new ArrayList<Member>();  
        for(int i=1;i<5;i++){  
            Member member = new Member();
            members.add(member);
        }  
        this.memberService.insertMember(members);  
    }  
}
