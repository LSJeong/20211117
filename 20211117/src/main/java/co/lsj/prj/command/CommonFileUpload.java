package co.lsj.prj.command;

import java.io.File;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import co.lsj.prj.comm.Command;
import co.lsj.prj.notice.service.NoticeService;
import co.lsj.prj.notice.service.NoticeVO;
import co.lsj.prj.notice.serviceImpl.NoticeServiceImpl;

public class CommonFileUpload implements Command {
	//Apache Common-fileupload 라이브러리 사용(pom.xml에도 추가해줘야한다)
	private String fileSave = "c:\\FileTest"; //개발시 업로드 파일 저장공간
//	private String fileSave = "fileUpload"; //운영서버에 실제 동작환경을 꾸밀때
	
	@Override
	public String run(HttpServletRequest request, HttpServletResponse response) {
		// 공지사항 저장
		HashMap<String, String> map = new HashMap<String, String>();
		NoticeService noticeDao = new NoticeServiceImpl();
		NoticeVO vo = new NoticeVO();
		HttpSession session = request.getSession();
		vo.setId((String) session.getAttribute("id")); // 세션에 저장된 아이디 값
		vo.setName((String) session.getAttribute("name")); // 세션에 저장된 이름 값
		String fileName = null; //파일명
		String pfileName = null; //물리 파일명

		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory(); //파일 저장소 관련 정보
		ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory); //request 객체 parse
		
		try {
			List<FileItem> items = fileUpload.parseRequest(request);
			//FileItem 객체는 폼에서 넘어온 multipart 객체형식을 다루는 객체
			for(FileItem item : items) {
				if(item.isFormField()) {
					map.put(item.getFieldName(), item.getString("utf-8")); //필드명과 데이터를 저장
					
				}else if(!item.isFormField() && item.getSize() > 0){
					//폼에서 넘어온 타입이 일반필드가 아니고 file 타입이고 사이즈가 0보다 크면
					int index = item.getName().lastIndexOf(File.separator); //마지막 \의 위치
					fileName = item.getName().substring(index+1); //실 파일명만 추출
					//System.out.println(item.getFieldName() + "===file===" + item.getString("utf-8"));
					String extension = fileName.substring(fileName.lastIndexOf("."), fileName.length()); //파일확장명
					UUID uuid = UUID.randomUUID(); //고유한 UUID생성
					String newFileName = uuid.toString() + extension; // UUID를 통한 새로운 파일명으로 변환 
					pfileName= fileSave + File.separator + newFileName;  // c:\\FileTest\파일명				
					File uploadFile = new File(pfileName); //파일을 열어서 읽고 
					item.write(uploadFile); //파일업로드가 일어남
					
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		//이곳에 DB처리할 값을 넣어주는 곳
		vo.setFileName(fileName);
		vo.setPfileName(pfileName);
		vo.setWdate(Date.valueOf(map.get("wdate")));
		vo.setTitle(map.get("title"));
		vo.setSubject(map.get("subject"));
		noticeDao.noticeInsert(vo);
		
		return "noticeList.do";
	}
}
