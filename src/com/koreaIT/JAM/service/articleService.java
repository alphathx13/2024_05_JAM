package com.koreaIT.JAM.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.koreaIT.JAM.dao.articleDao;

public class articleService {

	public static int articleWrite(String title, String body, Connection conn) {
		return articleDao.articleWrite(title, body, conn);
	}

	public static List<Map<String, Object>> articleList(Connection conn) {
		return articleDao.articleList(conn);
	}

	public static Map<String, Object> detail(String search, Connection conn) {
		return articleDao.articleDetail(search, conn);
	}

	public static void articleModify(String articleNumber, String title, String body, Connection conn) {
		articleDao.articleModify(articleNumber, title, body, conn);
	}

	public static void articleDelete(String articleNumber, Connection conn) {
		articleDao.articleDelete(articleNumber, conn);
	}
	
	public static boolean articleCheck(String articleNumber, Connection conn) {
		return articleDao.articleCheck(articleNumber, conn);
	}
}
