package camping.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import camping.model.camp_loc;
import camping.model.equipment;
import camping.model.reservation;
import camping.model.spot;

@Repository
public class reserveDAO {
	@Autowired
	private SqlSession session;
	
	//장소 DB
	public camp_loc loc(int camp_no) {
		return session.selectOne("reserve.loc_sel",camp_no);
	}
	//자리 DB
	public spot spot(int sp_no) {
		return session.selectOne("reserve.sp_sel",sp_no);
	}
	//장비 DB
	public List<equipment> eqm(int camp_no){
		return session.selectList("reserve.eqm_sel", camp_no);
	}
	//저장 (결제대기)
	public int res_save(reservation res) {
		return session.insert("reserve.res_save", res);
	}
}
