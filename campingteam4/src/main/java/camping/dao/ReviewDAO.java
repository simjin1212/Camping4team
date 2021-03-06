package camping.dao;


import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import camping.model.review;
import camping.model.review_reply;
import camping.model.revlike;

@Repository
public class ReviewDAO {

	@Autowired
	private SqlSession session;
	
	//글작성
	public int insert(review review) {
		return session.insert("reviewns.insert", review);
	} 
	//글 총개수
		public int getCount() {
			return session.selectOne("reviewns.count");
		}
		//글 리스트
		public List<review> reList(int page){
			return session.selectList("reviewns.list", page);
		}
		
		//조회수증가
		public void updatecount(int re_no) {
			session.update("reviewns.updatecount", re_no);
		}
		//보드 상세
		public review reviewdetail(int re_no) {
			return session.selectOne("reviewns.reviewdetail", re_no);
		}
		public String spname(int res_no) {
			return session.selectOne("reviewns.spname", res_no);
			
		}
		//보드 수정
		public int update(review review) {
			return session.update("reviewns.update",review);
		}
		//댓글
		public List<review_reply> replylist(int re_no) {
			return session.selectList("replyns.replylist", re_no);
		}
		//후기 삭제
		public int delete(int re_no) {
			return session.delete("reviewns.delete", re_no);
		}
		//좋아요 갯수 출력
		public int likecount(int re_no) {
			return session.selectOne("revlikens.count",re_no);
		}
		public int likecheck(revlike rl) {
			return session.selectOne("revlikens.likecheck", rl);
		}
		public int getlike() {
			// TODO Auto-generated method stub
			return session.update("reviewns.getlike");
		}
		public String getspname(int sp_no) {
			// TODO Auto-generated method stub
			return session.selectOne("reviewns.getspname", sp_no);
		}
		public List<review> myList(review rv) {
			return session.selectList("reviewns.myList", rv);
		}
		public review toplist1() {
			// TODO Auto-generated method stub
			return session.selectOne("reviewns.toplist1");
		}
		public review toplist2() {
			// TODO Auto-generated method stub
			return session.selectOne("reviewns.toplist2");
		}
		
		public int revexist(int res_no) {
			return session.selectOne("reviewns.revexist", res_no);
		}
		public int reno(int res_no) {
			return session.selectOne("reviewns.reno", res_no);
		}
}
