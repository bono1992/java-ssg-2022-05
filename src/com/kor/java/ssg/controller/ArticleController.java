package com.kor.java.ssg.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.kor.java.ssg.container.Container;
import com.kor.java.ssg.dto.Article;
import com.kor.java.ssg.service.ArticleService;
import com.kor.java.ssg.service.MemberService;
import com.kor.java.ssg.util.Util;

public class ArticleController extends Controller{
		private Scanner sc;
		private String command;
		private String actionMethodName;
		private ArticleService articleService;
		private MemberService memberService;
		
		public ArticleController(Scanner sc) {
			this.sc = sc;
			articleService = Container.articleService;
			memberService = Container.memberService;
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
				doWrite();
				break;
			default :
				System.out.println("존재하지 않는 명령어 입니다.");
				break;
			}
		}

		private void doWrite() {
			int id = Container.articleDao.getNewId();
			String regDate = Util.getNowDateStr();
			System.out.printf("#제목: ");
			String title = sc.nextLine();
			System.out.printf("#내용: ");
			String body = sc.nextLine();
			
			Article article = new Article(id, regDate, loginedMember.id, title, body);
			Container.articleDao.add(article);
			
			System.out.printf("*[%d]번 글이 생성되었습니다.*\n", id);
		}
		
		private void showList() {
			
			String searchKeyword = command.substring("article list".length()).trim();
			List<Article> forPrintArticles = articleService.getForPrintArticles(searchKeyword);
			
			if (forPrintArticles.size() == 0) {
				System.out.println("*게시글이 존재하지 않습니다.*");
				return;
		}
						
			System.out.println("====게시글====");
			for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
				Article article = forPrintArticles.get(i);
				
				String writerName = memberService.getMemberNameById(article.memberId);

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
			
			Article foundArticle = articleService.getArticleById(id);

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
		
		private void doModify() {
			System.out.print("#수정을 원하시는 게시글 번호를 입력해주세요. : ");
			int id = sc.nextInt();
			sc.nextLine();

			Article foundArticle = articleService.getArticleById(id);

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
		
		private void doDelete() {
			System.out.printf("#삭제를 원하는 게시글을 입력해주세요. : ");

			int id = sc.nextInt();

			Article foundArticle = articleService.getArticleById(id);

			if (foundArticle == null) {
				System.out.printf("*[%d]번 게시글은 존재하지 않습니다.*\n", id);
				return;
			}
			
			if (foundArticle.memberId != loginedMember.id) {
				System.out.printf("권한이 없습니다.\n");
				return;
			}

			articleService.remove(foundArticle);
			System.out.printf("*[%d]번 글이 삭제 되었습니다.*\n", id);
			sc.nextLine();
		}
	
}
