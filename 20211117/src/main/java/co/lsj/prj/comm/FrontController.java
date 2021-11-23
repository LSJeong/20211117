package co.lsj.prj.comm;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.lsj.prj.command.AjaxAuthorUpdate;
import co.lsj.prj.command.CommonFileUpload;
import co.lsj.prj.command.HomeCommand;
import co.lsj.prj.command.Logout;
import co.lsj.prj.command.MemberDelete;
import co.lsj.prj.command.MemberEditSave;
import co.lsj.prj.command.MemberIdCheck;
import co.lsj.prj.command.MemberInfo;
import co.lsj.prj.command.MemberJoin;
import co.lsj.prj.command.MemberJoinForm;
import co.lsj.prj.command.MemberList;
import co.lsj.prj.command.MemberLoginForm;
import co.lsj.prj.command.MemberUpdate;
import co.lsj.prj.command.NoticeForm;
import co.lsj.prj.command.NoticeList;
import co.lsj.prj.command.NoticeRead;
import co.lsj.prj.command.memberLogin;

@WebServlet("*.do")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HashMap<String, Command> map = new HashMap<String, Command>();   
	
    public FrontController() {
        super();
    }

	public void init(ServletConfig config) throws ServletException {
		//Command들을 요청에 따라 처리할 수 있도록 메모리에 구성한다.
		map.put("/home.do", new HomeCommand());  //홈 페이지
		map.put("/memberLoginForm.do", new MemberLoginForm()); //로그인 폼 호출
		map.put("/memberLogin.do", new memberLogin()); //로그인 처리
		map.put("/logout.do", new Logout()); //로그아웃 처리
		map.put("/memberList.do", new MemberList()); //멤버 목록
		map.put("/memberJoinForm.do", new MemberJoinForm()); //회원가입 폼 호출
		map.put("/ajaxIdCheck.do", new MemberIdCheck()); //아이디 중복 체크
		map.put("/memberJoin.do", new MemberJoin()); //회원가입 처리
		map.put("/memberInfo.do", new MemberInfo()); //회원정보
		map.put("/memberUpdate.do", new MemberUpdate()); //회원정보 수정 폼 호출
		map.put("/memberEditSave.do", new MemberEditSave()); //회원수정 정보 저장
		map.put("/memberDelete.do", new MemberDelete()); //회원탈퇴
		map.put("/ajaxAuthorUpdate.do", new AjaxAuthorUpdate()); //회원권한 변경
		map.put("/noticeform.do", new NoticeForm()); //공지사항 폼 호출
		map.put("/noticeList.do", new NoticeList()); //공지사항 목록보기
//		map.put("/noticeResister.do", new ServletApiUpload()); //공지사항 저장 Servlet Part 사용
//		map.put("/noticeResister.do", new NoticeResister()); //공지사항 저장 cos.jar 사용
		map.put("/noticeResister.do", new CommonFileUpload()); //공지사항 저장 common-fileupload 사용
		map.put("/noticeRead.do", new NoticeRead()); //공지사항 상세보기
	}

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//요청을 분석 실행할 명령을 찾아 수행하고 결과를 돌려주는 메소드
		request.setCharacterEncoding("UTF-8");
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String page = uri.substring(contextPath.length());
		
		Command command = map.get(page);  //new HomeCommand();
		String viewPage = command.run(request, response);
		
		if(!viewPage.endsWith(".do")) {
			if(viewPage.startsWith("ajax:")) { //ajax 처리
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().append(viewPage.substring(5));
				
				return;
			}else {
				viewPage = "WEB-INF/views/" + viewPage + ".jsp";				
			}
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
	}

}
