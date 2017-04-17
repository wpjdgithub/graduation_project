package Grad.Service.dataservice.impl;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;

import Grad.Service.dataservice.WenshuDataService;
import Grad.Service.searchservice.lucene.SearchFiles;
import Grad.Service.wenshu.Wenshu;
import Grad.Service.xml.WenshuXMLObject;

public class WenshuDataServiceImpl implements WenshuDataService{
	
	private String path = "F:/Programming.Project/data/";
	
	public WenshuDataServiceImpl(){
		
	}
	
	public WenshuDataServiceImpl(String path){
		this.path = path;
	}
	
	public void setPath(String path){
		this.path = path;
	}

	@Override
	public List<Wenshu> getAllWenshuData() {
		List<Wenshu> list = new LinkedList<Wenshu>();
		File rootDataDir = new File(this.path+"wenshudata\\");
		File[] mainDir = rootDataDir.listFiles();
		for(int i = 0;i < mainDir.length;i++){
			if(!mainDir[i].isDirectory())
				continue;
			File[] detailDir = mainDir[i].listFiles();
			for(int j = 0;j < detailDir.length;j++){
				if(!detailDir[j].isDirectory())
					continue;
				String[] filename = detailDir[j].list();
				for(int k = 0;k < filename.length;k++){
					String filepath = this.path+"wenshudata\\"+mainDir[i].getName()+"\\"
							+detailDir[j].getName()+"\\"+filename[k];
					System.out.println(filepath);
					WenshuXMLObject wenshuXML = new WenshuXMLObject(filepath);
					Wenshu wenshu = wenshuXML.toWenshu();
					list.add(wenshu);
				}
			}
		}
		return list;
	}

	@Override
	public List<Wenshu> getSmallTestSet(String dirname) {
		List<Wenshu> list = new LinkedList<Wenshu>();
		File dir0 = new File(this.path+"wenshudata\\"+dirname+"\\");
		File[] dir1 = dir0.listFiles();
		for(File dir:dir1){
			if(!dir.isDirectory()){
				continue;
			}
			String[] filenames = dir.list();
			for(String filename:filenames){
				String filepath =this.path + "wenshudata\\"+dir0.getName()+"\\"+dir.getName()+"\\"+filename;
				System.out.println(filepath);
				WenshuXMLObject wenshuXML = new WenshuXMLObject(filepath);
				Wenshu wenshu = wenshuXML.toWenshu();
				list.add(wenshu);
			}
		}
		return list;
	}

	@Override
	public Wenshu getWenshuByCaseID(String caseID) {
		SearchFiles searchTool = new SearchFiles(this.path);
		Wenshu wenshu = null;
		try {
			wenshu = searchTool.search(caseID);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return wenshu;
	}

}
