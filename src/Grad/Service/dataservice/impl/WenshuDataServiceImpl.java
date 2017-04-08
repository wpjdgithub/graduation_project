package Grad.Service.dataservice.impl;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import Grad.Service.dataservice.WenshuDataService;
import Grad.Service.wenshu.Wenshu;
import Grad.Service.xml.WenshuXMLObject;

public class WenshuDataServiceImpl implements WenshuDataService{

	@Override
	public List<Wenshu> getAllWenshuData() {
		List<Wenshu> list = new LinkedList<Wenshu>();
		File rootDataDir = new File("wenshudata/");
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
					String filepath = "wenshudata/"+mainDir[i].getName()+"/"
							+detailDir[j].getName()+"/"+filename[k];
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
	public List<Wenshu> getSmallTestSet() {
		List<Wenshu> list = new LinkedList<Wenshu>();
		File rootDataDir = new File("wenshudata/");
		File[] mainDir = rootDataDir.listFiles();
		for(int i = 0;i < mainDir.length;i++){
			if(!mainDir[i].isDirectory())
				continue;
			File[] detailDir = mainDir[i].listFiles();
			for(int j = 0;j < 2;j++){
				if(!detailDir[j].isDirectory())
					continue;
				String[] filename = detailDir[j].list();
				for(int k = 0;k < filename.length;k++){
					String filepath = "wenshudata/"+mainDir[i].getName()+"/"
							+detailDir[j].getName()+"/"+filename[k];
					System.out.println(filepath);
					WenshuXMLObject wenshuXML = new WenshuXMLObject(filepath);
					Wenshu wenshu = wenshuXML.toWenshu();
					list.add(wenshu);
				}
			}
		}
		return list;
	}

}
