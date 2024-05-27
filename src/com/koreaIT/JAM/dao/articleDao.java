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
		sql.append("SELECT a.id, a.regDate, a.updateDate, title, `body`, b.memberId");
		sql.append("FROM article a");
		sql.append("INNER JOIN `member` b");
		sql.append("ON a.writer = b.memberNumber");
		sql.append("ORDER BY id DESC");
		
		return DBUtil.selectRows(conn, sql);
	}

	public Map<String, Object> articleDetail(String search) {
		SecSql sql = new SecSql();
		sql.append("SELECT a.id, a.regDate, a.updateDate, title, `body`, b.memberId");
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

	public boolean[] articleCheck(String articleNumber, int loginMemberId) {
		boolean[] check = new boolean[2];
		
		SecSql sql = new SecSql();
		sql.append("SELECT count(*) > 0 FROM article");
		sql.append("WHERE id = ?", articleNumber);
		check[0] = DBUtil.selectRowBooleanValue(conn, sql);

		sql = new SecSql();
		sql.append("SELECT a.id, b.memberId");
		sql.append("FROM article a");
		sql.append("INNER JOIN `member` b");
		sql.append("ON a.writer = b.memberNumber");
		sql.append("WHERE a.id = ?", articleNumber);
		sql.append("AND b.memberId = ?", loginMemberId);
		
		check[1] = DBUtil.selectRowBooleanValue(conn, sql);
		
		return check;
	}

}