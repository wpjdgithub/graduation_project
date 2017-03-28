package Grad.Action;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;

import org.springframework.stereotype.Controller;

@Controller
public class CaseAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8255879776627973490L;
	
	private File file1;
	private String file1FileName;
	private String file1ContentType;
	
	public String upload() throws ServletException,IOException{
		System.out.println(file1FileName);
		return SUCCESS;
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
	
}