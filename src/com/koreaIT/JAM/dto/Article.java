package com.koreaIT.JAM.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class Article {
	private int articleId;
	private LocalDateTime regDate;
	private LocalDateTime updateDate;
	private String title;
	private String body;
	private String writer;
	private int writerId;
	private int viewCount;

	public int getArticleId() {
		return articleId;
	}
	
	public LocalDateTime getRegDate() {
		return regDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public String getTitle() {
		return title;
	}

	public String getBody() {
		return body;
	}
	
	public String getWriter() {
		return writer;
	}
	
	public int getWriterId() {
		return writerId;
	}

	public int getViewCount() {
		return viewCount;
	}

	public Article(Map<String, Object> articleMap) {
		this.articleId = (int) articleMap.get("id");
		this.regDate = (LocalDateTime) articleMap.get("regDate");
		this.updateDate = (LocalDateTime) articleMap.get("updateDate");
		this.title = (String) articleMap.get("title");
		this.body = (String) articleMap.get("body");
		this.writer = (String) articleMap.get("writerName");
		this.writerId = (int) articleMap.get("writer");
		this.viewCount = (int) articleMap.get("viewCount");
	}
}