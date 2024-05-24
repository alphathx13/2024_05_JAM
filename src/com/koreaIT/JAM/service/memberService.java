package com.koreaIT.JAM.service;

import java.sql.Connection;
import java.util.Map;

import com.koreaIT.JAM.dao.memberDao;

public class memberService {

	public static void memberJoin(String memberId, String memberPassword, String name, Connection conn) {
		memberDao.memberJoin(memberId, memberPassword, name, conn);
	}
	
	public static Map<String, Object> memberLogin(String loginId, String loginPassword, Connection conn) {
		return memberDao.memberLogin(loginId, loginPassword, conn);
	}
	
	public static boolean memberIdDupCheck(String memberId, Connection conn) {
		return memberDao.memberIdDupCheck(memberId, conn);
	}

	public static boolean memberLoginCheck(String loginId, String loginPassword, Connection conn) {
		return memberDao.memberLoginCheck(loginId, loginPassword, conn);
	}


}