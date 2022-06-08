package com.kor.java.ssg;

import java.util.Scanner;

import com.kor.java.ssg.controller.ArticleController;
import com.kor.java.ssg.controller.Controller;
import com.kor.java.ssg.controller.ExportController;
import com.kor.java.ssg.controller.MemberController;

public class App {
	public void Start() {
		System.out.println("=== 프로그램 시작 ===");

		//makeTestData();

		Scanner sc = new Scanner(System.in);
		
		MemberController memberController = new MemberController(sc);
		ArticleController articleController = new ArticleController(sc);
		ExportController exportController = new ExportController(sc);

		// while 문에 넣으면 초기화 됌
		// LocalDateTime now = LocalDateTime.now();
		// String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일
		// HH시 mm분 ss초"));
			
		while (true) {
			System.out.printf("Command# ");
			String command = sc.nextLine();

			command = command.trim(); // 공백제거

			if (command.length() == 0) {
				continue;
			}

			if (command.equals("exit")) {
				break;
			}
			
			String[] commandBits = command.split(" ");
			
			if (commandBits.length == 1) {
				System.out.println("존재하지 않는 명령어 입니다.");
				continue;
			}
		
			String controllerName = commandBits[0];
			String actionMethodName = commandBits[1];
			
			Controller controller = null;
			
			if (controllerName.equals("article")) {
				controller = articleController;
			}
			
			else if (controllerName.equals("member")) {
				controller = memberController;
			}
			
			else if (controllerName.equals("export")) {
				controller = exportController;
			}
			
			else {
				System.out.printf("[%s](은)는 존재하지 않는 명령어 입니다.\n", command);
				continue;
			}
			
			String actionName = controllerName + "/" + actionMethodName;
			
			switch (actionName) {
			case "article/write" :
			case "article/delete" :
			case "article/modify" :
			case "membet/logout" :
					if ( Controller.isLogined() == false) {
						System.out.println("로그인 후 이용해주세요.");
						continue;
					}
					break;
			}
			
			switch (actionName) {
			case "member/join" :
			case "member/login" :
					if ( Controller.isLogined()) {
						System.out.println("로그아웃 후 이용해주세요.");
						continue;
					}
					break;
			}
			
			controller.doAction(command, actionMethodName);
		}
	
		sc.close();
		System.out.println("=== 프로그램 종료 ===");
	}

	/*private void makeTestData() {
		System.out.println("테스트를 위한 데이터를 생성합니다.");

		articles.add(new Article(1, Util.getNowDateStr(), "제목1", "내용1", 10));
		articles.add(new Article(2, Util.getNowDateStr(), "제목2", "내용2", 22));
		articles.add(new Article(3, Util.getNowDateStr(), "제목3", "내용3", 33));
	}*/
}