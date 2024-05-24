package com.koreaIT.JAM.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.koreaIT.JAM.util.DBUtil;
import com.koreaIT.JAM.util.SecSql;

public class articleDao {

	public static int articleWrite(String title, String body, Connection conn) {
		SecSql sql = new SecSql();
		sql.append("INSERT INTO article");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", title = ?", title);
		sql.append(", `body` = ?", body);
		
		return DBUtil.insert(conn, sql);
		
	}

	public static List<Map<String, Object>> articleList(Connection conn) {
		SecSql sql = new SecSql();
		sql.append("SELECT * FROM article");
		sql.append("ORDER BY id DESC");
		
		return DBUtil.selectRows(conn, sql);
	}

	public static Map<String, Object> articleDetail(String search, Connection conn) {
		SecSql sql = new SecSql();
		sql.append("SELECT * FROM article");
		sql.append("WHERE id = ?", search);
		
		return DBUtil.selectRow(conn, sql);	
	}

	public static boolean articleCheck(String articleNumber, Connection conn) {
		SecSql sql = new SecSql();
		sql.append("SELECT count(*) > 0 FROM article");
		sql.append("WHERE id = ?", articleNumber);

		return DBUtil.selectRowBooleanValue(conn, sql);	
	}

	public static void articleModify(String articleNumber, String title, String body, Connection conn) {
		SecSql sql = new SecSql();
		sql.append("UPDATE article");
		sql.append("Set updateDATE = NOW()");
		sql.append(", title = ?", title);
		sql.append(", `body` = ?", body);
		sql.append("where id = ?", articleNumber);
		
		DBUtil.update(conn, sql);
	}

	public static void articleDelete(String articleNumber, Connection conn) {
		SecSql sql = new SecSql();
		sql = new SecSql();
		sql.append("DELETE FROM article");
		sql.append("where id = ?", articleNumber);

		DBUtil.delete(conn, sql);
	}
}