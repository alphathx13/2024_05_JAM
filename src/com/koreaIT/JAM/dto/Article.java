package com.koreaIT.JAM.dto;

import java.time.LocalDateTime;

import com.koreaIT.JAM.util.Util;

public class Article {
	int articleId;
	LocalDateTime writeDate;
	LocalDateTime updateDate;
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

	public Article(int lastArticleId, String title, String body) {
		this.articleId = lastArticleId;
		this.title = title;
		this.body = body;
		this.writeDate = Util.now();
	}
}