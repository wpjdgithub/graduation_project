package Grad.Action;

import java.io.IOException;

import javax.servlet.ServletException;

import Grad.Bean.CaseDetail;

public class CaseDetailAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -62519822120607250L;
	
	private CaseDetail detail;
	
	private String access() throws ServletException, IOException {
		
		return SUCCESS;
	}
}
