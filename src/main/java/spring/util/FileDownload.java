package spring.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;

/**
 * Servlet implementation class FileDownload
 */
public class FileDownload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileDownload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    //웹상에서 /FileDownload로 호출할 수 있도록 web.xml에 
    //servlet으로 등록하고 servlet-mapping으로 연결해야 한다.
    //그런데 이런것을 STS가 다 해주기 때문에 FileDownload라는 서블릿을 사용하자! 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dir = request.getParameter("dir");
		String filename = request.getParameter("filename");
		
		//dir은 파일이 저장되는 경로이다.
		//이것을 절대경로로 만들자
		String realPath = getServletContext().getRealPath(dir);
		
		//절대경로를 통해 전체경로를 만들자
		String fullPath = realPath+System.getProperty("file.separator")+filename;
		//os마다 경로를 지정하는 것이 다르기 때문에 System.getProperty("file.separator")를 사용하는 것을 권장한다.
		
		//서블릿을 통해 전체경로를 잘 가져오는지 확인하는 작업
		//System.out.println(fullPath);
		
		//전체경로가 준비되었으니 파일을 보내기 위해 파일 객체를 생성하자!
		File f = new File(fullPath);
		if(f.exists() && f.isFile()) {
			//위와 같은 조건문을 통해 생성한 파일이 실제로 존재하고 파일인 경우 수행!
			
			byte buf[] = new byte[2048];
			//전송한 데이터가 스트림 처리될 때 문자셋 지정
			response.setContentType("application/octet-stream; charset=8859_1");
			
			//다운로드 대화상자 처리
			response.setHeader("Content-Disposition","attachment; filename="+
			new String(filename.getBytes(),"8859_1"));
			
			//전송타입이 이진데이터(binary)
			response.setHeader("Content-Transfer-Encoding", "binary");
			
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
			
			//요청한 곳으로 보내기 위해 스트림을 응답객체로부터 얻어내기
			BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
			
			int size = -1;
			try {
				//읽어오자마자 바로 쓰기
				while((size = bis.read(buf)) != -1) {
					bos.write(buf, 0, size);
					bos.flush();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				if(bos != null)
					bos.close();
				
				if(bis != null)
					bis.close();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
