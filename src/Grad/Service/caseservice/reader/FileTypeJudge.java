package Grad.Service.caseservice.reader;
import java.util.*;
public class FileTypeJudge {
    public final static Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();     
    private FileTypeJudge(){
    	
    }     
    static{     
        getAllFileType(); //初始化文件类型信息     
    }     
    private static void getAllFileType()     
    {     
        FILE_TYPE_MAP.put("504b0304140006000800", "docx"); 
        FILE_TYPE_MAP.put("d0cf11e0a1b11ae10000", "doc");
    } 					  
	/**
	 * 得到上传文件的文件头
	 * @param src
	 * @return
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}
	/**
	 * 根据制定文件的文件头判断其文件类型
	 * @param filePaht
	 * @return
	 */
	public static String getFileType(String hex){
		if(FILE_TYPE_MAP.containsKey(hex))
			return FileTypeJudge.FILE_TYPE_MAP.get(hex);
		else
			return "txt";
	}
}
