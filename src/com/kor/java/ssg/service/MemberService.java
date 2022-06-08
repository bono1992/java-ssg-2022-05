package com.kor.java.ssg.service;

import com.kor.java.ssg.container.Container;
import com.kor.java.ssg.dao.MemberDao;
import com.kor.java.ssg.dto.Member;

public class MemberService {
		private MemberDao memberDao;
		
		public MemberService() {
			memberDao = Container.memberDao;
		}
		
		public Member getMemberByLoginId(String loginId) {
			return memberDao.getMemberByLoginId(loginId);
		}
		
		public int getMemberIndexByLoginId(String loginId) {
			return memberDao.getMemberIndexByLoginId(loginId);
		}
		
		public void join(Member member) {
			memberDao.add(member);
		}
		
		public String getMemberNameById(int memberId) {
			return memberDao.getMemberNameById(memberId);
		}
}
