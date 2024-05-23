package com.koreaIT.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.koreaIT.JAM.dto.Article;
import com.koreaIT.JAM.dto.Member;
import com.koreaIT.JAM.util.DBUtil;
import com.koreaIT.JAM.util.SecSql;

public class App {
	private final String URL;
	private final String USER;
	private final String PASSWORD;
	public Connection conn;

	{
		URL = "jdbc:mysql://192.168.56.106:3306/JAM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
		USER = "root";
		PASSWORD = "123456a";
	}

	public void run() {
		System.out.println("=== 프로그램 시작 ===");

		String cmd;
		Scanner sc = new Scanner(System.in);

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);

			while (true) {
				System.out.print("명령어) ");
				cmd = sc.nextLine().trim();

				if (cmd.equals("exit")) {
					break;

				} else if (cmd.equals("article write")) {
					System.out.print("제목) ");
					String title = sc.nextLine();
					System.out.print("내용) ");
					String body = sc.nextLine();

					SecSql sql = new SecSql();
					sql.append("INSERT INTO article");
					sql.append("SET regDate = NOW()");
					sql.append(", updateDate = NOW()");
					sql.append(", title = ?", title);
					sql.append(", `body` = ?", body);

					System.out.printf("%d번 게시물이 작성되었습니다\n", DBUtil.insert(conn, sql));

				} else if (cmd.equals("article list")) {

					SecSql sql = new SecSql();
					sql.append("SELECT * FROM article");
					sql.append("ORDER BY id DESC");

					List<Map<String, Object>> articleList = DBUtil.selectRows(conn, sql);

					if (articleList.size() == 0) {
						System.out.println("게시글이 존재하지 않습니다.");
						continue;
					}
					
					System.out.println("글번호 \t 글 제목");

//					List화 안하고 바로 출력
//					for (int i = 0; i < foundArticle.size(); i++) 
//						System.out.printf("%s \t %s\n", foundArticle.get(i).get("id"), foundArticle.get(i).get("title"));					                                   
//					for (Map<String, Object> article : foundArticle) 
//						System.out.printf("%s \t %s\n", article.get("id"), article.get("title"));

					List<Article> foundArticle = new ArrayList<>();

					for (Map<String, Object> article : articleList)
						foundArticle.add(new Article(article));

					for (Article article : foundArticle)
						System.out.printf("%s \t %s\n", article.getArticleId(), article.getTitle());

				} else if (cmd.startsWith("article detail ")) {
					String search = cmd.substring(15);

					SecSql sql = new SecSql();
					sql.append("SELECT count(*) > 0 FROM article");
					sql.append("WHERE id = ?", search);

					if (DBUtil.selectRowBooleanValue(conn, sql) == false) {
						System.out.println("해당 글은 존재하지 않습니다.");
						continue;
					}
					
					SecSql sql2 = new SecSql();
					sql2.append("SELECT * FROM article");
					sql2.append("WHERE id = ?", search);

					List<Map<String, Object>> articleList = DBUtil.selectRows(conn, sql2);

					List<Article> foundArticle = new ArrayList<>();

					for (Map<String, Object> article : articleList)
						foundArticle.add(new Article(article));

					for (Article article : foundArticle)
						System.out.printf("%s \t %s\n", article.getArticleId(), article.getTitle());

				} else if (cmd.startsWith("article modify ")) {
					String articleNumber = cmd.substring(15);

					SecSql sql = new SecSql();
					sql.append("SELECT count(*) > 0 FROM article");
					sql.append("WHERE id = ?", articleNumber);

					if (DBUtil.selectRowBooleanValue(conn, sql) == false) {
						System.out.println("해당 글은 존재하지 않습니다.");
						continue;
					}

					System.out.print("제목) ");
					String title = sc.nextLine();
					System.out.print("내용) ");
					String body = sc.nextLine();

					SecSql sql2 = new SecSql();
					sql2.append("UPDATE article");
					sql2.append("Set updateDATE = NOW()");
					sql2.append(", title = ?", title);
					sql2.append(", `body` = ?", body);
					sql2.append("where id = ?", articleNumber);

					DBUtil.update(conn, sql2);
					System.out.println(articleNumber + "번 글을 수정하였습니다.");

				} else if (cmd.startsWith("article delete ")) {
					String articleNumber = cmd.substring(15);

					SecSql sql = new SecSql();
					sql.append("SELECT count(*) > 0 FROM article");
					sql.append("WHERE id = ?", articleNumber);

					if (DBUtil.selectRowBooleanValue(conn, sql) == false) {
						System.out.println("해당 글은 존재하지 않습니다.");
						continue;
					}

					SecSql sql2 = new SecSql();
					sql2.append("DELETE FROM article");
					sql2.append("where id = ?", articleNumber);

					DBUtil.delete(conn, sql2);
					System.out.println(articleNumber + "번 글을 삭제하였습니다.");

				} else if (cmd.equals("member join")) {

					System.out.print("사용할 아이디) ");
					String memberId = sc.nextLine();
					System.out.print("사용할 비밀번호) ");
					String memberPassword = sc.nextLine();
					System.out.print("사용자 이름) ");
					String name = sc.nextLine();

					SecSql sql = new SecSql();
					sql.append("SELECT count(*) > 0 FROM `member`");
					sql.append("WHERE memberId = ?", memberId);

					if (DBUtil.selectRowBooleanValue(conn, sql) == true) {
						System.out.println("해당 아이디는 이미 사용중입니다.");
						continue;
					}		
					
					SecSql sql2 = new SecSql();
					sql2.append("INSERT INTO `member`");
					sql2.append("SET memberId = ?", memberId);
					sql2.append(", memberPassword = ?", memberPassword);
					sql2.append(", name = ?", name);
					sql2.append(", regDate = NOW()");

					DBUtil.insert(conn, sql2);
					System.out.println("회원가입 되셨습니다.");

				} else if (cmd.equals("member list")) {

					SecSql sql = new SecSql();
					sql.append("SELECT * FROM `member`");

					List<Map<String, Object>> memberList = DBUtil.selectRows(conn, sql);

					if (memberList.size() == 0) {
						System.out.println("회원이 존재하지 않습니다.");
						continue;
					}
					
					System.out.println("회원번호 \t 회원 아이디 \t 회원 이름");

					List<Member> foundMember = new ArrayList<>();

					for (Map<String, Object> member : memberList)
						foundMember.add(new Member(member));

					for (Member member : foundMember)
						System.out.printf("%d \t\t %s \t\t %s\n", member.getMemberNumber() , member.getMemberId(), member.getName());

				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			System.out.println("=== 프로그램 종료 ===");

			sc.close();

		}
	}
}