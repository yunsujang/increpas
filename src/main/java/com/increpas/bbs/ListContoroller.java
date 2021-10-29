package com.increpas.bbs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import mybatis.dao.BbsDAO;
import mybatis.vo.BbsVO;
import spring.util.Paging;

@Controller
public class ListContoroller {

	@Autowired
	BbsDAO b_dao;
	
	int nowPage, 
		rowTotal, 
		blockList = 10, 
		blockPage = 5;
		
	@RequestMapping("/list.inc")
	public ModelAndView list(String cPage, String bname) {
		ModelAndView mv = new ModelAndView();
		
		if(cPage == null)
			nowPage = 1;
		
		else
			nowPage = Integer.parseInt(cPage);
		
		if(bname == null)
			bname = "BBS";
		
		rowTotal = b_dao.getTotalCount(bname);
		
		Paging page = new Paging(nowPage, rowTotal, blockList, blockPage);
		
		int begin = page.getBegin();
		int end = page.getEnd();
		
		String pageCode = page.getSb().toString();
		
		BbsVO[] ar = b_dao.getList(String.valueOf(begin), String.valueOf(end), bname);
		mv.addObject("ar", ar);
		mv.addObject("nowPage", nowPage);
		mv.addObject("blockList", blockList);
		mv.addObject("rowTotal",rowTotal );
		mv.addObject("pageCode", pageCode);
		mv.setViewName("list");
		return mv;
	}
	
}
