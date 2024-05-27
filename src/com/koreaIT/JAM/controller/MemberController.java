package com.koreaIT.JAM.controller;

import java.sql.Connection;
import java.util.Scanner;

import com.koreaIT.JAM.dto.Member;
import com.koreaIT.JAM.service.MemberService;

public class MemberController extends Controller {
	
	private MemberService memberService;
	private Scanner sc;

	public MemberController(Connection conn, Scanner sc) {
		memberService = new MemberService(conn);
		this.sc = sc;
	}

	public void cmdCheck(String cmd) {
		String[] cmds = cmd.split(" ");

		switch (cmds[1]) {

		case "join":
			this.memberJoin();
			break;

		case "login":
			this.memberLogin();
			break;

		case "logout":
			this.memberLogout();
			break;

		}
	}

	private void memberJoin() {
		String memberId;
		String memberPassword;
		String name;

		while (true) {
			System.out.print("사용할 아이디) ");
			memberId = sc.nextLine().trim();

			if (memberId.length() == 0) {
				System.out.println("아이디는 필수적으로 입력해야합니다.");
				continue;
			}

			if (memberId.contains(" ")) {
				System.out.println("중간에 공백이 들어갈 수 없습니다.");
				continue;
			}

			if (memberService.memberIdDupCheck(memberId) == true) {
				System.out.println("해당 아이디는 이미 사용중입니다.");
				continue;
			}

			System.out.printf("[ %s ] 아이디는 사용 가능합니다.\n", memberId);

			break;
		}

		while (true) {
			System.out.print("사용할 비밀번호) ");
			memberPassword = sc.nextLine().trim();

			if (memberPassword.length() == 0) {
				System.out.println("비밀번호는 필수적으로 입력해야합니다.");
				continue;
			}

			if (memberPassword.contains(" ")) {
				System.out.println("중간에 공백이 들어갈 수 없습니다.");
				continue;
			}

			System.out.print("비밀번호를 다시입력) ");
			String passCheck = sc.nextLine().trim();

			if (!memberPassword.equals(passCheck)) {
				System.out.println("이전에 입력한 암호와 다릅니다.");
				continue;
			}

			break;
		}

		while (true) {
			System.out.print("사용자 이름) ");
			name = sc.nextLine().trim();

			if (name.length() == 0) {
				System.out.println("이름은 필수적으로 입력해야합니다.");
				continue;
			}

			if (name.contains(" ")) {
				System.out.println("중간에 공백이 들어갈 수 없습니다.");
				continue;
			}

			break;
		}

		memberService.memberJoin(memberId, memberPassword, name);

		System.out.printf(memberId + "님의 회원가입을 축하드립니다.\n");
	}

	private void memberLogin() {
		if (Controller.isLoginCheck() == true) {
			System.out.println("먼저 로그아웃 해야합니다.");
			return;
		}

		String loginId = null;
		String loginPassword = null;

		while (true) {
			System.out.print("아이디) ");
			loginId = sc.nextLine().trim();

			if (loginId.length() == 0 || loginId.contains(" ")) {
				System.out.println("다시 입력해주세요.");
				continue;
			}

			break;
		}

		while (true) {
			System.out.print("비밀번호) ");
			loginPassword = sc.nextLine().trim();

			if (loginPassword.length() == 0 || loginPassword.contains(" ")) {
				System.out.println("다시 입력해주세요.");
				continue;
			}

			break;
		}

		Member loginMember = memberService.memberLogin(loginId, loginPassword);
		
		if (loginMember == null) {
			System.out.println("계정정보가 일치하지 않습니다.");
			return;
		}
		
		Controller.setLoginMember(loginMember);
		Controller.setLoginCheck(true);

		System.out.println(Controller.getLoginMember().getMemberId() + "님의 로그인을 환영합니다.");
	}

	private void memberLogout() {
		if (Controller.isLoginCheck() == false) {
			System.out.println("먼저 로그인 해야합니다.");
			return;
		}

		Controller.setLoginCheck(false);
		Controller.setLoginMember(null);
		System.out.println("정상적으로 로그아웃 되었습니다.");
	}

}