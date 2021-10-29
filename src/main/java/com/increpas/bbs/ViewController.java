package com.increpas.bbs;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import mybatis.dao.BbsDAO;
import mybatis.vo.BbsVO;

@Controller
public class ViewController {
	@Autowired
	private BbsDAO b_dao;
	
	@Autowired
	HttpServletRequest request;
	
	@RequestMapping("/view.inc")
	public ModelAndView view(String cPage, String b_idx) {
		ModelAndView mv = new ModelAndView();
		BbsVO vo = b_dao.getBbs(b_idx);
		mv.addObject("vo", vo);
		mv.addObject("ip", request.getRemoteAddr());
		//cPage도 사실 가야하지만 저장할 필요는 없다.
		//이유는 view.jsp로 forward되므로 여기까지 전달된 파라미터들이 전부 가게된다.
		mv.setViewName("view");
		return mv;
	}
}
