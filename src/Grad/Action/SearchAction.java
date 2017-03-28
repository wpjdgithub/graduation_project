package Grad.Action;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import Grad.Service.SearchService;

@Controller
public class SearchAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*@Autowired
	@Qualifier("normal")
	private SearchService service_n;
	
	@Autowired
	@Qualifier("advanced")
	private SearchService service_a;
	*/

	public String normal() throws ServletException, IOException {
		return SUCCESS;
	}
	
	public String advanced() throws ServletException, IOException {
		return SUCCESS;
	}
	
	private void pageChange(int page){
		
	}
}
