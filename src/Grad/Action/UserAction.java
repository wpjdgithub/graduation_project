package Grad.Action;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.stereotype.Controller;

import Grad.Bean.CaseMinMes;
import Grad.Bean.UserInfo;
import Grad.Service.UserSerivice;
import Grad.Service.userservice.UserServiceImpl;

@Controller
public class UserAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UserInfo user;
	private String result;
	private List<CaseMinMes> caselist;
	
	private UserSerivice service;

	public String register() throws ServletException,IOException{
		init();
		boolean exist = service.register(user);
		System.out.println(user.getName());
		if(exist){
			this.setResult("success");
		}else{
			this.setResult("fail");
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String login() throws ServletException,IOException{
		init();
		boolean exist = service.login(user);
		System.out.println(user.getUsername());
		if(exist){
			session.put("username",user.getUsername());
			this.setResult("success");
		}else{
			this.setResult("fail");
		}
		return SUCCESS;
	}
	
	public String i_mes() throws ServletException,IOException{
		
		init();
		String username = (String) session.get("username");
		user = service.getInfo(username);
		caselist = service.getMinMes(username);
		
		if(caselist==null || caselist.isEmpty()){
			this.setResult("false");
		}else{
			this.setResult("true");
		}
		
		return SUCCESS;
	}
	
	
	@SuppressWarnings("unchecked")
	public String logout() throws ServletException,IOException{
		session.put("username", null);
		this.setResult("success");
		return SUCCESS;
	}
	
	private void init(){
		service = new UserServiceImpl();
	}
	
	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<CaseMinMes> getCaselist() {
		return caselist;
	}

	public void setCaselist(List<CaseMinMes> caselist) {
		this.caselist = caselist;
	}

}
