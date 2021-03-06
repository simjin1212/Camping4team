package camping.controller;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import camping.model.camp_loc;
import camping.model.member;
import camping.model.reservation;
import camping.model.review;
import camping.model.spot;
import camping.service.MemberServiceImpl;
import camping.service.msgService;
import camping.service.reserveService;

@Controller
public class MemberController {

	@Autowired
	private MemberServiceImpl memberService;
	@Autowired
	private msgService msv;
	@Autowired
	private reserveService rv;	
	
	// ID중복검사 ajax함수로 처리부분
	@RequestMapping(value = "member_idcheck.do", method = RequestMethod.POST)
	public String member_idcheck(@RequestParam("memid") String id, Model model) throws Exception {
		System.out.println("id:" + id);

		int result = memberService.checkMemberId(id);
		model.addAttribute("result", result);

		return "member/jsp/idcheckResult";
	}

	/* 로그인 폼 뷰 */
	@RequestMapping(value = "member_login.do")
	public String member_login() {
		return "member/jsp/member_login";
		// member 폴더의 member_login.jsp 뷰 페이지 실행
	}

	/* 비번찾기 폼 */
	@RequestMapping(value = "pwd_find.do")
	public String pwd_find() {
		return "member/jsp/pwd_find";
		// member 폴더의 pwd_find.jsp 뷰 페이지 실행
	}

	/* 회원가입 폼 */
	@RequestMapping(value = "member_join.do")
	public String member_join() {
		return "member/jsp/member_join";
		// member 폴더의 member_join.jsp 뷰 페이지 실행
	}

	/* 비번찾기 완료 */
	@RequestMapping(value = "pwd_find_ok.do", method = RequestMethod.POST)
	public String pwd_find_ok(@ModelAttribute member mem, HttpServletResponse response, Model model) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		member member = memberService.findpwd(mem);

