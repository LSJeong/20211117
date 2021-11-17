<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="js/JQuery-3.6.0.min.js"></script>
<script type="text/javascript">
	/* function CallProcess(n) {
		if(n == 1){
			frm.action = "memberUpdate.do";
		}else{
			var str = confirm("정말 탈퇴를 원하십니까?");
			if(str){
				frm.action = "memberDelete.do";				
			}else{
				return false;
			}
		}		
		from.submit();
	} */
	
	$(function(){
		$("#btn1").click(function(){
			frm.action = "memberUpdate.do";
			frm.submit();
		});
		
		$("#btn2").click(function(){
			var str = confirm("정말 탈퇴를 원하십니까?");
			if(str)
				frm.action = "memberDelete.do";
			else
				return false;
			frm.submit();
		});
	});
</script>
</head>
<body>
<jsp:include page="../home/header.jsp" />
<div align="center">
	<div><h1>나의 정보 보기</h1></div>
	<div>
		<table border="1">
			<tr>
				<th width="150">아 이 디</th>
				<td width="150">${member.id }</td>
				<th width="150">이 름</th>
				<td width="150">${member.name }</td>
			</tr>
			<tr>
				<th>패스워드</th>
				<td>********</td>
				<th>권 한</th>
				<td>${member.author }</td>
			</tr>
			<tr>
				<th>전화번호</th>
				<td colspan="3">${member.tel }</td>
			</tr>
			<tr>
				<th>주소</th>
				<td colspan="3">${member.address }</td>
			</tr>
		</table>
	</div><br>
	<div>
		<button type="button" id="btn1">수 정</button> &nbsp;&nbsp;&nbsp;
		<button type="button" id="btn2">회원탈퇴</button>
	</div>
	<div>
		<form id="frm" method="post">
			<input type="hidden" id="id" name="id" value="${member.id }">
		</form>
	</div>
</div>
</body>
</html>