package com.koreaIT.JAM.controller;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import com.koreaIT.JAM.dto.Article;
import com.koreaIT.JAM.service.ArticleService;
import com.koreaIT.JAM.session.Session;

public class ArticleController extends Controller {

	private ArticleService articleService;
	private Scanner sc;

	public ArticleController(Connection conn, Scanner sc) {
		this.articleService = new ArticleService(conn);
		this.sc = sc;
	}

	public void cmdCheck(String cmd) {
		String[] cmds = cmd.split(" ");

		switch (cmds[1]) {

		case "write":
			this.articleWrite();
			break;

		case "list":
			this.articleList();
			break;

		case "detail":
			this.articleDetail(cmds[2]);
			break;

		case "modify":
			this.articleModify(cmds[2]);
			break;

		case "delete":
			this.articleDelete(cmds[2]);
			break;
		}
	}

	private void articleWrite() {
		System.out.print("제목) ");
		String title = sc.nextLine();
		System.out.print("내용) ");
		String body = sc.nextLine();

		
		System.out.printf("%d번 게시물이 작성되었습니다\n",	articleService.articleWrite(title, body, Session.getLoginMemberNumber()));

	}

	private void articleList() {
		List<Article> articleList = articleService.articleList();

		if (articleList.size() == 0) {
			System.out.println("게시글이 존재하지 않습니다.");
			return;
		}

		System.out.println("글번호 \t 글 제목 \t 작성자");

		for (Article article : articleList)
			System.out.printf("%s \t %s \t %s\n", article.getArticleId(), article.getTitle(), article.getWriter());
	}

	private void articleDetail(String cmd) {
		Article foundArticle = articleService.articleDetail(cmd);

		if (foundArticle == null) {
			System.out.println("해당 글은 존재하지 않습니다.");
			return;
		}

		System.out.printf("번호 : %d\n", foundArticle.getArticleId());
		System.out.printf("작성일 : %s\n", foundArticle.getRegDate());
		System.out.printf("수정일 : %s\n", foundArticle.getUpdateDate());
		System.out.printf("제목 : %s\n", foundArticle.getTitle());
		System.out.printf("내용 : %s\n", foundArticle.getBody());
		System.out.printf("작성자 : %s\n", foundArticle.getWriter());
	}

	private void articleModify(String articleNumber) {

		boolean[] check = articleService.articleCheck(articleNumber, Session.getLoginMemberNumber());
		
		if (check[0] == false) {
			System.out.println("해당 글은 존재하지 않습니다.");
			return;
		} else if (check[1] == false) {
			System.out.println("글 수정은 작성자만 할 수 있습니다.");
			return;
		}
		
		System.out.print("제목) ");
		String title = sc.nextLine();
		System.out.print("내용) ");
		String body = sc.nextLine();

		articleService.articleModify(articleNumber, title, body);

		System.out.println(articleNumber + "번 글을 수정하였습니다.");

	}

	private void articleDelete(String articleNumber) {

		boolean[] check = articleService.articleCheck(articleNumber, Session.getLoginMemberNumber());

		if (check[0] == false) {
			System.out.println("해당 글은 존재하지 않습니다.");
			return;
		} else if (check[1] == false) {
			System.out.println("글 삭제는 작성자만 할 수 있습니다.");
			return;
		}

		articleService.articleDelete(articleNumber);

		System.out.println(articleNumber + "번 글을 삭제하였습니다.");

	}

}