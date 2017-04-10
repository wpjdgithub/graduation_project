import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test {
	public static double calc(double[] d1,double[] d2){
		double s1 = 0;
		double s2 = 0;
		double s3 = 0;
		for(int i = 0;i < d1.length;i++){
			s1 += d1[i]*d1[i];
			s2 += d2[i]*d2[i];
			s3 += d1[i]*d2[i];
		}
		s1 = Math.sqrt(s1);
		s2 = Math.sqrt(s2);
		if(s1 == 0.0 || s2 == 0.0)
			return 0.0;
		else{
			double result = s3/(s1*s2);
			return result;
		}
	}
	public static void main(String[] args) throws IOException{
		File file = new File("tmp/tfidf.txt");
		Scanner scanner = new Scanner(file);
		List<String> list1 = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		List<String> list3 = new ArrayList<String>();
		List<double[]> list4 = new ArrayList<double[]>();
		while(scanner.hasNextLine()){
			String caseID = scanner.nextLine();
			if(!scanner.hasNextLine())
				continue;
			String caseKeywords = scanner.nextLine();
			if(!scanner.hasNextLine())
				continue;
			String caseBrief = scanner.nextLine();
			if(!scanner.hasNextLine())
				continue;
			String vector = scanner.nextLine();
			String[] e = vector.split(",");
			double[] d = new double[e.length];
			for(int i = 0;i < e.length;i++){
				d[i] = Double.parseDouble(e[i]);
			}
			list1.add(caseID);
			list2.add(caseKeywords);
			list3.add(caseBrief);
			list4.add(d);
		}
		scanner.close();
		int index = 1;
		double distance = 0;
		for(int i = 1000;i < 2000;i++){
			double d = calc(list4.get(9),list4.get(i));
			if(d > distance){
				distance = d;
				index = i;
			}
		}
		System.out.println(distance);
		System.out.println(list2.get(9)+" "+list3.get(9));
		System.out.println(list2.get(index)+" "+list3.get(index));
	}
}
