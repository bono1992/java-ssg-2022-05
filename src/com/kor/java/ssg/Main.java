package com.kor.java.ssg;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		System.out.println("=== 프로그램 시작 ===");
		Scanner sc = new Scanner(System.in);
		
		int lastArticleId = 0; // while 문에 넣으면 초기화 됌
		LocalDateTime now = LocalDateTime.now();
		String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초"));
		
		List<Article> articles = new ArrayList<>();
		
		while(true) {
			System.out.println("명령어) ");
			String command = sc.nextLine();
			
			command = command.trim(); // 공백제거
			
			if (command.length() == 0) {
				continue;
			}
			
			if (command.equals("exit")) {
				break;
			}
			else if (command.equals("article list")) {
				if (articles.size() == 0) {
					System.out.println("게시글이 없습니다.");
				continue;
				}
				
				System.out.println("====게시글====");
				for (int i = articles.size() -1; i >=0; i--) {
					Article article = articles.get(i);
					
					System.out.printf("%d번 게시글\n제목: %s\n내용: %s\n날짜: %s\n", article.id, article.title, article.body, article.formatedNow);
				}
				
			}
			else if (command.equals("article write")) {
				int id = lastArticleId + 1;
				lastArticleId = id;
				System.out.printf("제목 : ");
				String title = sc.nextLine();
				System.out.printf("내용 : ");
				String body = sc.nextLine();
				
				Article article = new Article(id, title, body, formatedNow);
				articles.add(article);
				
				System.out.printf("%d번 게시글이 생성 되었습니다.\n", id);
				System.out.printf("생성시간 : %s \n",formatedNow);

			}
			
			else if (command.equals("article delete")) {
				System.out.print("삭제를 원하는 게시글을 입력해주세요. : ");
				int num = 0;
				try {
					num = sc.nextInt();
				}
				catch (Exception e) {
					System.out.println("맞지않는 형식입니다. 다시 입력해주세요.");
				}
				
				for (int i = articles.size() -1; i >= 0; i--) {
					Article article = articles.get(i);
					
					if (num == articles.size()) {
						articles.remove(articles.get(i));
						System.out.printf("%d번 글이 삭제 되었습니다.\n", article.id);
						System.out.printf("삭제된 시간 : %s\n",formatedNow);
						break;
					}
				}
			}
			
			else if (command.equals("article detail")) {
				System.out.print("열람을 원하는 게시글 번호를 입력해주세요. : ");
				int num = sc.nextInt();
				
				for (int i = articles.size() -1; i >= 0; i--) {
					Article article = articles.get(i);
					
						if (num == articles.size()) {
							System.out.printf("%d번 게시글\n제목: %s\n내용: %s\n날짜: %s\n", article.id, article.title, article.body, article.formatedNow);
							break;
								}
						else {
							System.out.printf("%d 번은(는) 존재하지 않는 게시글 입니다.\n", num);
						}
					}
				}
			
			else {
				System.out.printf("%s(은)는 존재하지 않는 명령어 입니다.\n", command);
			}
		}
		
		sc.close();
		System.out.println("=== 프로그램 끝 ===");
	}
}

class Article {
	int id;
	String title;
	String body;
	String formatedNow;
	
	public Article(int id, String title, String body, String formatedNow) {
		this.id = id;
		this.title = title;
		this.body = body;
		this.formatedNow = formatedNow;
	}
}
