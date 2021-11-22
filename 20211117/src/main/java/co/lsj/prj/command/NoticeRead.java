package co.lsj.prj.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.lsj.prj.comm.Command;
import co.lsj.prj.notice.service.NoticeService;
import co.lsj.prj.notice.service.NoticeVO;
import co.lsj.prj.notice.serviceImpl.NoticeServiceImpl;

public class NoticeRead implements Command {

	@Override
	public String run(HttpServletRequest request, HttpServletResponse response) {
		NoticeService noticeDao = new NoticeServiceImpl();
		NoticeVO vo = new NoticeVO();
		vo.setNo(Integer.valueOf(request.getParameter("no")));
		request.setAttribute("notice",  noticeDao.noticeSelect(vo));
		
		return "notice/noticeRead";
	}

}
