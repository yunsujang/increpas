package com.increpas.bbs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import mybatis.dao.BbsDAO;

@Controller
public class DelController {
	
	@Autowired
	BbsDAO b_dao;
	
	@RequestMapping("/del.inc")
	public ModelAndView del(String b_idx, String cPage) {
		ModelAndView mv = new ModelAndView();
		b_dao.del(b_idx);
		
		mv.addObject("cPage",cPage);
		mv.addObject("b_idx",b_idx);
		mv.setViewName("redirect:/list.inc");
		
		return mv;
	}
}
