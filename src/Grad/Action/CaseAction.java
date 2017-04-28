package Grad.Action;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import org.springframework.stereotype.Controller;

import Grad.Bean.CaseDetail;
import Grad.Service.CaseService;
import Grad.Service.caseservice.CaseServiceImpl;


@Controller
public class CaseAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8255879776627973490L;
	
	private File file1;
	private String file1FileName;
	private String file1ContentType;
	
	private String id_list;
	private CaseDetail detail;
	
	private CaseService service;
	
	public String upload() throws ServletException,IOException{
		System.out.println(file1FileName);
		
		init();
		detail = service.uploadCase((String)session.get("username"),file1FileName ,new FileInputStream(file1));
		return SUCCESS;
	}
	
	public String remove() throws ServletException, IOException{
		String[] list = id_list.split(",");
		for(String str:list){
			System.out.println(str);
		}
		init();
		service.deleteCase(list);
		return SUCCESS;
	}
	
	private void init(){
		service = new CaseServiceImpl(request.getRealPath("/"));
	}
	
	public File getFile1() {
		return file1;
	}
	public void setFile1(File file1) {
		this.file1 = file1;
	}
	public String getFile1FileName() {
		return file1FileName;
	}
	public void setFile1FileName(String file1FileName) {
		this.file1FileName = file1FileName;
	}
	public String getFile1ContentType() {
		return file1ContentType;
	}
	public void setFile1ContentType(String file1ContentType) {
		this.file1ContentType = file1ContentType;
	}

	public String getId_list() {
		return id_list;
	}

	public void setId_list(String id_list) {
		this.id_list = id_list;
	}

	public CaseDetail getDetail() {
		return detail;
	}

	public void setDetail(CaseDetail detail) {
		this.detail = detail;
	}
	
}
