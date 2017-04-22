package Grad.Service.caseservice.reader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
public class UploadFileSaver {
	private String dir;
	public UploadFileSaver(String path){
		this.dir = path;
	}
	/**
	 * @parameter 一个InputStream对象，可以读出文件内容
	 * @return 数据保存的路径
	 */
	public String save(InputStream in){
		File fDir = new File(dir);
		int length = fDir.list().length;
		String name = String.valueOf(length);
		String path = this.dir + name;
		try{
			FileOutputStream fout = new FileOutputStream(new File(path));
			OutputStreamWriter ow = new OutputStreamWriter(fout);
			BufferedWriter bw = new BufferedWriter(ow);
			InputStreamReader ir = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(ir);
			String s = null;
			while((s = br.readLine()) != null){
				bw.write(s);
				bw.newLine();
			}
			br.close();
			bw.close();
		} catch (IOException e){
			path = null;
		}
		return path;
	}
}
