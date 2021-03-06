package camping.controller;

import java.util.List;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import camping.model.msg;
import camping.model.partner;
import camping.model.partner_comment;
import camping.model.pb_join;
import camping.model.review_reply;
import camping.service.PartnerService;

@Controller
public class PartnerController {

	@Autowired
	private PartnerService PBService;

	// 글작성폼
	@RequestMapping("pbWrite.do")
	public String pbWrite() {
		System.out.println("pbWrite");
		return "partner/pbWrite";
	}

	// 글작성
	@RequestMapping("pbresult.do")
	public String pbresult(HttpServletRequest request, partner partner, Model model) {

		System.out.println("pbWriteResult");
		System.out.println("start_date:" + partner.getStart_date());
		System.out.println("par_no:" + partner.getEnd_date());

		System.out.println("getCamp_area:" + partner.getCamp_area());
		System.out.println("getCamp_type:" + partner.getCamp_type());
		System.out.println("getSubject:" + partner.getSubject());
		System.out.println("getContent:" + partner.getContent());
		System.out.println("getId:" + partner.getId());
		System.out.println("getNow_num:" + partner.getNow_num());
		System.out.println("getWant_num:" + partner.getWant_num());
		System.out.println("getReadcount:" + partner.getReadcount());
		System.out.println("getPar_no:" + partner.getPar_no());

		int result = PBService.insert(partner);
		System.out.println("result:" + result);
		model.addAttribute("result", result);
		return "partner/pbWriteResult";
	}

	@RequestMapping("pbList.do")
	public String pbList(HttpServletRequest request, Model model) {

		System.out.println("pbList");
		int page = 1;
		int limit = 10;

		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}

		int startRow = (page - 1) * limit + 1;
		int endRow = page * limit;

		int listcount = PBService.getCount();
		System.out.println("listcount:" + listcount);

		List<partner> pbList = PBService.getPBList(page);
		System.out.println("pbList:" + pbList);

		int pageCount = listcount / limit + ((listcount % limit == 0) ? 0 : 1);
		int startPage = ((page - 1) / 10) * limit + 1;
		int endPage = startPage + 10 - 1;

		if (endPage > pageCount)
			endPage = pageCount;

