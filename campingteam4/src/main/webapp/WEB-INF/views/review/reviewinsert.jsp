<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<c:if test="${result == 1 }">
		<script type="text/javascript">
			alert("입력 성공");
			location.href = "reviewlist.do";
		</script>
	</c:if>
	<c:if test="${result == 0 }">
		<script type="text/javascript">
			alert("입력 실패");
			history.go(-1);
		</script>
	</c:if>
	
	
<c:if test="${result == 2 }">
		<script type="text/javascript">
			alert("로그인이 필요합니다.");
			location.href = "member_login.do";
		</script>
	</c:if>