package com.koreaIT.JAM.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class Member {
	int memberNumber;
	String memberId;
	String memberPassword;
	String name;
	LocalDateTime regDate;
	
	public int getMemberNumber() {
		return memberNumber;
	}

	public String getMemberId() {
		return memberId;
	}

	public String getName() {
		return name;
	}

	public Member(Map<String, Object> foundMember) {
		this.memberNumber = (int) foundMember.get("memberNumber");
		this.memberId = (String) foundMember.get("memberId");
		this.memberPassword = (String) foundMember.get("memberPassword");
		this.name = (String) foundMember.get("name");
		this.regDate = (LocalDateTime) foundMember.get("regDate");
	}
}
