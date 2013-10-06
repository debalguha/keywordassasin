package com.affbeastmode.registration.web.spring.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.affbeastmode.kwassasin.model.ConfirmationState;
import com.affbeastmode.kwassasin.model.User;
import com.affbeastmode.kwassasin.service.KeywordService;
import com.affbeastmode.registration.service.MailService;
import com.affbeastmode.registration.service.PaymentService;
import com.affbeastmode.registration.ui.form.NewRegistrationForm;
import com.paypal.api.payments.IPNHandler;

@Controller
@RequestMapping({ "/controller" })
public class RegistrationController {
	@Autowired
	private KeywordService keywordService;
	@Autowired
	private MailService mailService;
	@Autowired
	private PaymentService paymentService;
	@Resource(name = "uuIDUserIdMap")
	private Map<String, String> uuIDUserIdMap;
	@Value("${confirmation.url}")
	private String confirmationURL;
	@Value("${login.url}")
	private String loginURL;	
	@Autowired
	private IPNHandler handler;

	private static final Logger logger = Logger.getLogger(RegistrationController.class);

	@RequestMapping(value = "/register.do", method = RequestMethod.POST)
	public @ResponseBody String doRegister(NewRegistrationForm form, HttpServletRequest request) throws Exception {
		logger.info("Request arrived with form: " + form.toString());
		String email = form.getTxtEmail();
		String password = form.getPassword();
		String timezoneID = form.getTimezoneId();
		UUID uuid = UUID.randomUUID();
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		user.setUserName(email);
		user.setTimezoneId(timezoneID);
		user.setState(ConfirmationState._INITIATE);
		user.setUUID(uuid.toString());
		String status = "FAILED";
		try {
			logger.info("Going to register user.");
			keywordService.registerUser(user);
			logger.info("First face of registration completed. Waiting to receive payment confirmation.");
			//mailService.sendMailTo(user, "Your registration request has been processed. Please click the following link to activate your profile \\n " + confirmationURL + uuid.toString());
			status = uuid.toString();
		} catch (Exception e) {
			logger.error("Error subscribing user.", e);
			e.printStackTrace();
		}
		return status;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/confirm.do", method = RequestMethod.POST)
	public void doConfirmRegistration(@RequestParam(required = true, value = "uuid")final String uuid, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("IPN received");
		final Map<String, String[]> reqMap = new HashMap<String, String[]>();
		Enumeration<String> en = request.getParameterNames();
		while(en.hasMoreElements()){
			String key = en.nextElement();
			String value = request.getParameter(key);
			reqMap.put(key, new String[]{value});
		}
		new Thread(new Runnable(){
			public void run() {
				try {
					handler.handleIPN(reqMap, uuid);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
		response.setStatus(200);
		response.setContentType("text");
		response.getWriter().append("Accepted");
		return ;
	}
	
	@RequestMapping(value = "/forgetPassword.do", method = RequestMethod.GET)
	public void doPasswordReset(@RequestParam(required = true, value = "emailsignup")final String emailsignup, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("Forget Password request received.");
		User user=null;
		try {
			user = keywordService.findUserByUserId(emailsignup);
		} catch (Exception e) {
			logger.error("Unable to retrieve user with email: "+emailsignup, e);
			request.setAttribute("errMsg", "Unable to retrieve user with email: "+emailsignup);
			return;
		}
		mailService.sendMailTo(user, "Your password is "+user.getPassword()+". If you still have difficulty logging in please contact support. The support email is kwassasin-support@gmail.com");
		request.getSession().invalidate();
		response.sendRedirect(loginURL);
	}		
	
	public void setKeywordService(KeywordService keywordService) {
		this.keywordService = keywordService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	public void setUuIDUserIdMap(Map<String, String> uuIDUserIdMap) {
		this.uuIDUserIdMap = uuIDUserIdMap;
	}

	public void setConfirmationURL(String confirmationURL) {
		this.confirmationURL = confirmationURL;
	}

	public void setHandler(IPNHandler handler) {
		this.handler = handler;
	}

	public void setLoginURL(String loginURL) {
		this.loginURL = loginURL;
	}

}