		model.addAttribute("page", page);
		model.addAttribute("listcount", listcount);
		model.addAttribute("pbList", pbList);
		model.addAttribute("pageCount", pageCount);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		return "partner/pbList";
	}

	@RequestMapping("pbView.do")
	public String pbView(HttpServletRequest request, int par_no, int page, Model model) {

		int result = 0;
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");

		System.out.println("pbView");

		PBService.updatecount(par_no);

		List<pb_join> joinlist = PBService.getJoinList(par_no);
		partner partner = PBService.getBoard(par_no);
		String content = partner.getContent().replace("\n", "<br>");
		List<partner_comment> pclist = PBService.getPcList(par_no);

		pb_join pb = new pb_join();
		pb.setJoin_id(id);
		pb.setPar_no(par_no);
		System.out.println("getID" + pb.getJoin_id());
		System.out.println("getno" + pb.getPar_no());
		System.out.println("id:" + id);
		System.out.println("joinlist:" + joinlist);
		result = PBService.chkID(pb); // model로 추가해주기

		System.out.println("result:" + result);

		model.addAttribute("pclist", pclist);
		model.addAttribute("par_no", par_no);
		model.addAttribute("partner", partner);
		model.addAttribute("content", content);
		model.addAttribute("page", page);
		model.addAttribute("result", result);
		model.addAttribute("joinlist", joinlist);

		return "partner/pbView";

	}

	// 수정 폼
	@RequestMapping("pbUpdate.do")
	public String pbUpdate(int par_no, int page, Model model) {
		System.out.println("pbUpdate");
		System.out.println("no:" + par_no);
		partner partner = PBService.getBoard(par_no); // 상세 정보 구하기

		model.addAttribute("partner", partner);
		model.addAttribute("page", page);

		return "partner/pbUpdate";
	}

	// 글수정
	@RequestMapping("pbUpdateResult.do")
	public String pbUpdateResult(HttpServletRequest request, partner partner, int page, int no, Model model) {
		int result = 0;
		System.out.println("pbUpdateResult");
		System.out.println("no:" + no);

		// System.out.println("subject:"+partner.getSubject());
		// System.out.println("content:"+partner.getContent());
		partner old = PBService.getBoard(no);

		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");

		System.out.println("id확인:" + old.getId() + "," + id);
		if (old.getId().equals(id)) { // id 일치시
			System.out.println("subject:" + partner.getSubject());
			System.out.println("content:" + partner.getContent());
			System.out.println("par_no:" + partner.getPar_no());
			System.out.println("id:" + partner.getId());
			System.out.println("content:" + partner.getCamp_area());
			System.out.println("content:" + partner.getCamp_type());
			result = PBService.update(partner);
		} else { // 비번 불일치시
			result = -1;
		}
		model.addAttribute("result", result);
		model.addAttribute("partner", partner);
		model.addAttribute("page", page);
		model.addAttribute("no", no);
		System.out.println("result" + result);
		return "partner/pbUpdateResult";
	}

	// 삭제 폼
	@RequestMapping("pbDelete.do")
	public String pbDelete() {
		System.out.println("pbDelete");
		return "partner/pbDelete";
	}

	// 글삭제
	@RequestMapping("pbDeleteResult.do")
	public String pbDeleteResult(HttpServletRequest request, partner partner, int page, int no, Model model) {
		int result = 0;

		System.out.println("pbDeleteResult");

		System.out.println("page:" + page);
		System.out.println("parno:" + no);
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");

		partner old = PBService.getBoard(no);

		System.out.println("id:" + old.getId() + ",id:" + id);
		if (old.getId().equals(id)) { // id 일치시
			result = PBService.delete(no);
		} else if (id.equals("admin")) { // 비번 불일치시
			result = PBService.delete(no);
		} else {
			result = -1;
		}
		model.addAttribute("result", result);
		model.addAttribute("page", page);
		model.addAttribute("no", no);

		return "partner/pbDeleteResult";
	}

	@RequestMapping("pbJoin.do")
	public String pbJoin(HttpServletRequest request, msg msg, int par_no, int page, Model model) {
		int result = 0;

		System.out.println("pbJoin");

		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		partner old = PBService.getBoard(par_no);

		pb_join pb = new pb_join();
		pb.setJoin_id(id);
		pb.setPar_no(par_no);

		System.out.println("pbid:" + pb.getJoin_id());
		System.out.println("pbno:" + pb.getPar_no());
		if (old.getId().equals(id)) { // id 일치시
			result = -1;
		} else { // 비번 불일치시
			if (old.getNow_num() <= old.getWant_num()) {
				result = PBService.join(pb);
				System.out.println("par_no_chk" + par_no);
				PBService.pb_count(par_no);

				msg.setId(id);
				msg.setRecv_id(old.getId());
				msg.setContent("새로운 참가 신청이 있습니다." + "\n" + "게시글 번호 :" + par_no + "\n" + id + "님께서 참가 신청을 하셨습니다.");
				PBService.sendJmsg(msg);
				System.out.println("result:" + result);
			} else {
				System.out.println("마감");
			}

		}

		model.addAttribute("result", result);
		model.addAttribute("page", page);
		model.addAttribute("par_no", par_no);

		return "partner/pbJoin";
	}

	@RequestMapping("pbCancel.do")
	public String pbCancle(HttpServletRequest request, msg msg, int par_no, int page, Model model) {

		System.out.println("pbCancel");

		int result = 0;

		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		partner old = PBService.getBoard(par_no);

		pb_join pb = new pb_join();
		pb.setJoin_id(id);
		pb.setPar_no(par_no);

		System.out.println("par_no:" + par_no);
		System.out.println("id:" + id);

		result = PBService.cancel(pb);

		msg.setId(id);
		msg.setRecv_id(old.getId());
		msg.setContent("게시글 번호 :" + par_no + "\n" + id + "님께서 참석을 취소 하였습니다.");
		PBService.sendJmsg(msg);

		System.out.println("result:" + result);
		PBService.pb_discount(par_no);

		model.addAttribute("result", result);
		model.addAttribute("page", page);
		model.addAttribute("par_no", par_no);

		return "partner/pbCancel";
	}

	// 댓글 작성

	@RequestMapping("pbreply.do")
	public String replywrite(partner_comment pc, int page, int par_no, HttpSession session, Model model) {

		System.out.println("pbreply");
		String id = (String) session.getAttribute("id");
		System.out.println("id=" + id);

		int result = 0;

		if (id == null) {
			result = 2;
			return "partner/pcresult";
		} else {
			pc.setId(id);

			result = PBService.pcinsert(pc);
			System.out.println("result :" + result);

			model.addAttribute("id", id);
			model.addAttribute("pc", pc);
			model.addAttribute("page", page);
			model.addAttribute("par_no", par_no);
			model.addAttribute("result", result);

			return "partner/pcresult";
		}
	}

	// 댓글 삭제
	@RequestMapping("pcdelete.do")
	public String replydelete(partner_comment pc, int com_no, int page, Model model) {
		System.out.println("pcdelete");

		int result = PBService.pcdelete(com_no);
		System.out.println("result :" + result);
		
		model.addAttribute("pc", pc);
		model.addAttribute("page", page);
		model.addAttribute("par_no", pc.getPar_no());
		model.addAttribute("result", result);

		return "partner/pcDeleteResult";

	}

}
