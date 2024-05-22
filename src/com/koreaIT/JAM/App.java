package com.koreaIT.JAM;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.koreaIT.JAM.dto.Article;
import com.koreaIT.JAM.util.JDBC;

public class App {
	public static int lastArticleId;
	public static int lastMemberId;

	static {
		lastArticleId = 1;
		lastMemberId = 1;
	}

	public void run() {
		System.out.println("=== 프로그램 시작 ===");

		String cmd;
		Scanner sc = new Scanner(System.in);

		JDBC jdbc = new JDBC();

		try {
			jdbc.conn = DriverManager.getConnection(jdbc.getURL(), jdbc.getUSER(), jdbc.getPASSWORD());
			
			while (true) {
				System.out.print("명령어) ");
				cmd = sc.nextLine().trim();

				if (cmd.equals("article write")) {
					System.out.print("제목) ");
					String title = sc.nextLine();
					System.out.print("내용) ");
					String body = sc.nextLine();

					jdbc.articleWrite(title, body);

					System.out.println("게시물이 작성되었습니다.");
				}

				else if (cmd.equals("article list")) {
					List<Article> articles = jdbc.articleList();

					if (articles.size() == 0) {
						System.out.println("게시글이 존재하지 않습니다.");
						continue;
					}

					System.out.println("번호 \t 제목");
					for (Article article : articles) {
						System.out.printf("%d \t %s\n", article.getArticleId(), article.getTitle());
					}
				}

				else if (cmd.startsWith("article modify ")) {
					String articleNumber = cmd.substring(15);

					if (jdbc.articleCheck(articleNumber) == false) {
						System.out.println("해당 글은 존재하지 않습니다.");
						continue;
					}

					System.out.print("제목) ");
					String title = sc.nextLine();
					System.out.print("내용) ");
					String body = sc.nextLine();

					jdbc.articleModify(articleNumber, title, body);
					System.out.println(articleNumber + "번 글을 수정하였습니다.");
				}

				if (cmd.equals("exit"))
					break;

			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		jdbc.jdbcClose();

		System.out.println("=== 프로그램 종료 ===");

		sc.close();
		
		}
	}
}