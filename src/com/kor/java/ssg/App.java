package com.kor.java.ssg;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.kor.java.ssg.dto.Article;
import com.kor.java.ssg.util.Util;

public class App {
	private List<Article> articles;

	public App() {
		articles = new ArrayList<>();
	}

	public void Start() {
		System.out.println("=== 프로그램 시작 ===");

		// makeTestData();

		Scanner sc = new Scanner(System.in);

		// while 문에 넣으면 초기화 됌
		// LocalDateTime now = LocalDateTime.now();
		// String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일
		// HH시 mm분 ss초"));

		while (true) {
			System.out.printf("명령어># ");
			String command = sc.nextLine();

			command = command.trim(); // 공백제거

			if (command.length() == 0) {
				continue;
			}

			if (command.equals("exit")) {
				break;
			} else if (command.equals("article list")) {
				if (articles.size() == 0) {
					System.out.println("게시글이 존재하지 않습니다.");
					continue;
				}

				System.out.println("====게시글====");
				for (int i = articles.size() - 1; i >= 0; i--) {
					Article article = articles.get(i);

					System.out.printf("[게시글] : %d\n", article.id);
					System.out.printf("[제목] : %s\n", article.title);
					System.out.printf("[내용] : %s\n", article.body);
					System.out.printf("[날짜] : %s\n", article.regDate);
					System.out.printf("[조회수] : %d\n", article.hit);
				}

			} else if (command.equals("article write")) {
				int id = articles.size() + 1;
				String regDate = Util.getNowDateStr();
				System.out.printf("[제목] : ");
				String title = sc.nextLine();
				System.out.printf("[내용] : ");
				String body = sc.nextLine();

				Article article = new Article(id, regDate, title, body);
				articles.add(article);

				System.out.printf("[%d]번 게시글이 생성 되었습니다.\n", id);
				System.out.printf("[생성시간] : %s \n", regDate);

			}

			else if (command.equals("article detail")) {

				System.out.print("열람을 원하는 게시글 번호를 입력해주세요. : ");
				int id = sc.nextInt();
				
				Article foundArticle = getArticleById(id);

				if (foundArticle == null) {
					System.out.printf("[%d]번 게시글은 존재하지 않습니다.\n", id);
					continue;
				}

				foundArticle.increaseHit();

				System.out.printf("[게시글] : %d\n", foundArticle.id);
				System.out.printf("[제목] : %s\n", foundArticle.title);
				System.out.printf("[내용] : %s\n", foundArticle.body);
				System.out.printf("[날짜] : %s\n", foundArticle.regDate);
				System.out.printf("[조회수] : %d\n", foundArticle.hit);
				sc.nextLine();
			}

			else if (command.equals("article modify")) {

				System.out.print("수정을 원하시는 게시글 번호를 입력해주세요. : ");
				int id = sc.nextInt();
				sc.nextLine();

				Article foundArticle = getArticleById(id);

				if (foundArticle == null) {
					System.out.printf("[%d]번 게시글은 존재하지 않습니다.\n", id);
					continue;
				}

				System.out.printf("[제목] : ");
				String title = sc.nextLine();
				System.out.printf("[내용] : ");
				String body = sc.nextLine();

				foundArticle.title = title;
				foundArticle.body = body;

				System.out.printf("[%d]번 게시글이 수정되었습니다.\n", id);
			}

			else if (command.equals("article delete")) {

				System.out.printf("삭제를 원하는 게시글을 입력해주세요. : ");

				int id = sc.nextInt();
				int foundIndex = getArticleIndexById(id);

				if (foundIndex == -1) {
					System.out.printf("[%d]번 게시글은 존재하지 않습니다.\n", id);
					continue;
				}

				articles.remove(foundIndex);
				System.out.printf("[%d]번 글이 삭제 되었습니다.\n", id);
				sc.nextLine();
			}

			else {
				System.out.printf("[%s](은)는 존재하지 않는 명령어 입니다.\n", command);
			}
		}

		sc.close();
		System.out.println("=== 프로그램 끝 ===");
	}

private int getArticleIndexById(int id) { //중복제거 구문
	int i = 0;
	for (Article article : articles) {
		if (article.id == id) {
			return i;
			}
		i++;
		}
	return -1;
	}

private Article getArticleById(int id) {
	int index = getArticleIndexById(id);
	
	if (index != -1) {
		return articles.get(index);
		}
	return null;
	}
}