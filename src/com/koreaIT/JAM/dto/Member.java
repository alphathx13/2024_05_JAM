package com.koreaIT.JAM.dto;

import java.time.LocalDateTime;

import com.koreaIT.JAM.util.Util;

public class Member {
	static int lastMemberNumber;
	int memberNumber;
	String memberId;
	String memberPass;
	String memberName;
	LocalDateTime regDate;
	int temp;
	
	public Member(int lastMemberNumber, String memberId, String memberPass, String memberName) {
		this.memberNumber = lastMemberNumber;
		this.memberId = memberId;
		this.memberPass = memberPass;
		this.memberName = memberName;
		this.regDate = Util.now();
	}
}
