package com.example.app.login;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AdminAuthFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)  
	                                                               // 次の処理（コントローラーや他のフィルター）を実行するためのオブジェクト
	                                                               // リクエスト情報 を受け取る（HttpServletRequest にキャストする）
                                                                   //  レスポンス情報 を受け取る（HttpServletResponse にキャストする
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;     // ServletRequest を HttpServletRequest にキャスト して HTTP リクエストを扱えるようにする。
		HttpServletResponse res = (HttpServletResponse) response;  // ServletResponse を HttpServletResponse にキャスト してリダイレクトなどの処理を行えるようにする。 レスポンス情報 を受け取る（HttpServletResponse にキャストする）
		HttpSession session = req.getSession();                    // HttpSession を取得し、ログイン情報を管理する。
 
		if(session.getAttribute("loginStatus") == null) {          // session.getAttribute("loginStatus") で ログイン情報があるか確認
			res.sendRedirect("/admin/login");                      // null の場合、ログインしていないので /admin/login にリダイレクト
			return;                                                // return; で処理を終了し、それ以上の処理を実行させない
		}

		LoginStatus status = (LoginStatus) session.getAttribute("loginStatus");  // loginStatus を LoginStatus 型にキャスト
		if(status.getAuthority() != LoginAuthority.ADMIN) {                     // 管理者 (LoginAuthority.ADMIN) 以外のユーザー なら、/admin/login にリダイレクト
			res.sendRedirect("/admin/login");
			return;
		}

		chain.doFilter(request, response);    // ここまでのチェックをクリアした場合、次の処理（コントローラーや他のフィルター）にリクエストを渡す                    
	}

}
