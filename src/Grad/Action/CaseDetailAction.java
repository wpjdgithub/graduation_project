package Grad.Action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;

import Grad.Bean.CaseBrief;
import Grad.Bean.CaseDetail;
import Grad.Bean.CaseParagraph;
import Grad.Bean.CaseRelation;
import Grad.Bean.Sentence;

public class CaseDetailAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -62519822120607250L;
	
	private CaseDetail detail;
	
	public String access() throws ServletException, IOException {
		CaseBrief brief = new CaseBrief(123214,"标题1","浙江省法院","2017-01-01",
				"案由1","审批程序1","文书类型1","来源1","核心词汇1");
		detail.setBrief(brief);
		ArrayList<CaseParagraph> context = new ArrayList<CaseParagraph>();
		ArrayList<Sentence> s1 = new ArrayList<Sentence>();
		s1.add(new Sentence("句子1",false,""));
		s1.add(new Sentence("句子2",true,"这是句子2的解释"));
		s1.add(new Sentence("句子3",false,""));
		ArrayList<Sentence> s2 = new ArrayList<Sentence>();
		s2.add(new Sentence("句子1",false,""));
		s2.add(new Sentence("句子2",true,"这是句子2的解释"));
		s2.add(new Sentence("句子3",false,""));
		detail.setContext(context);
		ArrayList<CaseRelation> relatedCase = new ArrayList<CaseRelation>();
		
		return SUCCESS;
	}

	public CaseDetail getDetail() {
		return detail;
	}

	public void setDetail(CaseDetail detail) {
		this.detail = detail;
	}
}
