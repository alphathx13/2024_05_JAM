package com.koreaIT.JAM.dto;

import java.time.LocalDateTime;

import com.koreaIT.JAM.util.Util;

public class Article {
	int articleId;
	String regDate;
	String updateDate;
	String title;
	String body;

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Article(int id, String regDate, String updateDate, String title, String body) {
		this.articleId = id;
		this.title = title;
		this.body = body;
		this.regDate = regDate;
		this.updateDate = updateDate;
	}
}