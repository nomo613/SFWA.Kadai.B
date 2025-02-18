package com.example.app.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.Login;
import com.example.app.domain.Student;
import com.example.app.login.LoginAuthority;
import com.example.app.login.LoginStatus;
import com.example.app.service.StudentService;
import com.example.app.validation.LoginGroup;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StudentLoginController {

	private final StudentService studentService;
	private final HttpSession session;

	@GetMapping("/login")                 
	public String login(Model model) {
		model.addAttribute(new Login());
		return "login-student";
	}

	@PostMapping("/login")
	public String login(         // LoginGroup という バリデーション・グループ を指定して、フォームの入力チェックを行う
			@Validated(LoginGroup.class) Login login,
			Errors errors) throws Exception {
		if(errors.hasErrors()) {
			return "login-student";          //  バリデーションチェック 入力エラーがある場合は ログイン画面を再表示
		}

		// ユーザー情報をデータベースから取得し、認証 (正しいIDとパスワードの組み合わせか確認)
		Student student = studentService.getStudentByLoginId(login.getLoginId());
		if(student == null || !login.isCorrectPassword(student.getLoginPass())) {
			errors.rejectValue("loginId", "error.incorrect_id_or_password");
			return "login-student";
		}

		// セッションに認証情報を格納　（ログイン情報をセッションに保存）
		LoginStatus loginStatus = LoginStatus.builder()
				.id(student.getId())
				.name(student.getName())
				.loginId(student.getLoginId())
				.authority(LoginAuthority.STUDENT)
				.build();
		session.setAttribute("loginStatus", loginStatus);
		return "redirect:/rental";
	}

	@GetMapping("/logout")
	public String logout(
			RedirectAttributes redirectAttributes) {
		session.removeAttribute("loginStatus");
		redirectAttributes.addFlashAttribute("message", "ログアウトしました。");
		return "redirect:/login";
	}

}