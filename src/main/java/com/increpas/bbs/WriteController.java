package com.increpas.bbs;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import mybatis.dao.BbsDAO;
import mybatis.vo.BbsVO;
import spring.util.FileRenameUtil;
import spring.vo.ImgVO;

@Controller
public class WriteController {

	@Autowired
	private BbsDAO b_dao;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private ServletContext application;

	private String editor_img = "/resources/editor_img";
	private String bbs_upload = "/resources/bbs_upload";

	@RequestMapping("/write.inc")
	public String write() {
		return "write";
	}

	// 에디터에서 이미지가 들어갈 때 해당 이미지를 받아서
	// 저장할 위치

	@RequestMapping(value = "/saveImg.inc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> saveImg(ImgVO vo) {
		// 반환객체 생성
		Map<String, String> map = new HashMap<String, String>();
		MultipartFile mf = vo.getS_file();
		String fname = null;

		if (mf.getSize() > 0) {
			String realPath = application.getRealPath(editor_img);
			fname = mf.getOriginalFilename();

			// 이미지가 이미 저장된 이름과 동일하다면 파일명 뒤에
			// 숫자를 붙여서 이름이 같은 경우가 발생하지 않도록 한다.
			fname = FileRenameUtil.checkSameFileName(fname, realPath);

			try {
				mf.transferTo(new File(realPath, fname)); // 파일 업로드
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		String c_path = request.getContextPath();
		map.put("url", c_path + editor_img);
		map.put("fname", fname);

		return map;
	}

	@RequestMapping(value = "/write.inc", method = RequestMethod.POST)
	public ModelAndView write(BbsVO vo) {
		MultipartFile f = vo.getFile();
		String fname = null;

		if (f.getSize() > 0) {
			// 절대경로
			String realPath = application.getRealPath(bbs_upload);
			// 파일이름 얻어내기
			fname = f.getOriginalFilename();

			// 파일 이름 중복 제거
			fname = FileRenameUtil.checkSameFileName(fname, realPath);
			try {
				// 파일 업로드
				f.transferTo(new File(realPath, fname));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			vo.setFile_name(fname);
			vo.setOri_name(fname);
		}
		vo.setIp(request.getRemoteAddr());
		vo.setBname("BBS");
		
		b_dao.add(vo);
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/list.inc");
		
		return mv;
	}
}
