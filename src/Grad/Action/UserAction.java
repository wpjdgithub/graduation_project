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

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}
	
	public String register() throws ServletException,IOException{
		return INPUT;
	}
	
	public String login() throws ServletException,IOException{
		session.put("user",user);
		return SUCCESS;
	}

}
