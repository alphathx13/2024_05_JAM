package com.koreaIT.JAM.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.koreaIT.JAM.util.DBUtil;
import com.koreaIT.JAM.util.SecSql;

public class ArticleDao {

	private Connection conn;

	public ArticleDao(Connection conn) {
		this.conn = conn;
	}

	public int articleWrite(String title, String body, int memberId) {
		SecSql sql = new SecSql();
		sql.append("INSERT INTO article");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", title = ?", title);
		sql.append(", `body` = ?", body);
		sql.append(", writer = ?", memberId);

		return DBUtil.insert(conn, sql);
	}

	public List<Map<String, Object>> articleList() {
		SecSql sql = new SecSql();
		sql.append("SELECT a.*, b.memberId AS 'writerName'");
		sql.append("FROM article a");
		sql.append("INNER JOIN `member` b");
		sql.append("ON a.writer = b.memberNumber");
		sql.append("ORDER BY id DESC");

		return DBUtil.selectRows(conn, sql);
	}

	public Map<String, Object> articleDetail(String search) {

		SecSql sql = new SecSql();
		sql.append("SELECT a.*, b.memberId AS `writerName`");
		sql.append("FROM article a");
		sql.append("INNER JOIN `member` b");
		sql.append("ON a.writer = b.memberNumber");
		sql.append("WHERE a.id = ?", search);

		return DBUtil.selectRow(conn, sql);
	}

	public void articleModify(String articleNumber, String title, String body) {
		SecSql sql = new SecSql();
		sql.append("UPDATE article");
		sql.append("Set updateDATE = NOW()");
		sql.append(", title = ?", title);
		sql.append(", `body` = ?", body);
		sql.append("where id = ?", articleNumber);

		DBUtil.update(conn, sql);
	}

	public void articleDelete(String articleNumber) {
		SecSql sql = new SecSql();
		sql = new SecSql();
		sql.append("DELETE FROM article");
		sql.append("where id = ?", articleNumber);

		DBUtil.delete(conn, sql);
	}

	public Map<String, Object> articleCheck(String articleNumber) {
		SecSql sql = new SecSql();
		sql.append("SELECT * FROM article");
		sql.append("WHERE id = ?", articleNumber);

		return DBUtil.selectRow(conn, sql);
	}

	public int increaseViewCount(String cmd) {
		SecSql sql = new SecSql();
		sql.append("UPDATE article");
		sql.append("SET viewCount = viewCount + 1");
		sql.append("WHERE id = ?", cmd);

		return DBUtil.update(conn, sql);
	}

}