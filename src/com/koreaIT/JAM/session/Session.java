package com.koreaIT.JAM.session;

public class Session {
	private static int loginMemberNumber;

	static {
		loginMemberNumber = -1;
	}

	public static void login(int id) {
		loginMemberNumber = id;
	}

	public static void logout() {
		loginMemberNumber = -1;
	}

	public static boolean isLogin() {
		if (loginMemberNumber == -1)
			return false;
		return true;
	}

	public static int getLoginMemberNumber() {
		return loginMemberNumber;
	}
}