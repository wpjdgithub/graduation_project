package Grad.Action;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.stereotype.Controller;

import Grad.Bean.UserInfo;

@Controller
public class UserAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UserInfo user;
	private String result;

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

	public String register() throws ServletException,IOException{
		return null;
	}
	
	public String login() throws ServletException,IOException{
		boolean exist = true;
		System.out.println(user.getUsername());
		if(exist){
			
		}else{
			
		}
		
		this.setResult("success");
		return SUCCESS;
	}

}
