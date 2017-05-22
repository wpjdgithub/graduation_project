package tfidf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import Grad.Service.dataservice.jdbc.MySQLConnection;
import Grad.Service.dataservice.jdbc.MySQLConnectionImpl;

public class LoadToDB {
	public static void main(String[] args) throws IOException{
		MySQLConnection connection = new MySQLConnectionImpl("wenshu");
		connection.connect();
		File file = new File("F:\\tmp\\tfidf\\idf.txt");
		Scanner scanner = new Scanner(file);
		while(scanner.hasNextLine()){
			String line = scanner.nextLine();
			String[] s = line.split("=");
			if(s[0].length() >= 4)
				continue;
			if(s[0].startsWith("赵") || s[0].startsWith("王") || s[0].startsWith("周")
					|| s[0].startsWith("张") || s[0].startsWith("孙") || s[0].startsWith("李")
					|| s[0].startsWith("吴") || s[0].startsWith("刘") || s[0].startsWith("冯")
					|| s[0].startsWith("罗") || s[0].startsWith("中") || s[0].startsWith("天")
					|| s[0].startsWith("陈") || s[0].startsWith("杨") || s[0].startsWith("孟")
					|| s[0].startsWith("郭") || s[0].startsWith("姚") || s[0].startsWith("黄")
					|| s[0].startsWith("不"))
				continue;
			String sql = "insert into idf value('"+s[0]+"','"+s[1]+"');";
			connection.execute(sql);
			System.out.println(sql);
		}
		scanner.close();
//		File dir = new File("F:\\tmp\\wenshuvec\\");
//		File[] file = dir.listFiles();
//		for(int i = 0;i < file.length;i++){
//			FileInputStream fin = new FileInputStream(file[i]);
//			InputStreamReader ir = new InputStreamReader(fin);
//			BufferedReader br = new BufferedReader(ir);
//			String caseid = file[i].getName().substring(0,file[i].getName().length()-4);
//			StringBuilder sb = new StringBuilder();
//			String line = null;
//			while((line = br.readLine()) != null){
//				String[] s = line.split("=");
//				double d = Double.parseDouble(s[1]);
//				if(d != 0){
//					sb.append(line).append(";");
//				}
//			}
//			String tfidf = sb.toString().substring(0,sb.length()-1);
//			String sql = "insert into newtfidf value('"+caseid+"','"+tfidf+"');";
//			connection.execute(sql);
//			System.out.println(caseid);
//			br.close();
//			ir.close();
//			fin.close();
//		}
		connection.release();
	}
}
