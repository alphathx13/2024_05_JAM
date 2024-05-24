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
import com.koreaIT.JAM.service.articleService;
import com.koreaIT.JAM.service.memberService;
import com.koreaIT.JAM.util.DBUtil;
import com.koreaIT.JAM.util.SecSql;

public class App {
	private final String URL;
	private final String USER;
	private final String PASSWORD;
	private boolean loginCheck;
	private Member loginMember;
	public Connection conn;

	{
		URL = "jdbc:mysql://192.168.56.106:3306/JAM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
		USER = "root";
		PASSWORD = "123456a";
		loginCheck = false;
		loginMember = null;
		conn = null;
	}

	public void run() {
		System.out.println("=== 프로그램 시작 ===");

		String cmd;
		Scanner sc = new Scanner(System.in);

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);

			while (true) {
				if (loginCheck == true)
					System.out.print(loginMember.getMemberId() + " - 명령어) ");
				else
					System.out.print("명령어) ");

				cmd = sc.nextLine().trim();

				if (cmd.equals("exit")) {
					break;

				} else if (cmd.equals("member join")) {
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
						

						if (memberService.memberIdDupCheck(memberId, conn) == true) {
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

					memberService.memberJoin(memberId, memberPassword, name, conn);
					
					System.out.printf(memberId + "님의 회원가입을 축하드립니다.\n");

				} else if (cmd.equals("member login")) {
					if (loginCheck == true) {
						System.out.println("먼저 로그아웃 해야합니다.");
						continue;
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
					
					if (memberService.memberLoginCheck(loginId, loginPassword, conn) == false) {
						System.out.println("계정정보가 일치하지 않습니다.");
						continue;
					}

					loginMember = new Member(memberService.memberLogin(loginId, loginPassword, conn));
					loginCheck = true;

					System.out.println(loginMember.getMemberId() + "님의 로그인을 환영합니다.");

				} else if (cmd.equals("member logout")) {
					if (loginCheck == false) {
						System.out.println("먼저 로그인 해야합니다.");
						continue;
					}

					loginCheck = false;
					loginMember = null;
					System.out.println("정상적으로 로그아웃 되었습니다.");

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
						System.out.printf("%d \t\t %s \t\t %s\n", member.getMemberNumber(), member.getMemberId(),
								member.getName());

				} else if (cmd.equals("article write")) {
					System.out.print("제목) ");
					String title = sc.nextLine();
					System.out.print("내용) ");
					String body = sc.nextLine();

					System.out.printf("%d번 게시물이 작성되었습니다\n", articleService.articleWrite(title, body, conn));

				} else if (cmd.equals("article list")) {

					List<Map<String, Object>> articleList = articleService.articleList(conn);

					if (articleList.size() == 0) {
						System.out.println("게시글이 존재하지 않습니다.");
						continue;
					}

					System.out.println("글번호 \t 글 제목");

					List<Article> foundArticle = new ArrayList<>();

					for (Map<String, Object> article : articleList)
						foundArticle.add(new Article(article));

					for (Article article : foundArticle)
						System.out.printf("%s \t %s\n", article.getArticleId(), article.getTitle());

				} else if (cmd.startsWith("article detail ")) {
					String search = cmd.substring(15);

					Map<String, Object> article = articleService.detail(search, conn);

					if (article.size() == 0) {
						System.out.println("해당 글은 존재하지 않습니다.");
						continue;
					}

					Article foundArticle = new Article(article);

					System.out.printf("번호 : %d\n", foundArticle.getArticleId());
					System.out.printf("작성일 : %s\n", foundArticle.getRegDate());
					System.out.printf("수정일 : %s\n", foundArticle.getUpdateDate());
					System.out.printf("제목 : %s\n", foundArticle.getTitle());
					System.out.printf("내용 : %s\n", foundArticle.getBody());

				} else if (cmd.startsWith("article modify ")) {
					String articleNumber = cmd.substring(15);

					if (articleService.articleCheck(articleNumber, conn) == false) {
						System.out.println("해당 글은 존재하지 않습니다.");
						continue;
					}

					System.out.print("제목) ");
					String title = sc.nextLine();
					System.out.print("내용) ");
					String body = sc.nextLine();

					articleService.articleModify(articleNumber, title, body, conn);

					System.out.println(articleNumber + "번 글을 수정하였습니다.");

				} else if (cmd.startsWith("article delete ")) {
					String articleNumber = cmd.substring(15);

					if (articleService.articleCheck(articleNumber, conn) == false) {
						System.out.println("해당 글은 존재하지 않습니다.");
						continue;
					}

					articleService.articleDelete(articleNumber, conn);

					System.out.println(articleNumber + "번 글을 삭제하였습니다.");

				} else {
					System.out.println("명령어를 다시 입력해주세요.");
				}
			}

		} catch (

		SQLException e) {
			e.printStackTrace();
		} finally {

			System.out.println("=== 프로그램 종료 ===");

			sc.close();

		}
	}
}