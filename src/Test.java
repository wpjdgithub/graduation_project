import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Grad.Service.caseservice.reader.FileTypeJudge;

public class Test {
	public static void main(String[] args) throws IOException{
		File file = new File("F:\\tmp\\test.docx");
		InputStream inputStream = new FileInputStream(file);
		byte[] b = new byte[10];
		inputStream.read(b, 0, b.length);
		String fileCode = FileTypeJudge.bytesToHexString(b);
		String fileType = FileTypeJudge.getFileType(fileCode);
		BufferedReader br = new BufferedReader(
				new InputStreamReader(inputStream));
		System.out.println(br.readLine());
		inputStream.close();
		System.out.println(fileCode+" "+fileType);
	}
}
