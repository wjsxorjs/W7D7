package spring.intercept;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean res = true;
		
		// 로그인 체크를 해서 만약! 로그인이 안 된 상태이면 res에 false를 저장한다.
		// 먼저, 로그인 체크를 하기 위해 HttpSession을 얻어내자.
		HttpSession session =  request.getSession(true);
		// true에 의미는 : 
		// 만약 session이 없다면(혹은 삭제된 상태면) 새로운 세션을 생성하라는 뜻.
		// false일 경우 새로운 세션을 생성하지 않고 null값을 반환해준다.
		
		// 예를 들어, 로그인 시 session에 "mvo"를 저장했다고 가정을 하면
		// 여기서는 session에서 "mvo"를 얻어내면 된다.
		
		// 있으면 로그인을 했다는 뜻
		Object obj = session.getAttribute("mvo");
		if(obj == null) {
			response.sendRedirect("/login");
			res = false;
		}
		
		// res가 true인 경우: 원래 경로로 진행을 계속 하지만,
		// 		  false인 경우: 원래 경로로 진행을 하지않고 "/login"으로 강제이동한다.
		
		return res;
	}

	
	
}
