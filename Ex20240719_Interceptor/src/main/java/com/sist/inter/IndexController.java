package com.sist.inter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class IndexController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		
		return "index";
	}
	@RequestMapping("/index")
	public String main() {
		
		return "index";
	}

	@RequestMapping("/sub/bravo")
	public ModelAndView bravo() {
		ModelAndView mv = new ModelAndView();
		
		// 할 일들...
		
		mv.addObject("msg", "안녕!"); // jsp에서 표현할 자원
		mv.setViewName("bravo"); // WEB-INF/views/bravo.jsp
		
		return mv; // forwarding 된다.
	}
	
	
}
