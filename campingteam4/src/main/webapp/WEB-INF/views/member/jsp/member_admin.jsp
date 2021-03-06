<%@ page language="java" contentType="text/html; charset=UTF-8"
		pageEncoding="UTF-8"%>
		<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>
	<s:authentication property="principal" var="user"/>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<c:set var="path" value="${pageContext.request.contextPath }" />
	<!DOCTYPE html>
	<html> 
	<head>
	<meta charset="UTF-8">
	<title>회원관리</title>
	  <meta name="viewport" content="width=device-width, initial-scale=1">
	  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.min.js"></script>
	  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
	  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
	<style> 
	h2 {font-size:15px;}
	.star-rating {width:143px; }
	.star-rating,.star-rating span {display:inline-block; height:29px; overflow:hidden; background:url(img/star.png)no-repeat; }
	.star-rating span{background-position:left bottom; line-height:0; vertical-align:top; }
	 html { font-size:8px; } 
	  @font-face {
	    font-family: 'GmarketSansBold';
	    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansBold.woff') format('woff');
	    font-weight: normal;
	    font-style: normal;
	}
	  .title{
	  margin-top:3rem;
	   font-family: 'GmarketSansBold';
	   font-size:3.2rem;
	   color:#E35E0A;
	   margin-bottom:1rem;
	  }
	  
	  table{
	  font-size:1.6rem;
	  width:80%;
	  }
	 
	
	a{
	color: #000;
	}
	a:hover{
	text-decoration:none;
	}
	
	.container1 {
/* 	  position: fixed;
	  height: 400px;
	  margin-left: -100px;
	  margin-top: -100px;
	  left: 15%;
	  top: 28%; */
	 background-color:white;
	};
	</style>
	</head>
	<body>
	<header>
<%@ include file="../include/top.jsp" %>
</header>
<h1 style="margin-bottom:50px;">--</h1>
<div class="container" style="background-color:white; ">
	 <form class="container1">
	<div class="title">회원 관리</div>
			<table class="table" align=center>
				<thead><tr class="cent" >
					<th width=20%>아이디</th>
					<th width=15%>이름</th>
					<th width=30%>주소</th>
					<th width=15%>휴대폰번호</th>
					<th width=20%>이메일</th>
				</tr></thead>
			<c:forEach var = "r" items="${memberlist}" varStatus="status">
			<tr>
				<td><a style="text-decoration:none" href="./memberdetail.do?id=${r.id}">
				${r.id}</td>
				<td>${r.name}</td>
				<td>${r.addr1}-${r.addr2}</td>
				<td>${r.phone}</td>	
				<td>${r.email}</td>
			</tr>
			</c:forEach>
			</table>
	<!-- 페이지처리 -->
	<center>
	<c:if test="${listcount>0}">
		
	<!-- 1페이지로 이동              //   text-decoration은 밑줄 없애기-->
	<a href="reviewlist.do?page=1" style="text-decoration:none"> << </a>
	<!-- 이전 블럭으로 이동 -->
	<c:if test="${startPage > 10}">
		<a href="reviewlist.do?page=${startPage-10}">[이전]</a>
	</c:if>
	<!-- 각 블럭에 10개의 페이지 출력 -->
	<c:forEach var="i" begin="${startPage}" end="${endPage}">
		<c:if test="${i == page}">				<!-- 현재 페이지 -->
			[${i}]	
		</c:if>
		<c:if test="${i != page}">				<!-- 현재 페이지가 아닌 경우 -->
			<a href="reviewlist.do?page=${i}">[${i}]</a>	
		</c:if>
	</c:forEach>
	
	<!-- 다음 블럭으로 이동 -->	
	<c:if test="${endPage < pageCount}">
		<a href="reviewlist.do?page=${startPage+10}">[다음]</a>
	</c:if>
	<!-- 마지막 페이지로 이동 -->
	<a href="reviewlist.do?page=${pageCount}" style="text-decoration:none"> >> </a>
	</c:if>	
	</center>
	</form>
	</div>
	</body>
	</html>
}

