package com.sist.jdom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import data.vo.MemberVO;

@Controller
public class MemberController {
	
	// 오픈API 서비스의 url같은 경로가 멤버변수로 선언되어야 하지만
	// 우리는 내부에 저장된 XML파일을 접근하는 형식이므로
	// 절대경로를 얻을 수 있는 객체가 준비되어야 한다.
	@Autowired
	private ServletContext application;
	
	@RequestMapping("test")
	public String test(Model model) throws Exception{ // request에서 가져온다.
		
		String realPath = application.getRealPath("/resources/pm/data/member.xml");
		
		// xml문서를 읽기 하기 위해 필요한 객체생성
		SAXBuilder builder = new SAXBuilder();
		
		// 위에서 만든 builder를 통해 지정된 xml문서를 읽어
		// 문서화(Document)시킨다.
		Document doc = builder.build(realPath);
		
		// 메모리 상에 존재하는 xml문서의 root를 얻어낸다.
		Element root = doc.getRootElement(); // members
		
		// root 요소의 자식들 중 태그명인 member인 자식들 모두 가져오기
		List<Element> m_list = root.getChildren("member");
		
		// 위의 list의 길이만큼 MemberVO배열을 만들자
		MemberVO[] m_ar = null;
		if(m_list != null && m_list.size() > 0) {
			m_ar = new MemberVO[m_list.size()];
			
			int i=0;
			for(Element mem: m_list) {
				String name = mem.getChildText("name"); // 마루치
				String email = mem.getChildText("email"); // maru@email.com
				String phone = mem.getChildText("phone"); // 010-1111-1111
				
				MemberVO mvo = new MemberVO(name, email, phone);
				m_ar[i++] = mvo;
			}
		}
		model.addAttribute("ar",m_ar);
		return "test";
	}
	
	@RequestMapping("search")
	@ResponseBody
	public Map<String, Object> search(Model model, int searchType, String searchValue) throws Exception{ // request에서 가져온다.
		
		String realPath = application.getRealPath("/resources/pm/data/member.xml");
		
		// xml문서를 읽기 하기 위해 필요한 객체생성
		SAXBuilder builder = new SAXBuilder();
		
		// 위에서 만든 builder를 통해 지정된 xml문서를 읽어
		// 문서화(Document)시킨다.
		Document doc = builder.build(realPath);
		
		// 메모리 상에 존재하는 xml문서의 root를 얻어낸다.
		Element root = doc.getRootElement(); // members
		
		// root 요소의 자식들 중 태그명인 member인 자식들 모두 가져오기
		List<Element> e_list = root.getChildren("member");
		
		List<MemberVO> m_list = new ArrayList<MemberVO>();
		
		// 위의 list의 길이만큼 MemberVO배열을 만들자
		if(e_list != null && e_list.size() > 0) {
			
			int i=0;
			boolean chk = false;
			for(Element mem: e_list) {
				chk = false;
				String name = mem.getChildText("name"); // 마루치
				String email = mem.getChildText("email"); // maru@email.com
				String phone = mem.getChildText("phone"); // 010-1111-1111
				
				switch(searchType) {
				case 1:
					if(name.contains(searchValue)) {
						chk = true;
					}
					break;
				case 2:
					if(email.contains(searchValue)) {
						chk = true;
					}
					break;
				case 3:
					if(phone.contains(searchValue)) {
						chk = true;
					}
					break;
				}
				
				if(chk) {
					MemberVO mvo = new MemberVO(name, email, phone);
					m_list.add(mvo);
				}
			}
		}
		Map<String, Object> m_map = new HashMap<String, Object>();
		MemberVO[] m_ar = null;
		m_ar = new MemberVO[m_list.size()];
		m_list.toArray(m_ar);
		
		m_map.put("ar",m_ar);
		m_map.put("len",m_ar.length);
		return m_map;
	}
	
	
}
