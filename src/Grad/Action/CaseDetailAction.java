package Grad.Action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import Grad.Bean.CaseDetail;
import Grad.Bean.CaseJudgeCompare;

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
	
	private List<CaseJudgeCompare> always_like;
	private List<CaseJudgeCompare> sometimes_like;
	private List<CaseJudgeCompare> seldom_like;
	
	public String access() throws ServletException, IOException {
		init();
		System.out.println(id);
		detail = service.getCaseByTitle(id);
		
		getJudgeCompare();
		
		return SUCCESS;
	}
	
	private void getJudgeCompare(){
		CaseJudgeCompare c1 = new CaseJudgeCompare("（2008）东民初字第2614号","2",1,"自由","2003");
		CaseJudgeCompare c2 = new CaseJudgeCompare("1","3",2,"平等","2004");
		CaseJudgeCompare c3 = new CaseJudgeCompare("1","4",3,"博爱","2005");
		List<CaseJudgeCompare> res = new ArrayList<CaseJudgeCompare>();
		res.add(c1);
		res.add(c2);
		res.add(c3);
		choose_compare(res);
	}
	
	private void choose_compare(List<CaseJudgeCompare> list){
		
		always_like = new ArrayList<CaseJudgeCompare>();
		sometimes_like = new ArrayList<CaseJudgeCompare>();
		seldom_like = new ArrayList<CaseJudgeCompare>();
		
		for(CaseJudgeCompare c:list){
			if(c.getAlike()==1){
				always_like.add(c);
			}else if(c.getAlike()==2){
				sometimes_like.add(c);
			}else{
				seldom_like.add(c);
			}
		}
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
	
	private void init(){
		service = new CaseServiceImpl(request.getRealPath("/"));
	}

	public List<CaseJudgeCompare> getAlways_like() {
		return always_like;
	}

	public void setAlways_like(List<CaseJudgeCompare> always_like) {
		this.always_like = always_like;
	}

	public List<CaseJudgeCompare> getSometimes_like() {
		return sometimes_like;
	}

	public void setSometimes_like(List<CaseJudgeCompare> sometimes_like) {
		this.sometimes_like = sometimes_like;
	}

	public List<CaseJudgeCompare> getSeldom_like() {
		return seldom_like;
	}

	public void setSeldom_like(List<CaseJudgeCompare> seldom_like) {
		this.seldom_like = seldom_like;
	}
	
	
}
