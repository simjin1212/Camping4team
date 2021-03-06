package camping.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import camping.dao.PartnerDAO;
import camping.model.msg;
import camping.model.partner;
import camping.model.partner_comment;
import camping.model.pb_join;

@Service
public class PartnerService {

	@Autowired
	private PartnerDAO PBdao;
	
	public int insert(partner partner) {
		return PBdao.insert(partner);
	}
	
	public int getCount() {
		return PBdao.getCount();
	}
	
	public List<partner> getPBList(int page){
		return PBdao.getPBList(page);
	}
	
	public void updatecount(int par_no) {
		PBdao.updatecount(par_no);
	}
	
	public partner getBoard(int par_no) {
		return PBdao.getBoard(par_no);
	}
	
	public int delete(int par_no) {
		return PBdao.delete(par_no);
	}
	
	public int update(partner partner) {
		return PBdao.update(partner);
	}

	public int join(pb_join pb_join) {
		return PBdao.join(pb_join);
	}
	
	public int pb_count(int par_no) {
		return PBdao.pb_count(par_no);
	}

	public int pb_discount(int par_no) {
		return PBdao.pb_discount(par_no);
	}
	
	public int cancel(pb_join pb_join) {
		return PBdao.cancel(pb_join);
	}

	public int chkID(pb_join pb_join) {
		return PBdao.chkID(pb_join);
	}

	public List<pb_join> getJoinList(int par_no) {
		return PBdao.getJoinList(par_no);
	}

	public void sendJmsg(msg msg) {
		PBdao.sendJmsg(msg);
	}

	public List<partner_comment> getPcList(int par_no) {
		return PBdao.getPcList(par_no);
	}

	public int pcinsert(partner_comment pc) {
		// TODO Auto-generated method stub
		return PBdao.pcinsert(pc);
	}

	public int pcdelete(int com_no) {
		return PBdao.pcdelete(com_no);
	}


}
