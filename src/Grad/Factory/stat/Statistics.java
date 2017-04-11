package Grad.Factory.stat;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import Grad.Service.dataservice.WenshuDataService;
import Grad.Service.dataservice.impl.WenshuDataServiceImpl;
import Grad.Service.wenshu.Wenshu;

public class Statistics {
	public static void main(String[] args){
		WenshuDataService wenshuDataService = new WenshuDataServiceImpl();
		Set<String> courtLevelSet = new HashSet<String>();
		Set<String> caseProgramSet = new HashSet<String>();
		Set<String> caseTypeSet = new HashSet<String>();
		Set<String> documentTypeSet = new HashSet<String>();
		Set<String> caseBriefSet = new HashSet<String>();
		List<Wenshu> wenshus = wenshuDataService.getAllWenshuData();
		int size = wenshus.size();
		for(int i = 0;i < size;i++){
			Wenshu wenshu = wenshus.get(i);
//			courtLevelSet.add(wenshu.getCourtLevel());
//			caseProgramSet.add(wenshu.getCaseProgram());
//			caseTypeSet.add(wenshu.getCaseType());
//			documentTypeSet.add(wenshu.getDocumentType());
			caseBriefSet.add(wenshu.getCaseBrief());
		}
		Iterator<String> iterator ;
		iterator = caseBriefSet.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next()+" ");
		}
		System.out.println();
//		System.out.println("法院层级");
//		iterator = courtLevelSet.iterator();
//		while(iterator.hasNext()){
//			String courtLevel = iterator.next();
//			System.out.print(courtLevel+" ");
//		}
//		System.out.println();
//		System.out.println("审判程序");
//		iterator = caseProgramSet.iterator();
//		while(iterator.hasNext()){
//			String caseProgram = iterator.next();
//			System.out.println(caseProgram+" ");
//		}
//		System.out.println();
//		System.out.println("案件类型");
//		iterator = caseTypeSet.iterator();
//		while(iterator.hasNext()){
//			String caseType = iterator.next();
//			System.out.println(caseType+" ");
//		}
//		System.out.println();
//		System.out.println("文书类型");
//		iterator = documentTypeSet.iterator();
//		while(iterator.hasNext()){
//			String documentType = iterator.next();
//			System.out.println(documentType);
//		}
	}
}
