package Grad.Service.caseservice;

import java.io.InputStream;
import java.util.ArrayList;

import Grad.Bean.CaseDetail;
import Grad.Bean.CaseMinMes;
import Grad.Service.CaseService;

public class CaseServiceImpl implements CaseService{
	
	private String path;
	
	public CaseServiceImpl(String path){
		this.path = path;
	}

	@Override
	public boolean uploadCase(String username, InputStream in) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<CaseMinMes> getCaseByUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CaseDetail getCaseByTitle(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
