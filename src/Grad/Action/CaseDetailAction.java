package Grad.Action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;

import Grad.Bean.CaseBrief;
import Grad.Bean.CaseDetail;
import Grad.Bean.CaseParagraph;
import Grad.Bean.CaseRelation;
import Grad.Bean.Sentence;
import Grad.Service.CaseService;
import Grad.Service.caseservice.CaseServiceImpl;

public class CaseDetailAction extends BaseAction {
	
	private CaseService service;
	/**
	 * 
	 */
	private static final long serialVersionUID = -62519822120607250L;
	
	private String id;
	private CaseDetail detail;
	
	public String access() throws ServletException, IOException {
		service = new CaseServiceImpl(request.getRealPath("/"));
		System.out.println(id);
		detail = service.getCaseByTitle(id);
		
		return SUCCESS;
	}

	public CaseDetail getDetail() {
		return detail;
	}

	public void setDetail(CaseDetail detail) {
		this.detail = detail;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
