package Grad.Action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import Grad.Bean.CaseMinMes;
import Grad.Bean.UserInfo;

@Controller
public class UserAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UserInfo user;
	private String result;
	private ArrayList<CaseMinMes> caselist;

	public String register() throws ServletException,IOException{
		boolean exist = false;
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
		boolean exist = true;
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
		user = new UserInfo("user1", "name1");
		
		caselist = new ArrayList<CaseMinMes>();
		caselist.add(new CaseMinMes("title1","2012/123/13"));
		caselist.add(new CaseMinMes("title2","2012/a/asd"));
		caselist.add(new CaseMinMes("title3","2012/dq/safd"));
		this.setResult("asdsdgsdfgfdgh");
		return SUCCESS;
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

	public ArrayList<CaseMinMes> getCaselist() {
		return caselist;
	}

	public void setCaselist(ArrayList<CaseMinMes> caselist) {
		this.caselist = caselist;
	}

}
