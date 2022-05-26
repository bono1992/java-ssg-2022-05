package com.kor.java.ssg.controller;

import java.util.List;
import java.util.Scanner;

import com.kor.java.ssg.dto.Member;
import com.kor.java.ssg.util.Util;

public class MemberController {
	private Scanner sc;
	private List<Member> members;
	
	public MemberController(Scanner sc, List<Member> members) {
		this.sc = sc;
		this.members = members;
	}
	
	private int getMemberindexByLoginId(String loginId) {
		int i = 0;
		for ( Member member : members ) {
		if ( member.loginId.equals(loginId)) {
				return i;
			}
		}
		
		return -1;
	}
	
	private boolean isJoinableLoginId(String loginId) {
		int index = getMemberindexByLoginId(loginId);
		
		if ( index == -1 ) {
			return true;
		}
		
		return false;
	}

	public void doJoin() {
		int id = members.size() + 1;
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
		members.add(member);
		
		System.out.printf("%d번 회원이 생성되었습니다. 환영합니다.\n", id);
		
	}

}
