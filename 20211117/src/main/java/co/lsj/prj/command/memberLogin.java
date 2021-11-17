package co.lsj.prj.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import co.lsj.prj.comm.Command;
import co.lsj.prj.member.service.MemberService;
import co.lsj.prj.member.service.MemberVO;
import co.lsj.prj.member.serviceImpl.MemberServiceImpl;

public class memberLogin implements Command {
	private String message;
	@Override
	public String run(HttpServletRequest request, HttpServletResponse response) {
		// 모델을 호출해서 결과를 처리함
		HttpSession session = request.getSession();
		MemberService memberDao = new MemberServiceImpl();
		MemberVO member = new MemberVO();
		member.setId(request.getParameter("id"));
		member.setPassword(request.getParameter("password"));
		member = memberDao.memberSelect(member);
		if(member != null) {
			session.setAttribute("id", member.getId());
			session.setAttribute("author", member.getAuthor());
			session.setAttribute("name", member.getName());
			message = member.getName() + "님 환영합니다!!!";
		}else {
			message = "아이디 또는 패스워드를 확인하세요!!!";
		}
		request.setAttribute("message", message);
		return "member/memberLogin";
	}

}
