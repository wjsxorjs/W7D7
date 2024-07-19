package com.sist.inter;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MemberController {
	@Autowired
	private HttpSession session;
	
	@RequestMapping("/login") 	// GET, POST 방식 둘 다 받지만
	public String login() {			// 주로 GET 방식을 쓰기에
		return "login";				// GET 방식을 먼저 받는다.
	}
	
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ModelAndView login(String m_id, String m_pw) {
		ModelAndView mv = new ModelAndView();
		
		// id와 pw로 DB검증
		
		// session처리
		session.setAttribute("mvo", m_id);
		
		mv.setViewName("redirect:/");
		
		return mv;
	}
	
	
		
	
	
	
}
