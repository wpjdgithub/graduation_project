package Grad.Action;

public class IndexAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2995375406589162945L;
	
	@SuppressWarnings("unchecked")
	public String index(){
		
		session.put("input", "");
		
		return SUCCESS;
	}

}
