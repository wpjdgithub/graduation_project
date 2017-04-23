package Grad.Interceptor;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import Grad.Action.BaseAction;

public class LoginInterceptor extends AbstractInterceptor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1318100702155939840L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		// TODO Auto-generated method stub
		
		HttpSession session = ServletActionContext.getRequest().getSession(); 
		if( session==null || session.getAttribute("username")==null){
			System.out.println("尚未登录");
			return Action.LOGIN;
		}else{
			System.out.println("已登录"+session.getAttribute("username"));
			return invocation.invoke();
		}
	}

}
