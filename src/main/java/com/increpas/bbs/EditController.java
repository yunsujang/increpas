package com.increpas.bbs;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import mybatis.dao.BbsDAO;
import mybatis.vo.BbsVO;
import spring.util.FileRenameUtil;

@Controller
public class EditController {

	@Autowired
	private BbsDAO b_dao;
	
	@Autowired
	private HttpServletRequest request;
	
	private ServletContext application;
	
	private String bbs_upload = "/resources/bbs_upload";
	
	@RequestMapping("/edit.inc")
	public String edit(String b_idx, Model m) {
		BbsVO vo = b_dao.getBbs(b_idx);
		
		m.addAttribute("vo",vo); // Model은 request에 저장된다.
								 // forward하면 사용이 가능하다.
		return "edit";
	}
	
	@RequestMapping(value = "/edit.inc", method = RequestMethod.POST)
	public ModelAndView edit(BbsVO vo, String cPage){
		ModelAndView mv = new ModelAndView();
		// 요청 시 파일이 첨부된 요청인지 확인
		// 파일이 첨부된 요청(multipart)
		// 파일이 첨부되지 않은 요청(application)
		String ctx = request.getContentType();
		
		if(ctx.startsWith("multipart")) {
			//파일첨부가 되어서 전달되어 온 경우!!!!!!!!
			MultipartFile mf = vo.getFile();
			if(mf != null && mf.getSize() > 0) {
				//절대경로 얻기
				String fname = mf.getOriginalFilename();
				
				String realPath = application.getRealPath(bbs_upload);
				fname = FileRenameUtil.checkSameFileName(fname, realPath);
				
				try {
					mf.transferTo(new File(realPath, fname));//파일 업로드!!!!!
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				vo.setFile_name(fname);
				vo.setOri_name(fname);
			}
			
			vo.setIp(request.getRemoteAddr());
			
			b_dao.edit(vo);
			mv.setViewName("redirect:/view.inc?b_idx="+vo.getB_idx()+"&cPage="+cPage);}else if(ctx.startsWith("application")) {
			BbsVO bvo = b_dao.getBbs(vo.getB_idx());
			mv.addObject("vo",bvo);
			
			mv.setViewName("edit");
		}
		
		return mv;
	}
	
	/*
	 * @RequestMapping(value = "edit.inc", method = RequestMethod.POST) public
	 * ModelAndView edit(BbsVO vo,String cPage) { ModelAndView mv = new
	 * ModelAndView(); String ctx = request.getContentType(); if
	 * (ctx.startsWith("application")) { BbsVO bvo = b_dao.getBbs(vo.getB_idx());
	 * mv.addObject("vo", bvo); mv.setViewName("edit");
	 * 
	 * } else if (ctx.startsWith("multipart")) { MultipartFile f = vo.getFile();
	 * if(f != null && f.getSize() > 0) { String realPath =
	 * application.getRealPath(bbs_upload); String fname = f.getOriginalFilename();
	 * fname = FileRenameUtil.checkSameFileName(fname, realPath);
	 * 
	 * try { f.transferTo(new File(realPath, fname)); } catch (Exception e) {
	 * e.printStackTrace(); // TODO: handle exception } vo.setFile_name(fname);
	 * vo.setOri_name(fname);
	 * 
	 * }
	 * 
	 * vo.setIp(request.getRemoteAddr()); b_dao.edit(vo);
	 * mv.setViewName("redirect:/view.inc?cPage="+cPage+"&b_idx="+vo.getB_idx()); }
	 * 
	 * return mv; }
	 */
}
