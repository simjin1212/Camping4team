<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:if test="${result==1}">
	<script>
	alert("캠핑장 등록 성공");
	location.href="admin_camp_list.do";
	</script>
	</c:if>
<c:if test="${result!=1}">
	<script>
	alert("캠핑장 등록 실패");
	history.go(-1);
	</script>
	</c:if>
</body>
</html>