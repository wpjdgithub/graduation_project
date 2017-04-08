package Grad.Service.dataservice.testdata;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class TestData {
	public static HashMap<String,HashSet<String> > documentTypeMap = 
			new HashMap<String,HashSet<String> >();
	public static HashMap<String,HashSet<String> > handlingCourtMap = 
			new HashMap<String,HashSet<String> >();
	public static HashMap<String,HashSet<String> > courtLevelMap = 
			new HashMap<String,HashSet<String> >();
	public static HashMap<String,HashSet<String> > courtAreaMap = 
			new HashMap<String,HashSet<String> >();
	public static HashMap<String,HashSet<String> > documentNameMap = 
			new HashMap<String,HashSet<String> >();
	public static HashMap<String,String> caseIDMap = new HashMap<String,String>();
	public static HashMap<String,HashSet<String> > caseYearMap = 
			new HashMap<String,HashSet<String> >();
	public static HashMap<String,HashSet<String> > caseTypeMap = 
			new HashMap<String,HashSet<String> >();
	public static HashMap<String,HashSet<String> > caseProgramMap = 
			new HashMap<String,HashSet<String> >();
	public static HashMap<String,HashSet<String> > caseBriefMap = 
			new HashMap<String,HashSet<String> >();
	public static HashMap<String,HashSet<String> > judgerMap = 
			new HashMap<String,HashSet<String> >();
	public static HashMap<String,HashSet<String> > litigantsMap = 
			new HashMap<String,HashSet<String> >();
	public static HashMap<String,HashSet<String> > lawMap = 
			new HashMap<String,HashSet<String> >();
	static{
		File indexFile = new File("wenshudata/index.txt");
		try {
			Scanner scanner = new Scanner(indexFile);
			TestData.documentTypeMap = trans1(scanner.nextLine());
			TestData.handlingCourtMap = trans1(scanner.nextLine());
			TestData.courtLevelMap = trans1(scanner.nextLine());
			TestData.courtAreaMap = trans1(scanner.nextLine());
			TestData.documentNameMap = trans1(scanner.nextLine());
			TestData.caseIDMap = trans2(scanner.nextLine());
			TestData.caseYearMap = trans1(scanner.nextLine());
			TestData.caseTypeMap = trans1(scanner.nextLine());
			TestData.caseProgramMap = trans1(scanner.nextLine());
			TestData.caseBriefMap = trans1(scanner.nextLine());
			TestData.judgerMap = trans1(scanner.nextLine());
			TestData.litigantsMap = trans1(scanner.nextLine());
			TestData.lawMap = trans1(scanner.nextLine());
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	private static HashMap<String,HashSet<String> > trans1(String str){
		HashMap<String,HashSet<String> > map = new HashMap<String,HashSet<String> >();
		str = str.substring(1,str.length()-2);//去除首位的{]}
		String[] s1 = str.split("],");
		for(int i = 0;i < s1.length;i++){
			String[] s2 = s1[i].split("=\\[");
			String key = s2[0].trim();
			String[] value = s2[1].split(",");
			HashSet<String> hashset = new HashSet<String>();
			for(int j = 0;j < value.length;j++){
				hashset.add(value[j].trim());
			}
			map.put(key, hashset);
		}
		return map;
	}
	private static HashMap<String,String> trans2(String str){
		HashMap<String,String> map = new HashMap<String,String>();
		str = str.substring(1,str.length()-1);
		String[] s = str.split(",");
		for(int i = 0;i < s.length;i++){
			String[] value = s[i].split("=");
			map.put(value[0].trim(), value[1].trim());
		}
		return map;
	}
	
}
