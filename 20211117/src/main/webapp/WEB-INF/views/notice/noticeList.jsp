<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	th{
		background-Color:rgb(252, 218, 252);
		
	}
</style>
<script type="text/javascript">
	function noticeRead(str) {
		frm.no.value = str;
		frm.submit();
	}
</script>
</head>
<body>
<jsp:include page="../home/header.jsp" />
<div align="center">
	<div><h1>📢 공지사항 리스트 📢</h1></div>
	<div>
		<table border="1">
			<tr>
				<th width="70">No</th>
				<th width="100">작성자</th>
				<th width="300">제 목</th>
				<th width="100">작성일자</th>
				<th width="100">첨부파일</th>
			</tr>
			
			<c:forEach items="${notices }" var="notice">
				<tr onmouseover="this.style.background='rgb(252, 244, 252)';"
						onmouseleave="this.style.background='#FFFFFF'; "
						onclick="noticeRead('${notice.no }')">
					<td align="center">${notice.no }</td>
					<td align="center">${notice.name }</td>
					<td align="center">${notice.title }</td>
					<td align="center">${notice.wdate }</td>
					<td align="center">
						<c:if test="${not empty notice.fileName }">
							<img src="img/file.png" alt="첨부파일" width="25" height="25">
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div><br>
	<div>
		<c:if test="${not empty id }">
			<button type="button" onclick="location.href='noticeform.do'">글쓰기</button>
		</c:if>
	</div>
	<div>
		<form id="frm" action="noticeRead.do" method="post">
			<input type="hidden" id="no" name="no">
		</form>
	</div>
</div>
</body>
</html>