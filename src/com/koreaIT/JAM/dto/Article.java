package com.koreaIT.JAM.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class Article {
	public int articleId;
	public LocalDateTime regDate;
	public LocalDateTime updateDate;
	public String title;
	public String body;

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

	public Article(Map<String, Object> articleMap) {
		this.articleId = (int) articleMap.get("id");
		this.regDate = (LocalDateTime) articleMap.get("regDate");
		this.updateDate = (LocalDateTime) articleMap.get("updateDate");
		this.title = (String) articleMap.get("title");
		this.body = (String) articleMap.get("body");
	}
}