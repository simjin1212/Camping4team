package camping.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import camping.dao.ReviewDAO;
import camping.model.review;
import camping.model.review_reply;
import camping.model.revlike;

@Service
public class ReviewService {

	@Autowired
	private ReviewDAO dao;

	// 글작성
	public int insert(review review) {
		return dao.insert(review);
	}
 
	// 글 총개수
	public int getCount() {
		return dao.getCount();
	}

	// 리스트 출력
	public List<review> reList(int page) {
		return dao.reList(page);
	}


 
	// 조회수 증가(값을 돌려주지 않기 때문에 void로 반환)
	public void updatecount(int re_no) {
		dao.updatecount(re_no);
	}

	// 보드 상세
	public review reviewdetail(int re_no) {
		return dao.reviewdetail(re_no);
	}

	public String spname(int res_no) {
			return dao.spname(res_no);
			
		}
	//보드수정
	public int update(review review) {
		return dao.update(review);
	}
	
	//댓글
	public List<review_reply> replylist(int re_no) {
		return dao.replylist(re_no);
	}
	
	public int delete(int re_no) {
		return dao.delete(re_no);
	}
	//좋아요 갯수 출력
	public int likecount(int re_no) {
		return dao.likecount(re_no);
	}
	public int likecheck(revlike rl) {
		return dao.likecheck(rl);
	}

	public int getlike() {
		return dao.getlike();
		
	}

	public String getspname(int sp_no) {
		
		return dao.getspname(sp_no);
	}
 
	public List<review> myList(review rv) {
		return dao.myList(rv);
	}

	public review toplist1() {
		// TODO Auto-generated method stub
		return dao.toplist1();
	}
	
	public review toplist2() {
		// TODO Auto-generated method stub
		return dao.toplist2();
	}
	
	public int revexist(int res_no) {
		return dao.revexist(res_no); 
	}
	public int reno(int res_no) {
		return dao.reno(res_no);
	}
}