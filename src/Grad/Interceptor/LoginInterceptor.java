package Grad.Interceptor;

import java.util.Map;

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
		BaseAction action = (BaseAction) invocation.getAction();
		@SuppressWarnings("rawtypes")
		Map session = action.session;
		if( session==null || session.get("username")==null){
			System.out.println("尚未登录");
			return Action.LOGIN;
		}else{
			System.out.println("已登录");
			return invocation.invoke();
		}
	}

}
