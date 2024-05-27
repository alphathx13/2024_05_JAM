package com.koreaIT.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import com.koreaIT.JAM.controller.ArticleController;
import com.koreaIT.JAM.controller.Controller;
import com.koreaIT.JAM.controller.MemberController;

public class App {
	private final String URL;
	private final String USER;
	private final String PASSWORD;
	public Connection conn;

	{
		URL = "jdbc:mysql://192.168.56.106:3306/JAM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
		USER = "root";
		PASSWORD = "123456a";
		conn = null;
	}

	public void run() {
		System.out.println("=== 프로그램 시작 ===");

		String cmd;
		Scanner sc = new Scanner(System.in);

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);

			Controller controller = null;
			ArticleController articleController = new ArticleController(conn, sc);
			MemberController memberController = new MemberController(conn, sc);

			while (true) {
				if (Controller.isLoginCheck() == true)
					System.out.print(Controller.getLoginMember().getMemberId() + " - 명령어) ");
				else
					System.out.print("명령어) ");

				cmd = sc.nextLine().trim();
				String[] cmds = cmd.split(" ");

				if (cmd.equals("exit"))
					break;

				switch (cmds[0]) {

				case "article":
					controller = articleController;
					break;

				case "member":
					controller = memberController;
					break;

				default:
					System.out.println("명령어를 다시 입력해주세요.");
				}

				controller.cmdCheck(cmd);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		sc.close();

		System.out.println("== 프로그램 끝 ==");

	}
}
