package com.kor.java.ssg.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.kor.java.ssg.dto.Article;
import com.kor.java.ssg.util.Util;

public class ArticleController extends Controller{
		private Scanner sc;
		private List<Article> articles;
		private String command;
		private String actionMethodName;
		
		public ArticleController(Scanner sc) {
			this.sc = sc;
			
			articles = new ArrayList<Article>();
		}
		
		public void doAction(String command, String actionMethodName) {
			this.command = command;
			this.actionMethodName = actionMethodName;
			
			switch (actionMethodName) {
			case "list" :
				showList();
				break;
			case "detail" :
				showDetail();
				break;
			case "modify" :
				doModify();
				break;
			case "delete" :
				doDelete();
				break;
			case "write" :
				if (isLogined() == false) {
					System.out.println("로그인 후 이용해주세요.");
					break;
				}
				
				doWrite();
				break;
			default :
				System.out.println("존재하지 않는 명령어 입니다.");
				break;
			}
		}

		private void doWrite() {
			int id = articles.size() + 1;
			String regDate = Util.getNowDateStr();
			System.out.printf("#제목: ");
			String title = sc.nextLine();
			System.out.printf("#내용: ");
			String body = sc.nextLine();
			
			Article article = new Article(id, regDate, title, body);
			articles.add(article);
			
			System.out.printf("*[%d]번 글이 생성되었습니다.*\n", id);
		}
		
		private void showList() {
			if (articles.size() == 0) {
				System.out.println("*게시글이 존재하지 않습니다.*");
				return;
		}
			String searchKeyword = command.substring("article list".length()).trim();
			List<Article> forListArticles = articles;
			
			if (searchKeyword.length() > 0 ) {
				forListArticles = new ArrayList<>();
				
				for (Article article : articles) {
					if (article.title.contains(searchKeyword)) {
						forListArticles.add(article);
					}
				}
				
				if (articles.size() == 0) {
					System.out.println("*검색결과가 존재하지 않습니다.*");
					return;
				}
			}
			
			System.out.println("====게시글====");
			for (int i = articles.size() - 1; i >= 0; i--) {
				Article article = articles.get(i);

				System.out.printf("#게시글 : %d\n", article.id);
				System.out.printf("#제목 : %s\n", article.title);
				System.out.printf("#내용 : %s\n", article.body);
				System.out.printf("#날짜 : %s\n", article.regDate);
				System.out.printf("#조회수 : %d\n", article.hit);
			}
		}
		
		private void showDetail() {
			System.out.print("#열람을 원하는 게시글 번호를 입력해주세요. : ");
			int id = sc.nextInt();
			
			Article foundArticle = getArticleById(id);

			if (foundArticle == null) {
				System.out.printf("*[%d]번 게시글은 존재하지 않습니다.*\n", id);
				return;
			}

			foundArticle.increaseHit();

			System.out.printf("#게시글 : %d\n", foundArticle.id);
			System.out.printf("#제목 : %s\n", foundArticle.title);
			System.out.printf("#내용 : %s\n", foundArticle.body);
			System.out.printf("#날짜 : %s\n", foundArticle.regDate);
			System.out.printf("#조회수 : %d\n", foundArticle.hit);
			sc.nextLine();
		}
		
		private Article getArticleById(int id) {
			int index = getArticleIndexById(id);

			if (index != -1) {
				return articles.get(index);
			}
			return null;
		
		}
	
		private void doModify() {
			System.out.print("#수정을 원하시는 게시글 번호를 입력해주세요. : ");
			int id = sc.nextInt();
			sc.nextLine();

			Article foundArticle = getArticleById(id);

			if (foundArticle == null) {
				System.out.printf("*[%d]번 게시글은 존재하지 않습니다.*\n", id);
				return;
			}

			System.out.printf("#제목 : ");
			String title = sc.nextLine();
			System.out.printf("#내용 : ");
			String body = sc.nextLine();

			foundArticle.title = title;
			foundArticle.body = body;

			System.out.printf("*[%d]번 게시글이 수정되었습니다.*\n", id);
		}
		
		private int getArticleIndexById(int id) { // 중복제거 구문
			int i = 0;
			for (Article article : articles) {
				if (article.id == id) {
					return i;
				}
				i++;
			}
			return -1;
		}
		
		private void doDelete() {
			System.out.printf("#삭제를 원하는 게시글을 입력해주세요. : ");

			int id = sc.nextInt();
			int foundIndex = getArticleIndexById(id);

			if (foundIndex == -1) {
				System.out.printf("*[%d]번 게시글은 존재하지 않습니다.*\n", id);
				return;
			}

			articles.remove(foundIndex);
			System.out.printf("*[%d]번 글이 삭제 되었습니다.*\n", id);
			sc.nextLine();
		}
	
}
