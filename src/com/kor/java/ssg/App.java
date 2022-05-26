package com.kor.java.ssg;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.kor.java.ssg.controller.ArticleController;
import com.kor.java.ssg.controller.MemberController;
import com.kor.java.ssg.dto.Article;
import com.kor.java.ssg.dto.Member;
import com.kor.java.ssg.util.Util;

public class App {
	private List<Article> articles;
	private List<Member> members;

	public App() {
		articles = new ArrayList<>();
		members = new ArrayList<>();
	}

	public void Start() {
		System.out.println("=== 프로그램 시작 ===");

		//makeTestData();

		Scanner sc = new Scanner(System.in);
		
		MemberController memberController = new MemberController(sc, members);
		ArticleController articleController = new ArticleController(sc, articles);

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
			
			if (command.equals("join")) {
				memberController.doJoin();
			}
			
			else if (command.equals("article list")) {
				articleController.showList(command);
				}

			else if (command.equals("article write")) {
				articleController.doWrite();
				}

			else if (command.equals("article detail")) {
				articleController.showDetail(command);
				}

			else if (command.equals("article modify")) {
				articleController.doModify(command);	
				}

			else if (command.equals("article delete")) {
				articleController.doDelete(command);
				}

			else {
				System.out.printf("[%s](은)는 존재하지 않는 명령어 입니다.\n", command);
			}
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