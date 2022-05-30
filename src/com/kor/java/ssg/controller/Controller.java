package com.kor.java.ssg.controller;

import com.kor.java.ssg.dto.Member;

public abstract class Controller {
	public static Member loginedMember;
	public abstract void doAction(String command, String actionMethodName);
	
	//public abstract void makeTestData();
	
	public static boolean isLogined() {
		return loginedMember != null;
	}
}
