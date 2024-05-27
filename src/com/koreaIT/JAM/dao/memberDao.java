package com.koreaIT.JAM.dao;

import java.sql.Connection;
import java.util.Map;

import com.koreaIT.JAM.util.DBUtil;
import com.koreaIT.JAM.util.SecSql;

public class MemberDao {
	private Connection conn;

	public MemberDao(Connection conn) {
		this.conn = conn;
	}

	public void memberJoin(String memberId, String memberPassword, String name) {
		SecSql sql = new SecSql();
		sql.append("INSERT INTO `member`");
		sql.append("SET memberId = ?", memberId);
		sql.append(", memberPassword = ?", memberPassword);
		sql.append(", name = ?", name);
		sql.append(", regDate = NOW()");
		sql.append(", updateDate = NOW()");

		DBUtil.insert(conn, sql);
	}

	public Map<String, Object> memberLogin(String loginId, String loginPassword) {
		SecSql sql = new SecSql();
		sql.append("SELECT * FROM `member`");
		sql.append("WHERE memberId = ?", loginId);
		sql.append("AND memberPassword = ?", loginPassword);

		return DBUtil.selectRow(conn, sql);
	}

	public boolean memberIdDupCheck(String memberId) {
		SecSql sql = new SecSql();
		sql = new SecSql();
		sql.append("SELECT count(*) > 0");
		sql.append("FROM `member`");
		sql.append("WHERE memberId = ?", memberId);

		return DBUtil.selectRowBooleanValue(conn, sql);
	}

	public boolean memberLoginCheck(String loginId, String loginPassword) {
		SecSql sql = new SecSql();
		sql.append("SELECT count(*) > 0 FROM `member`");
		sql.append("WHERE memberId = ?", loginId);
		sql.append("AND memberPassword = ?", loginPassword);

		return DBUtil.selectRowBooleanValue(conn, sql);
	}

	public Map<String, Object> LoginMemberId(int loginMemberNumber) {
		SecSql sql = new SecSql();
		sql.append("SELECT * FROM `member`");
		sql.append("WHERE memberNumber = ?", loginMemberNumber);

		return DBUtil.selectRow(conn, sql);
	}

}