package com.koreaIT.JAM.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.koreaIT.JAM.dao.MemberDao;
import com.koreaIT.JAM.dto.Member;

public class MemberService {
	MemberDao memberDao;

	public MemberService(Connection conn) {
		this.memberDao = new MemberDao(conn);
	}

	public void memberJoin(String memberId, String memberPassword, String name) {
		memberDao.memberJoin(memberId, memberPassword, name);
	}
	
	public Member memberLogin(String loginId, String loginPassword) {
		Map<String, Object> loginMember = memberDao.memberLogin(loginId, loginPassword);
		
		if (loginMember.size() != 0) {
			return new Member(loginMember);
		}
		
		return null;
	}
	
	public boolean memberIdDupCheck(String memberId) {
		return memberDao.memberIdDupCheck(memberId);
	}

	public boolean memberLoginCheck(String loginId, String loginPassword) {
		return memberDao.memberLoginCheck(loginId, loginPassword);
	}

}