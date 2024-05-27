package com.koreaIT.JAM.controller;

import java.sql.Connection;
import java.util.Scanner;

import com.koreaIT.JAM.dto.Member;

public class Controller {
	private static boolean loginCheck;
	private static Member loginMember;
	public Connection conn;
	public Scanner sc;
	
	static {
		loginCheck = false;
		loginMember = null;
	}

	public void cmdCheck(String cmd) {
	}
	
	public static boolean isLoginCheck() {
		return loginCheck;
	}

	public static void setLoginCheck(boolean loginCheck) {
		Controller.loginCheck = loginCheck;
	}

	public static Member getLoginMember() {
		return loginMember;
	}

	public static void setLoginMember(Member loginMember) {
		Controller.loginMember = loginMember;
	}
	
}