package Grad.Action;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.stereotype.Controller;

import com.google.gson.Gson;

import Grad.Bean.CaseCompare;
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
	private String id;
	private String type;
	
	private String compare;
	
	
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
	
	public String compare(){
		getCompareMes();
		System.out.println(id);
		System.out.println(type+"type");
		System.out.println(compare);
		return SUCCESS;
	}
	
	private void getCompareMes(){
		/*CaseCompare c1 = new CaseCompare("1","bt1",12);
		CaseCompare c2 = new CaseCompare("1","bt2",24);
		CaseCompare c3 = new CaseCompare("1","bt3",48);
		*/
		String username = (String) session.get("username");
		List<CaseCompare> compare_user = service.getCompareMes(username, id, Integer.valueOf(type));
		/*compare_user.add(c1);
		compare_user.add(c2);
		compare_user.add(c3);
		*/
		Gson gson = new Gson();
		compare = gson.toJson(compare_user);
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

	public String getCompare() {
		return compare;
	}

	public void setComparer(String compare) {
		this.compare = compare;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
