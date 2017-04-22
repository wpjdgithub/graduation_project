package mllib;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Grad.Service.dataservice.WenshuDataService;
import Grad.Service.dataservice.impl.WenshuDataServiceImpl;
import Grad.Service.wenshu.Wenshu;

public class Stat {
	public static void main(String[] args){
		WenshuDataService wenshuDataService = new WenshuDataServiceImpl();
		List<Wenshu> wenshus = wenshuDataService.getAllWenshuData();
		Map<String,Set<String> > map = new HashMap<String,Set<String> >();
		//统计案由
		for(Wenshu wenshu:wenshus){
			String caseBrief = wenshu.getCaseBrief();
			String caseID = wenshu.getCaseID();
			System.out.println(caseID);
			if(map.containsKey(caseBrief)){
				map.get(caseBrief).add(caseID);
			}
			else{
				Set<String> set = new HashSet<String>();
				set.add(caseID);
				map.put(caseBrief, set);
			}
		}
		Iterator<String> iterator = map.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			System.out.println(key+" "+map.get(key).size());
		}
		//统计法律
		Set<String> laws = new HashSet<String>();
		for(Wenshu wenshu:wenshus){
			List<String> list = wenshu.getLaws();
			for(String law:list){
				laws.add(law.split(" ")[0]);
			}
		}
		for(String law:laws){
			System.out.println(law);
		}
	}
}
