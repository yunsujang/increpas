package spring.util;

import java.io.File;

public class FileRenameUtil {
	public static String checkSameFileName(String filename, String path) {
		//인자인 fileName에서 확장자를 뺀 파일명 가려내자!
		int period = filename.lastIndexOf("."); // test123.txt => 7 
		
		String f_name = filename.substring(0, period); //test123(파일명)
		String suffix = filename.substring(period); //.txt(확장자)
		
		//전체경로
		String saveFileName = path + System.getProperty("file.separator") + filename;
		
		File f = new File(saveFileName);
		
		//같은 이름이 있을 경우 파일명 뒤에 숫자를 붙여준다.
		int idx = 1;
		
		while(f != null && f.exists()) {
			//파일이 이미 존재하므로 이름을 변경하자!
			StringBuffer sb = new StringBuffer();
			
			sb.append(f_name);
			sb.append("_");
			sb.append(idx++); //변경된 파일명마저도 존재할 수도 있으니 idx증가
			sb.append(suffix);
			
			filename = sb.toString();
			
			saveFileName = path + System.getProperty("file.separator") + filename;
			
			f = new File(saveFileName);
		}//while문의 끝
		
		return filename;
		
	}
}
