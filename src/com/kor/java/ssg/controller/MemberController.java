package com.kor.java.ssg.controller;

import java.util.Scanner;

import com.kor.java.ssg.container.Container;
import com.kor.java.ssg.dto.Member;
import com.kor.java.ssg.service.MemberService;
import com.kor.java.ssg.util.Util;

public class MemberController extends Controller{
	private Scanner sc;
	private String command;
	private String actionMethodName;
	private MemberService memberService;
	
	public MemberController(Scanner sc) {
		this.sc = sc;
				
		memberService = Container.memberService;
	}
	
	public void doAction(String command, String actionMethodName) {
		this.command = command;
		this.actionMethodName = actionMethodName;
		
		switch (actionMethodName) {
		case "join" :
			doJoin();
			break;
		case "login" :
			doLogin();
			break;
		case "logout" :
			doLogout();
			break;
		default:
			System.out.println("존재하지 않는 명령어 입니다.");
			break;
		}
	}
	
	private void doLogout() {
		loginedMember = null;
		System.out.println("*로그아웃 되었습니다.*");
	}
	
	private void doLogin() {
		System.out.printf("로그인 아이디 : ");
		String loginId = sc.nextLine();
		System.out.printf("로그인 비밀번호 : ");
		String loginPw = sc.nextLine();
		
		Member member = memberService.getMemberByLoginId(loginId);
		
		if (member == null) {
			System.out.println("해당회원은 존재하지 않습니다.");
			return;
		}
		
		if (member.loginPw.equals(loginPw) == false) {
			System.out.println("비밀번호를 확인해주세요.");
			return;
		}
		
		loginedMember = member;
		System.out.printf("로그인 성공! %s님 환영합니다.\n", loginedMember.name);
		
	}
	
	private boolean isJoinableLoginId(String loginId) {
		int index = memberService.getMemberIndexByLoginId(loginId);
		
		if ( index == -1 ) {
			return true;
		}
		
		return false;
	}

	public void doJoin() {
		int id = Container.memberDao.getNewId();
		String regDate = Util.getNowDateStr();
		
		String loginId = null;
		System.out.println("****회원가입을 진행 합니다.****");
		
		while(true) {
			System.out.printf("아이디: ");
			loginId = sc.nextLine();
			
			if (isJoinableLoginId(loginId) == false) {
				System.out.printf("%s(은)는 이미 사용중인 아이디 입니다.", loginId);
				continue;
			}
			
			break;
		}
		
		String loginPw = null;
		String logidPwConfirm = null;
		
		while(true) {
			System.out.printf("비밀번호: ");
			loginPw = sc.nextLine();
			System.out.printf("비밀번호 확인: ");
			logidPwConfirm = sc.nextLine();
			
			if(loginPw.equals(logidPwConfirm) == false) {
				System.out.println("비밀번호를 다시 입력해주세요.");
				continue;
			}
			
			break;
		}
		
		System.out.printf("이름: ");
		String name = sc.nextLine();
		
		Member member = new Member(id, regDate, loginId, loginPw, name);
		memberService.join(member);
		
		System.out.printf("%d번 회원이 생성되었습니다. 환영합니다.\n", id);
		
	}

}
