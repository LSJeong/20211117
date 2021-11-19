package co.lsj.prj.command;

import java.io.File;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import co.lsj.prj.comm.Command;
import co.lsj.prj.notice.service.NoticeService;
import co.lsj.prj.notice.service.NoticeVO;
import co.lsj.prj.notice.serviceImpl.NoticeServiceImpl;

public class NoticeResister implements Command {
	private String filePath = "c:\\FileTest"; // 파일이 저장되는 절대 경로
	private int fileSize = 1024 * 1024 * 100; // 파일 최대 사이즈 : 100MB로 설정함

	@Override
	public String run(HttpServletRequest request, HttpServletResponse response) {
		// 공지사항 저장
		NoticeService noticeDao = new NoticeServiceImpl();
		NoticeVO vo = new NoticeVO();
		HttpSession session = request.getSession();
		vo.setId((String) session.getAttribute("id")); // 세션에 저장된 아이디 값
		vo.setName((String) session.getAttribute("name")); // 세션에 저장된 이름 값

		try {
			MultipartRequest multi = new MultipartRequest(request, filePath, fileSize, "UTF-8",
					new DefaultFileRenamePolicy()); // 객체 생성시 파일이 전송됨

			// filename은 중복이름이 들어올 경우 자동으로 index가 잇는 물리 파일명
			String fileName = multi.getFilesystemName("fileName");
			String orignal = multi.getOriginalFileName("fileName"); // 원본 파일 명

			fileName = filePath + File.separator + fileName; // separator: 슬러시, 저장경로를 포함해서 만듦(c://FileTest/fileName)

			if (orignal != null) {  //첨부파일 존재할때
				vo.setFileName(orignal); // 원본
				vo.setPfileName(fileName); // 물리파일명
			} else { //첨부 파일이 존재하지않을때
				vo.setFileName("");
				vo.setPfileName("");
			}
			vo.setWdate(Date.valueOf(multi.getParameter("wdate"))); // String타입(문자)을 Date타입(날짜)으로 변환
			vo.setTitle(multi.getParameter("title"));
			vo.setSubject(multi.getParameter("subject"));

			noticeDao.noticeInsert(vo);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "noticeList.do";
	}

}
