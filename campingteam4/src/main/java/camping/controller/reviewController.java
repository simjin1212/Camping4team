package camping.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import camping.model.review;
import camping.model.review_reply;
import camping.model.revlike;

@Controller
public class reviewController {

	@Autowired
	private camping.service.ReviewService service;

	// 글작성 폼
	@RequestMapping("re_insertform.do")
	public String re_insertform() {

		return "review/reviewinsertform";
	}

	// 글작성
	@RequestMapping("re_insert.do")
	public String re_insert(review review, HttpSession session, Model model) {

		String id = (String) session.getAttribute("id");

		int result = 0;

		if (id == null) {

			result = 2;
			return "review/reviewinsert";

		} else {
			review.setId(id);

			result = service.insert(review);
			System.out.println("result :" + result);

			model.addAttribute("result", result);

			return "review/reviewinsert";
		}
	}

	// 글목록
	@RequestMapping("reviewlist.do")
	public String reviewlist(HttpServletRequest request, HttpSession session, Model model) {

		String id = (String) session.getAttribute("id");

		System.out.println("id" + id);

		int page = 1; // 페이지 초기값
		int limit = 10; // 한화면에 나올 데이터 개수 정의

		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		// int startRow = (page - 1)*limit +1;
		// int endRow = page * limit;

		int listcount = service.getCount();
		System.out.println("총 개수 : " + listcount);

		List<review> relist = service.reList(page); // Dao까지 두개의 값을 전달할 수 있지만 mybatise는 한개만 전달가능
		System.out.println("relist : " + relist);

		List<review> splist = service.splist();

		// 총 페이지 수
		int pageCount = listcount / limit + ((listcount % limit == 0) ? 0 : 1);

		int startPage = ((page - 1) / 10) * limit + 1; // 1, 11, 21....
		int endPage = startPage + 10 - 1; // 10,20,30.......

		if (endPage > pageCount)
			endPage = pageCount;

		model.addAttribute("page", page);
		model.addAttribute("relist", relist);
		model.addAttribute("splist", splist);
		model.addAttribute("listcount", listcount);
		model.addAttribute("pageCount", pageCount);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		return "review/reviewlist";
	}

	// 상세 페이지 : 조회수 1 증가 + 상세정보 구하기
	@RequestMapping("reviewdetail.do")
	public String reviewdetail(@RequestParam int re_no, @RequestParam int page, HttpSession session, Model model) {

		String id = (String) session.getAttribute("id");
		System.out.println("id=" + id);

		service.updatecount(re_no); // 조회수 1 증가

		review review = service.reviewdetail(re_no); // 상세정보 구하기
		
		int res_no = review.getRes_no();

		List<review_reply> list = service.replylist(re_no);

		String spname = service.spname(res_no);
		
		int likecount = service.likecount(re_no);
		System.out.println("likecount"+likecount);

		revlike rl = new revlike();
		
		rl.setId(id);
		rl.setRe_no(re_no);
		
		int likecheck = service.likecheck(rl);
		
		model.addAttribute("likecheck", likecheck);
		model.addAttribute("likecount", likecount);
		model.addAttribute("id", id);
		model.addAttribute("re_no", re_no);
		model.addAttribute("list", list);
		model.addAttribute("review", review);
		model.addAttribute("spname", spname);
		model.addAttribute("page", page);

		return "review/reviewdetail";
	}

	// 수정 폼
	@RequestMapping("reviewupdateform.do")
	public String reviewupdateform(int re_no, int page, Model model) {
		review review = service.reviewdetail(re_no);

		model.addAttribute("review", review);
		model.addAttribute("page", page);

		return "review/reviewupdateform";
	}

	// 수정
	@RequestMapping("reviewupdate.do")
	public String reviewupdate(review review, int page, Model model) {
		int result = 0;
//				review old = service.getBoard(board.getNo());
//				if(old.getPasswd().equals(board.getPasswd())) {
//					result = service.update(board);
//				}else {
//					result = -1;
//				}
		System.out.println("reno" + review.getRe_no());
		System.out.println("sub" + review.getSubject());
		System.out.println("con" + review.getContent());
		System.out.println("scor" + review.getScore());
		System.out.println("c첨" + review.getRe_file());
		System.out.println("c첨2" + review.getRe_file2());

		result = service.update(review);

		model.addAttribute("result", result);
		model.addAttribute("review", review);
		model.addAttribute("page", page);

		return "review/updateresult";
	}

	// 글삭제

	@RequestMapping("reviewdeleteform.do")
	public String reviewdelete(int re_no, int page, Model model) {
		
		int result = service.delete(re_no);
		
		model.addAttribute("result", result);
		model.addAttribute("page", page);
		
		return "review/reviewdelete";
		
	}

}
