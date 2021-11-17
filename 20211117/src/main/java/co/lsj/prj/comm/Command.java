package co.lsj.prj.comm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
	public String run(HttpServletRequest request, HttpServletResponse response);  //view 페이지를 돌려줘야하기때문에 String타입
}
