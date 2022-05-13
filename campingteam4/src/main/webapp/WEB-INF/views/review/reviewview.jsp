<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>
<s:authentication property="principal" var="user" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="path" value="${pageContext.request.contextPath }" />


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상세페이지</title>

<!-- jQuery문 설정 불러오기 -->
<script src="${path}/js/info/jquery.js"></script>

<!-- 로그인한 사람과 글쓴사람이 동일할때 수정,삭제 가능 -->
<script type="text/javascript">
	var session = '${id}';
	var id = '${review.id}';
	console.log(id);
	$(function() {
		$("#chk1").submit(function() {
			if (member_id != session) {
				alert("사용자가 다르면 수정할 수 없습니다");
				return false;
			}
		});
		$("#chk2").submit(function() {
			if (member_id != session) {
				alert("사용자가 다르면 삭제할 수 없습니다");
				return false;
			}
		});
	});
</script>

<!-- 좋아요 버튼 -->
<script type="text/javascript">
	var session = '${id}';
	var id = '${review.id}';
	$(function() {
		$("#like").submit(function() {
			if (member_id == session) {
				alert("사용자가 같으면 좋아요를 할수 없습니다.");
				return false;

			}

		});
	});
</script>
<!-- 댓글 작성 jQuery문 -->
<script type="text/javascript">
	$(function() {
		$('#review_reply').load(
				'${path}/review_reply/re_no/${review.re_no}')

		$('#review_reply_insert').click(function() {
			if (!frm.review_re_content.value) {
				alert('댓글 입력후에 클릭하시오');
				frm.review_re_content.focus();
				return false;
			}
			var frmData = $('#frm').serialize();
			$.post('${path}/review_reply_insert', frmData, function(data) {
				$('#review_reply').html(data);
				frm.review_re_content.value = '';
			});
		});
	});
</script>

</head>
<body>



	<!-- center -->
	<div class="reviewlist_center">

		<div class="review_view_table">
			<table id="r_view_table1" class="table table-hover">
				<h2>후기 게시판</h2>
				&nbsp;
				<tbody>
					<tr>
						<td width="90">제목</td>
						<td>${review.subject}</td>
					</tr>
					<tr>
						<td>작성자</td>
						<td>${review.id}</td>
					</tr>
					<tr>
						<td>조회수</td>
						<td>${review.readcount}</td>
					</tr>
					<tr>
						<td>내용</td>
						<td><pre>${review.content}</pre></td>
					</tr>
				</tbody>
			</table>

			<div class="r_view_btns">
				<input type="button" value="목록" class="btn btn-dark"
					onClick="location.href='/reviewlist/pageNum/${pageNum}' ">
				<s:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_USER')">
					<form
						action="${path}/reviewupdateform/re_no/${review.re_no}/pageNum/${pageNum}"
						method="post" name="chk" id="chk1">
						<input class="btn btn-dark" type="submit" value="수정">
					</form>

					<form
						action="${path}/reviewdelete/re_no/${review.re_no}/pageNum/${pageNum}"
						method="post" name="chk" id="chk2">
						<input class="btn btn-dark" type="submit" value="삭제">
					</form>
				</s:authorize>
				<s:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_USER')">
					<form
						action="/revlike?review_no=${review.review_no}&pageNum=${pageNum}"
						method="post" id="like">

						<input class="btn btn-dark" type="submit" value="좋아요">
					</form>
				</s:authorize>
			</div>
		</div>


		<!-- 댓글 작성 -->
		<div class="r_view_repl" align=center>
			<form name="frm" id="frm">
				<input type="hidden" name="member_id" value="${username}"> <input
					type="hidden" name="review_fno" value="${review.review_no}">
				<p>댓글쓰기 :</p>
				<div>
					<textarea class="form-control" rows="3" cols="50"
						name="review_re_content"></textarea>
				</div>
				<div>
				<s:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_USER')">
					<input class="btn btn-outline-secondary" type="button" value="확인"
						id="review_reply_insert">
				</s:authorize>
				</div>
			</form>
		</div>

		<!-- 댓글 list 불러오는곳 -->
		<div class="view_rep_list" id="review_reply"></div>

	</div>


</body>
</html>