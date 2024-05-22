package com.koreaIT.JAM.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.koreaIT.JAM.dto.Article;

public class JDBC {
	private final String URL;
	private final String USER;
	private final String PASSWORD;
	public Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	{
		URL = "jdbc:mysql://192.168.56.106:3306/JAM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
		USER = "root";
		PASSWORD = "123456a";
		pstmt = null;
		rs = null;
	}

	public void articleWrite(String title, String body) {
		try {
			String sql = "INSERT INTO article";
			sql += " SET regDate = NOW()";
			sql += ", updateDate = NOW()";
			sql += ", title = '" + title + "'";
			sql += ", `body` = '" + body + "';";

			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}

	public List<Article> articleList() {
		try {
			String sql = "SELECT * FROM article";
			sql += " ORDER BY id DESC";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			List<Article> articles = new ArrayList<>();

			while (rs.next()) {
				int id = rs.getInt("id");
				String regDate = rs.getString("regDate");
				String updateDate = rs.getString("updateDate");
				String title = rs.getString("title");
				String body = rs.getString("body");

				Article article = new Article(id, regDate, updateDate, title, body);
				articles.add(article);
			}

			return articles;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void articleModify(String articleNumber, String title, String body) {
		try {
			String sql = "UPDATE article";
			sql += " Set updateDATE = NOW()";
			sql += ", title = '" + title + "'";
			sql += ", `body` = '" + body + "'";
			sql += "where id = " + articleNumber + ";";

			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean articleCheck(String articleNumber) {
		try {
			String sql = "SELECT * FROM article ";
			sql += "where id = " + articleNumber + ";";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			List<Article> articles = new ArrayList<>();

			while (rs.next()) {
				int id = rs.getInt("id");
				String regDate = rs.getString("regDate");
				String updateDate = rs.getString("updateDate");
				String title = rs.getString("title");
				String body = rs.getString("body");

				Article article = new Article(id, regDate, updateDate, title, body);
				articles.add(article);
			}

			if (articles.size() != 0)
				return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public void jdbcClose() {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public String getURL() {
		return URL;
	}

	public String getUSER() {
		return USER;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}
}