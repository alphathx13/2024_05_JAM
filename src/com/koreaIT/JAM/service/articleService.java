package com.koreaIT.JAM.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.koreaIT.JAM.dao.ArticleDao;
import com.koreaIT.JAM.dto.Article;

public class ArticleService {
	
	private ArticleDao articleDao;
	
	public ArticleService(Connection conn) {
		this.articleDao = new ArticleDao(conn);
	}

	public int articleWrite(String title, String body, int MemberId) {
		return articleDao.articleWrite(title, body, MemberId);
	}

	public List<Article> articleList(String search) {
		List<Map<String, Object>> articleList = articleDao.articleList(search);

		List<Article> foundArticle = new ArrayList<>();

		for (Map<String, Object> article : articleList)
			foundArticle.add(new Article(article));

		return foundArticle;
	}

	public Article articleDetail(String search) {
		Map<String, Object> article = articleDao.articleDetail(search);
		
		if (article.size() == 0)
			return null;
		
		return new Article(article);
	}

	public void articleModify(String articleNumber, String title, String body) {
		articleDao.articleModify(articleNumber, title, body);
	}

	public void articleDelete(String articleNumber) {
		articleDao.articleDelete(articleNumber);
	}

	public Article articleCheck(String articleNumber) {
		Map<String, Object> article = articleDao.articleCheck(articleNumber);
		
		if (article.isEmpty())
			return null;
		
		return new Article(article);
		
	}

	public int increaseViewCount(String cmd) {
		return articleDao.increaseViewCount(cmd);
	}
	
}