		if (member == null) {// 값이 없는 경우

			return "member/jsp/pwdResult";

		} else {

			// Mail Server 설정
			String charSet = "utf-8";
			String hostSMTP = "smtp.naver.com";
			String hostSMTPid = "itta841@naver.com";
			String hostSMTPpwd = "0000"; // 비밀번호 입력해야함

			// 보내는 사람 EMail, 제목, 내용
			String fromEmail = "itta841@naver.com";
			String fromName = "관리자";
			String subject = "비밀번호 찾기";

			// 받는 사람 E-Mail 주소
			String mail = member.getEmail();

			try {
				HtmlEmail email = new HtmlEmail();
				email.setDebug(true);
				email.setCharset(charSet);
				email.setSSL(true);
				email.setHostName(hostSMTP);
				email.setSmtpPort(587);

				email.setAuthentication(hostSMTPid, hostSMTPpwd);
				email.setTLS(true);
				email.addTo(mail, charSet);
				email.setFrom(fromEmail, fromName, charSet);
				email.setSubject(subject);
				email.setHtmlMsg("<p align = 'center'>비밀번호 찾기</p><br>" + "<div align='center'> 비밀번호 : "
						+ member.getPwd() + "</div>");
				email.send();
			} catch (Exception e) {
				System.out.println(e);
			}

			model.addAttribute("pwdok", "등록된 email을 확인 하세요~!!");
			return "member/jsp/pwd_find";

		}

	}

	// /* 회원 가입 저장 */
	// @RequestMapping(value = "/member_join_ok.do", method =
	// RequestMethod.POST)
	// public String member_join_ok(HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	// MemberBean m = new MemberBean();
	//
	// String uploadPath = request.getRealPath("upload");
	// int size = 10 * 1024 * 1024; // 10MB까지 업로드 가능
	//
	// MultipartRequest multi = new MultipartRequest(request, uploadPath, size,
	// "utf-8",
	// new DefaultFileRenamePolicy());
	//
	// String join_id = multi.getParameter("join_id").trim();// 회원아이디
	// String join_pwd = multi.getParameter("join_pwd1").trim();// 회원비번
	// String join_name = multi.getParameter("join_name").trim();// 회원이름
	// String join_zip1 = multi.getParameter("join_zip1").trim();
	// String join_addr1 = multi.getParameter("join_addr1").trim();
	// String join_addr2 = multi.getParameter("join_addr2").trim();
	// String join_tel1 = multi.getParameter("join_tel1").trim();
	// String join_tel2 = multi.getParameter("join_tel2").trim();
	// String join_tel3 = multi.getParameter("join_tel3").trim();
	// String join_tel = join_tel1 + "-" + join_tel2 + "-" + join_tel3;
	// String join_phone1 = multi.getParameter("join_phone1").trim();
	// String join_phone2 = multi.getParameter("join_phone2").trim();
	// String join_phone3 = multi.getParameter("join_phone3").trim();
	// String join_phone = join_phone1 + "-" + join_phone2 + "-" + join_phone3;
	// String join_mailid = multi.getParameter("join_mailid").trim();
	// String join_maildomain = multi.getParameter("join_maildomain").trim();
	// String join_email = join_mailid + "@" + join_maildomain;
	//
	// // 첨부 파일 받는 부분
	// m.setJoin_profile(multi.getFilesystemName((String)
	// multi.getFileNames().nextElement()));
	//
	// m.setJoin_id(join_id);
	// m.setJoin_pwd(join_pwd);
	// m.setJoin_name(join_name);
	// m.setJoin_zip1(join_zip1);
	// // m.setJoin_zip2(join_zip2);
	// m.setJoin_addr1(join_addr1);
	// m.setJoin_addr2(join_addr2);
	// m.setJoin_tel(join_tel);
	// m.setJoin_phone(join_phone);
	// m.setJoin_email(join_email);
	//
	// memberService.insertMember(m);
	//
	// return "redirct:member_login.do";
	// }

	/* 회원 가입 저장(fileupload) */
	@RequestMapping(value = "member_join_ok.do", method = RequestMethod.POST)
	public String member_join_ok(@RequestParam("profile1") MultipartFile mf, member member,
			HttpServletRequest request, Model model) throws Exception {

		String filename = mf.getOriginalFilename();
		int size = (int) mf.getSize(); // 첨부파일의 크기 (단위:Byte)

		String path = request.getRealPath("upload");
		System.out.println("mf=" + mf);
		System.out.println("filename=" + filename); // filename="Koala.jpg"
		System.out.println("size=" + size);
		System.out.println("Path=" + path);
		int result = 0;

		String file[] = new String[2];
//		file = filename.split(".");
//		System.out.println(file.length);
//		System.out.println("file0="+file[0]);
//		System.out.println("file1="+file[1]);

		String newfilename = "";

		if (size > 0) { // 첨부파일이 전송된 경우

			// 파일 중복문제 해결
			String extension = filename.substring(filename.lastIndexOf("."), filename.length());
			System.out.println("extension:" + extension);

			UUID uuid = UUID.randomUUID();
			

			newfilename = uuid.toString() + extension;
			System.out.println("newfilename:" + newfilename);

			StringTokenizer st = new StringTokenizer(filename, ".");
			file[0] = st.nextToken(); // 파일명 Koala
			file[1] = st.nextToken(); // 확장자 jpg

			if (size > 100000) { // 100KB
				result = 1;
				model.addAttribute("result", result);

				return "member/jsp/uploadResult";

			} else if (!file[1].equals("jpg") && !file[1].equals("gif") && !file[1].equals("png")) {

				result = 2;
				model.addAttribute("result", result);

				return "member/jsp/uploadResult";
			}
		}

		if (size > 0) { // 첨부파일이 전송된 경우

			mf.transferTo(new File(path + "/" + newfilename));
			member.setProfile(newfilename);

		}else {
			String basicprofile = "basic_image.png";
			member.setProfile(basicprofile);
		}

		String jumin1 = request.getParameter("jumin1").trim();
		String jumin2 = request.getParameter("jumin2").trim();
		String jumin = jumin1 + "-" + jumin2;
		String phone1 = request.getParameter("phone1").trim();
		String phone2 = request.getParameter("phone2").trim();
		String phone3 = request.getParameter("phone3").trim();
		String phone = phone1 + "-" + phone2 + "-" + phone3;
		String mailid = request.getParameter("mailid").trim();
		String maildomain = request.getParameter("maildomain").trim();
		String email = mailid + "@" + maildomain;

		member.setJumin(jumin);
		member.setPhone(phone);
		member.setEmail(email);


		memberService.insertMember(member);

		return "redirect:member_login.do";
	}

	/* 로그인 인증 */
	@RequestMapping(value = "member_login_ok.do", method = RequestMethod.POST)
	public String member_login_ok(@RequestParam("id") String id, @RequestParam("pwd") String pwd, HttpSession session,
			Model model) throws Exception {
		int result = 0;
		member m = memberService.userCheck(id);

		if (m == null) { // 등록되지 않은 회원일때

			result = 1;
			model.addAttribute("result", result);

			return "member/jsp/loginResult";

		} else { // 등록된 회원일때
			if (m.getPwd().equals(pwd)) {// 비번이 같을때
				session.setAttribute("id", id);

				String name = m.getName();
				String profile = m.getProfile();

//				model.addAttribute("name", name);
				model.addAttribute("profile", profile);

				return "redirect:/layout2.do";

			} else {// 비번이 다를때
				result = 2;
				model.addAttribute("result", result);

				return "member/jsp/loginResult";
			}
		}

	}

	/* 회원정보 수정 폼 */
	@RequestMapping(value = "member_edit.do")
	public String member_edit(HttpSession session, Model m) throws Exception {

		String id = (String) session.getAttribute("id");

		camping.model.member editm = memberService.userCheck(id);

		String jumin = editm.getJumin();
		StringTokenizer st01 = new StringTokenizer(jumin, "-");
		// java.util 패키지의 StringTokenizer 클래스는 첫번째 전달인자를
		// 두번째 -를 기준으로 문자열을 파싱해준다.
		String jumin1 = st01.nextToken();// 첫번째 주민번호 저장
		String jumin2 = st01.nextToken(); // 두번째 주민번호 저장

		String phone = editm.getPhone();
		StringTokenizer st02 = new StringTokenizer(phone, "-");
		// java.util 패키지의 StringTokenizer 클래스는 첫번째 전달인자를
		// 두번째 -를 기준으로 문자열을 파싱해준다.
		String phone1 = st02.nextToken();// 첫번째 전화번호 저장
		String phone2 = st02.nextToken(); // 두번째 전번 저장
		String phone3 = st02.nextToken();// 세번째 전번 저장

		String email = editm.getEmail();
		StringTokenizer st03 = new StringTokenizer(email, "@");
		// java.util 패키지의 StringTokenizer 클래스는 첫번째 전달인자를
		// 두번째 @를 기준으로 문자열을 파싱해준다.
		String mailid = st03.nextToken();// 첫번째 전화번호 저장
		String maildomain = st03.nextToken(); // 두번째 전번 저장

		m.addAttribute("editm", editm);
		m.addAttribute("jumin1", jumin1);
		m.addAttribute("jumin2", jumin2);
		m.addAttribute("phone1", phone1);
		m.addAttribute("phone2", phone2);
		m.addAttribute("phone3", phone3);
		m.addAttribute("mailid", mailid);
		m.addAttribute("maildomain", maildomain);

		return "member/jsp/member_edit";
	}

	/* 회원정보 수정(cos) */
	// @RequestMapping(value = "/member_edit_ok.do", method =
	// RequestMethod.POST)
	// public String member_edit_ok(HttpServletRequest request,
	// HttpSession session,
	// Model mv) throws Exception {
	//
	// MemberBean member = new MemberBean();
	//
	// String uploadPath = request.getRealPath("upload");
	// int size = 10 * 1024 * 1024; // 10MB까지 업로드 가능
	//
	// MultipartRequest multi =
	// new MultipartRequest(request,
	// uploadPath,
	// size,
	// "utf-8",
	// new DefaultFileRenamePolicy());
	//
	// String id = (String) session.getAttribute("id");
	//
	// String join_pwd = multi.getParameter("join_pwd1").trim();
	// String join_name = multi.getParameter("join_name").trim();
	// String join_zip1 = multi.getParameter("join_zip1").trim();
	// // String join_zip2 = multi.getParameter("join_zip2").trim();
	// String join_addr1 = multi.getParameter("join_addr1").trim();
	// String join_addr2 = multi.getParameter("join_addr2").trim();
	// String join_tel1 = multi.getParameter("join_tel1").trim();
	// String join_tel2 = multi.getParameter("join_tel2").trim();
	// String join_tel3 = multi.getParameter("join_tel3").trim();
	// String join_tel = join_tel1 + "-" + join_tel2 + "-" + join_tel3;
	// String join_phone1 = multi.getParameter("join_phone1").trim();
	// String join_phone2 = multi.getParameter("join_phone2").trim();
	// String join_phone3 = multi.getParameter("join_phone3").trim();
	// String join_phone = join_phone1 + "-" + join_phone2 + "-" + join_phone3;
	// String join_mailid = multi.getParameter("join_mailid").trim();
	// String join_maildomain = multi.getParameter("join_maildomain").trim();
	// String join_email = join_mailid + "@" + join_maildomain;
	//
	// MemberBean editm = this.memberService.userCheck(id);
	// // 아이디를 기준으로 디비로 부터 회원정보를 가져옴
	//
	// // 첨부 파일 받는 부분
	// String join_profile = multi.getFilesystemName((String)
	// multi.getFileNames().nextElement());
	// if (join_profile == null) { // 첨부 파일이 수정되지 않으면
	// member.setJoin_profile(editm.getJoin_profile());
	// } else { // 첨부파일이 수정
	// member.setJoin_profile(join_profile);
	// }
	//
	// member.setJoin_id(id);
	// member.setJoin_pwd(join_pwd);
	// member.setJoin_name(join_name);
	// member.setJoin_zip1(join_zip1);
	// // member.setJoin_zip2(join_zip2);
	// member.setJoin_addr1(join_addr1);
	// member.setJoin_addr2(join_addr2);
	// member.setJoin_tel(join_tel);
	// member.setJoin_phone(join_phone);
	// member.setJoin_email(join_email);
	//
	// memberService.updateMember(member);// 수정 메서드 호출
	//
	// mv.addAttribute("join_name", member.getJoin_name());
	// mv.addAttribute("join_profile", member.getJoin_profile());
	//
	// return "member/main";
	// }

	/* 회원정보 수정(fileupload) */
	@RequestMapping(value = "member_edit_ok.do", method = RequestMethod.POST)
	public String member_edit_ok(@RequestParam("profile1") MultipartFile mf, member member,
			HttpServletRequest request, HttpSession session, Model model) throws Exception {

		String filename = mf.getOriginalFilename();
		int size = (int) mf.getSize();
		
		String path = request.getRealPath("upload");
		System.out.println("mf=" + mf);
		System.out.println("filename=" + filename); // filename="Koala.jpg"
		System.out.println("size=" + size);
		System.out.println("path:" + path);
		int result = 0;
		
		String file[] = new String[2];
//		file = filename.split(".");
//		System.out.println(file.length);
//		System.out.println("file0="+file[0]);
//		System.out.println("file1="+file[1]);

		String newfilename = "";

		if (size > 0) { // 첨부파일이 전송된 경우

			// 파일 중복문제 해결
			String extension = filename.substring(filename.lastIndexOf("."), filename.length());
			System.out.println("extension:" + extension);

			UUID uuid = UUID.randomUUID();

			newfilename = uuid.toString() + extension;
			System.out.println("newfilename:" + newfilename);

			StringTokenizer st = new StringTokenizer(filename, ".");
			file[0] = st.nextToken(); // 파일명
			file[1] = st.nextToken(); // 확장자

			if (size > 100000) {
				result = 1;
				model.addAttribute("result", result);

				return "member/jsp/uploadResult";

			} else if (!file[1].equals("jpg") && !file[1].equals("gif") && !file[1].equals("png")) {

				result = 2;
				model.addAttribute("result", result);

				return "member/jsp/uploadResult";
			}

		}

		if (size > 0) { // 첨부파일이 전송된 경우

			mf.transferTo(new File(path + "/" + newfilename));
			member.setProfile(newfilename);
		}else {
			String basicprofile = "basic_image.png";
			member.setProfile(basicprofile);
		}

		String id = (String) session.getAttribute("id");
		
		String jumin1 = request.getParameter("jumin1").trim();
		String jumin2 = request.getParameter("jumin2").trim();
		String jumin = jumin1 + "-" + jumin2;
		String phone1 = request.getParameter("phone1").trim();
		String phone2 = request.getParameter("phone2").trim();
		String phone3 = request.getParameter("phone3").trim();
		String phone = phone1 + "-" + phone2 + "-" + phone3;
		String mailid = request.getParameter("mailid").trim();
		String maildomain = request.getParameter("maildomain").trim();
		String email = mailid + "@" + maildomain;

		member editm = this.memberService.userCheck(id);	

		/*
		 * if (size > 0) { // 첨부 파일이 수정되면 member.setProfile(newfilename); } else { //
		 * 첨부파일이 수정되지 않으면 member.setProfile(editm.getProfile()); }
		 */
		member.setId(id);
		member.setJumin(jumin);
		member.setPhone(phone);
		member.setEmail(email);

		memberService.updateMember(member);// 수정 메서드 호출

		model.addAttribute("name", member.getName());
		model.addAttribute("profile", member.getProfile());
		

		return "member/jsp/main";
	}

	/* 회원정보 삭제 폼 */
	@RequestMapping(value = "member_del.do")
	public String member_del(HttpSession session, Model dm) throws Exception {

		String id = (String) session.getAttribute("id");
		camping.model.member deleteM = memberService.userCheck(id);
		dm.addAttribute("d_id", id);
		dm.addAttribute("d_name", deleteM.getName());

		return "member/jsp/member_del";
	}

	/* 회원정보 삭제 완료 */
	@RequestMapping(value = "member_del_ok.do", method = RequestMethod.POST)
	public String member_del_ok(@RequestParam("pwd") String pass,
			HttpSession session) throws Exception {

		String id = (String) session.getAttribute("id");
		camping.model.member member = this.memberService.userCheck(id);

		if (!member.getPwd().equals(pass)) {

			return "member/jsp/deleteResult";

		} else { // 비번이 같은 경우

			String up = session.getServletContext().getRealPath("upload");
			String fname = member.getProfile();
			System.out.println("up:" + up);

			// 디비에 저장된 기존 이진파일명을 가져옴
			if (fname != null) {// 기존 이진파일이 존재하면
				File delFile = new File(up + "/" + fname);
				delFile.delete();// 기존 이진파일을 삭제
			}
			camping.model.member delm = new camping.model.member();
			delm.setId(id);

			memberService.deleteMember(delm);// 삭제 메서드 호출

			session.invalidate(); // 세션만료

			return "redirect:member_login.do";
		}
	} 

	// 로그아웃
	@RequestMapping("member_logout.do")
	public String logout(HttpSession session) {
		session.invalidate();

		return "member/jsp/member_logout";
	}
	
	@RequestMapping("/mypage.do")
	public String mypage(HttpSession session, Model model) throws Exception {
		
				String id = (String) session.getAttribute("id");
				session.setAttribute("id", id);
				System.out.println("id="+id);
				
				member m = memberService.userCheck(id);
				String name = m.getName();
				String profile = m.getProfile();
				
				
				System.out.println("name="+name);
				
				model.addAttribute("name", name);
				model.addAttribute("profile", profile);

				return "member/jsp/main";
	}
	
	@RequestMapping("/profile.do")
	public String profile(HttpServletResponse response, @RequestParam("id") String id, Model model) throws Exception {
		System.out.println(id);
		member m = memberService.userCheck(id);
		String profile=m.getProfile();		//프로필사진
		System.out.println(profile);
		PrintWriter out = response.getWriter();
		out.print(profile);
		return null;
	}
	@RequestMapping("/navmsg.do")
	public String navmsg(HttpServletResponse response, @RequestParam("id") String id, Model model) throws Exception {
		int cnt=msv.msgcnt(id);				//안읽은 메시지
		PrintWriter out = response.getWriter();
		out.print(cnt);
		return null;
	}
	// 회원관리목록
	@RequestMapping("memberlist.do")
	public String memberlist(member m, HttpServletRequest request, HttpSession session, Model model) {

	String id = (String) session.getAttribute("id");
	System.out.println("id" + id);

	List<member> memberlist = memberService.memberlist(m);
	System.out.println("memberlist : " + memberlist);

	model.addAttribute("memberlist", memberlist);
	return "member/jsp/member_admin";
			}
	//회원관리상세
	@RequestMapping("memberdetail.do")
	public String memberdetail(member m, HttpServletRequest request, HttpSession session, Model model) {

	String id = m.getId();
	member member = memberService.memberdetail(id);
	System.out.println("member : " + member);
	
	List<reservation> reslist = memberService.reslist(id); 
	
	List<review> rlist = memberService.memberlist(id);
	System.out.println(rlist.size());
	int i=0;
	String[] cname = new String[reslist.size()];
	String[] sname = new String[reslist.size()];
	for (reservation res : reslist) {
		camp_loc loc = rv.loc(res.getCamp_no());
		spot spot = rv.spot(res.getSp_no());
		cname[i] = loc.getName();
		sname[i] = spot.getSp_name();
		i++;
	}
/*	int page = 1; // 페이지 초기값
	int limit = 10; // 한화면에 나올 데이터 개수 정의

	if (request.getParameter("page") != null) {
		page = Integer.parseInt(request.getParameter("page"));
	}*/

	model.addAttribute("cname", cname);
	model.addAttribute("sname", sname);
	model.addAttribute("member", member);
	model.addAttribute("reslist", reslist);
	model.addAttribute("rlist", rlist);
/*	model.addAttribute("page", page);*/
	return "member/jsp/member_admin_detail";
	}
	}
