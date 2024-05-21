package com.koreaIT.JAM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.koreaIT.JAM.dto.Article;
import com.koreaIT.JAM.dto.Member;
import com.koreaIT.JAM.util.JDBC;

public class Main {
	public static int lastArticleId;
	public static int lastMemberId;
	
	static {
		lastArticleId = 1;
		lastMemberId = 1;
	}
	
	public static void main(String[] args) {
		
		String cmd;
		Scanner sc = new Scanner(System.in);
		
		List <Article> articles = new ArrayList<>();
		List <Member> members = new ArrayList<>();
		
		System.out.println("=== 프로그램 시작 ===");
		
		while (true) {
			System.out.print("명령어) ");
			cmd = sc.nextLine().trim();
			
			if (cmd.equals("article write")) {
				System.out.print("제목) ");
				String title = sc.nextLine();
				System.out.print("내용) ");
				String body = sc.nextLine();
				
				JDBC.articleWrite(title, body);
				
//				JDBC jdbc = new JDBC;
//				jdbc.articleWrite(title, body);
				
				System.out.println(lastArticleId + "번 게시물이 작성되었습니다.");
				articles.add(new Article(lastArticleId++, title, body));
			}
			
			else if (cmd.equals("article list")) {			
				if (articles.size() == 0) {
					System.out.println("게시글이 존재하지 않습니다.");
					continue;
				}
				
				Article foundArticle = null;
				
				System.out.println("번호 \t 제목");
				for (int i = articles.size()-1; i >= 0; i--) {
					foundArticle = articles.get(i);
					System.out.printf("%d \t %s\n", foundArticle.getArticleId(), foundArticle.getTitle());
				}
			}
			
			if (cmd.equals("exit"))
				break;
		}
		
		System.out.println("=== 프로그램 종료 ===");
		sc.close();
	}
}