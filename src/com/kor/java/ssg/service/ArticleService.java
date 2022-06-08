package com.kor.java.ssg.service;

import java.util.List;

import com.kor.java.ssg.container.Container;
import com.kor.java.ssg.dao.ArticleDao;
import com.kor.java.ssg.dto.Article;

public class ArticleService {
		private ArticleDao articleDao;
		
		public ArticleService() {
			this.articleDao = Container.articleDao;
		}
		
		public List<Article> getForPrintArticles(String searchKeyword) {
			return articleDao.getArticles(searchKeyword);
		}
		
		public int getArticleIndexById(int id) {
			return articleDao.getArticleIndexById(id);
		}
		
		public Article getArticleById(int id) {
			return articleDao.getArticleById(id);
		}
		
		public void remove(Article foundArticle) {
			articleDao.remove(foundArticle);
		}
		
		public List<Article> getForPrintArticles() {
			return articleDao.getArticles(null);
		}
}
