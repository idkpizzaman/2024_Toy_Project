package com.project.toy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.toy.service.MemberService;
import com.project.toy.util.Util;
import com.project.toy.vo.Member;
import com.project.toy.vo.ResultData;
import com.project.toy.vo.Rq;

@Controller
public class MemberController {
	private MemberService memberService;
	private Rq rq;
	
	public MemberController(MemberService memberService, Rq rq) {
		this.memberService = memberService;
		this.rq = rq;
	}
	
	@GetMapping("/usr/member/join")
	public String join() {
		return "usr/member/join";
	}
	
	@GetMapping("/usr/member/loginIdDupChk")
	@ResponseBody
	public ResultData<Member> loginIdDupChk(String loginId) {
		Member member = memberService.getMemberByLoginId(loginId);

		if (member != null) {
			return ResultData.from("F-1", String.format("[ %s ] 은(는) 이미 사용중인 아이디입니다", loginId));
		}
		
		return ResultData.from("S-1", String.format("[ %s ] 은(는) 사용가능한 아이디입니다", loginId));
	}
	
	@PostMapping("/usr/member/doJoin")
	@ResponseBody
	public String doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email) {

		memberService.joinMember(loginId, loginPw, name, nickname, cellphoneNum, email);
		
		return Util.jsReplace(String.format("%s님이 가입되었습니다", nickname), "/");
	}

	@GetMapping("/usr/member/login")
	public String login() {
		return "usr/member/login";
	}
	
	@PostMapping("/usr/member/doLogin")
	@ResponseBody
	public String doLogin(String loginId, String loginPw) {
		Member member = memberService.getMemberByLoginId(loginId);
		
		if (member == null) {
			return Util.jsHistoryBack(String.format("[%s]는 존재하지 않는 아이디입니다.", loginId));
		}
		
		if (member.getLoginPw().equals(loginPw) == false) {
			return Util.jsHistoryBack("비밀번호가 잘못되었습니다.");
		}
		
		rq.login(member);
		
		return Util.jsReplace(String.format("[%s]님 환영합니다!", member.getNickname()), "/");
	}
	
	@GetMapping("/usr/member/doLogout")
	@ResponseBody
	public String doLogout() {
		rq.logout();

		return Util.jsReplace("정상적으로 로그아웃 되었습니다.", "/");
	}
	
	@GetMapping("/usr/member/getNickname")
	@ResponseBody
	public String getNickname() {
		Member member = memberService.getMemberById(rq.getLoginedMemberId());
		
		if (member == null) {
			return "로그인 정보 없음";
		}
		
		return member.getNickname();
	}
	
	@GetMapping("/usr/member/myPage")
	public String myPage(Model model) {
		Member member = memberService.getMemberById(rq.getLoginedMemberId());
		
		model.addAttribute("member", member);
		
		return "usr/member/myPage";
	}
	
	@GetMapping("/usr/member/checkPassword")
	public String checkPassword(Model model, String loginId) {
		model.addAttribute("loginId", loginId);
		
		return "usr/member/checkPassword";
	}
	
	@GetMapping("/usr/member/passwordModify")
	public String passwordModify() {
		return "usr/member/passwordModify";
	}
	
	@PostMapping("/usr/member/doCheckPassword")
	public String doCheckPassword(Model model, String loginPw) {
		Member member = memberService.getMemberById(rq.getLoginedMemberId());
		
		model.addAttribute("member", member);
		
		return "usr/member/modify";
	}
	
	@GetMapping("/usr/member/getMemberById")
	@ResponseBody
	public ResultData<Member> getMemberById() {

		Member member = memberService.getMemberById(rq.getLoginedMemberId());

		return ResultData.from("S-1", "회원 조회 성공", member);
	}
	
	@PostMapping("/usr/member/doModify")
	@ResponseBody
	public String doModify(String name, String nickname, String cellphoneNum, String email) {

		memberService.memberModify(rq.getLoginedMemberId(), name, nickname, cellphoneNum, email);

		Member member = memberService.getMemberById(rq.getLoginedMemberId());

		return Util.jsReplace(String.format("%s님의 회원정보가 수정되었습니다", member.getLoginId()), "myPage");
	}
	
	@PostMapping("/usr/member/doPasswordModify")
	@ResponseBody
	public String doPasswordModify(String loginPw) {

		memberService.doPasswordModify(rq.getLoginedMemberId(), loginPw);

		return Util.jsReplace("비밀번호가 변경되었습니다", "myPage");
	}
}