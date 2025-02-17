package com.example.app.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.Material;
import com.example.app.service.MaterialService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/material")
@RequiredArgsConstructor
public class MaterialController {

	
	// 2. フィールドの定義 
	
	private static final int NUM_PER_PAGE = 5; // 1ページに表示する教材の数（5件）

	private final MaterialService service;      // 教材のデータを操作するサービスクラス
	private final HttpSession session;          //  セッション情報を保持するためのオブジェクト
	                                            // （現在のページ番号を保存するために利用）

	
	// 3. 教材一覧表示 (/list) 
	@GetMapping("/list")
	public String list(
			@RequestParam(name="page", defaultValue="1") Integer page,  // リクエストパラメータ page を受け取り、デフォルトは 1
			Model model) throws Exception {
		
		// 詳細・追加・編集ページから戻る際に利用
		session.setAttribute("page", page);  // 現在のページ番号をセッションに保存
		
	    int totalPages = service.getTotalPages(NUM_PER_PAGE);  // 全データの総ページ数を取得
	    model.addAttribute("totalPages", totalPages);
	    model.addAttribute("currentPage", page);
		model.addAttribute("materialList", service.getMaterialListPerPage(page, NUM_PER_PAGE)); // 指定したページの教材リストを取得。
		return "admin/list-material";  // admin/list-material.jsp にデータを渡してページを表示
	}

	
	// 4. 教材の詳細表示 (/show/{id})
	@GetMapping("/show/{id}")
	public String show(
			@PathVariable Integer id, // URL の {id} を取得
			Model model) throws Exception {
		model.addAttribute("material", service.getMaterialById(id)); // 指定 ID の教材を取得
		return "admin/show-material"; // 詳細ページへデータを渡す
	}

	
	// 5. 教材の追加 (/add)
	  // (1) 追加フォームの表示
	@GetMapping("/add")   // 新規の Material オブジェクトをモデルに追加
	public String add(Model model) throws Exception {    // 教材タイプリストを取得し、モデルに追加
		model.addAttribute("material", new Material()); 
		model.addAttribute("materialTypeList", service.getMaterialTypeList());
		model.addAttribute("heading", "視聴覚教材の追加"); // heading を設定し、同じフォームテンプレート (save-material.jsp) を利用
		return "admin/save-material";
	}

	 
	  // (2) 教材の登録処理
	@PostMapping("/add")
	public String add(
			@Valid Material material,  // オブジェクトのバリデーションを実施
			Errors errors,
			Model model,
			RedirectAttributes redirectAttributes) throws Exception {

		if(!material.getName().isBlank()) {
			if(service.isExistingMaterial(material.getName())) {
				errors.rejectValue("name", "error.existing_material_name"); // 既存の教材名があればエラー
			}
		}

		if(errors.hasErrors()) {  // エラーがあれば入力画面に戻る
			model.addAttribute("materialTypeList", service.getMaterialTypeList());
			model.addAttribute("heading", "視聴覚教材の追加");
			return "admin/save-material";
		}

		service.addMaterial(material); // 新しい教材をデータベースに保存
		redirectAttributes.addFlashAttribute("message", "教材を追加しました。");
		
		// 追加後に戻るページ(⇒最終ページ) 追加後、最終ページへリダイレクト
		int totalPages = service.getTotalPages(NUM_PER_PAGE);
		return "redirect:/admin/material/list?page=" + totalPages;
	}

	
	// 6. 教材の編集 (/edit/{id})
	  // (1) 編集フォームの表示  *指定IDの教材を取得し、フォームに表示
	@GetMapping("/edit/{id}")
	public String edit(
			@PathVariable Integer id,
			Model model) throws Exception {
		model.addAttribute("material", service.getMaterialById(id));
		model.addAttribute("materialTypeList", service.getMaterialTypeList());
		model.addAttribute("heading", "視聴覚教材の編集");
		return "admin/save-material";
	}

	  // (2) 教材の更新処理
	@PostMapping("/edit/{id}")
	public String edit(
			@PathVariable Integer id,
			@Valid Material material,
			Errors errors,
			Model model,
			RedirectAttributes redirectAttributes) throws Exception {

		String originalMaterialName = service.getMaterialById(id).getName();

		if(!material.getName().isBlank()) {       // 既存の教材名があればエラー
			if(!originalMaterialName.equals(material.getName()) && service.isExistingMaterial(material.getName())) {
				errors.rejectValue("name", "error.existing_material_name");
			}
		}

		if(errors.hasErrors()) {  // エラーがあれば入力画面に戻る
			model.addAttribute("materialTypeList", service.getMaterialTypeList());
			model.addAttribute("heading", "視聴覚教材の編集");
			return "admin/save-material";
		}

		service.editMaterial(material);  // 更新した教材をデータベースに保存
		redirectAttributes.addFlashAttribute("message", "教材を編集しました。");
		
		// 編集後に戻るページ(元のページ)   編集後、元のページへリダイレクト
		int previousPage = (int) session.getAttribute("page");
		return "redirect:/admin/material/list?page=" + previousPage;
	}

	
	// 7. 教材の削除 (/delete/{id})
	@GetMapping("/delete/{id}")  
	public String delete(
			@PathVariable Integer id,
			RedirectAttributes redirectAttributes) throws Exception {
		service.deleteMaterialById(id);           // 指定 ID の教材を削除
		redirectAttributes.addFlashAttribute("message", "教材を削除しました。");
		
		// 削除後、元のページがなくなった場合は最終ページへリダイレクト 
		// 削除後に戻るページ(⇒ページ数が減って、元のページが無くなった場合は最終ページ)
		int previousPage = (int) session.getAttribute("page");
		int totalPages = service.getTotalPages(NUM_PER_PAGE);
		int page = previousPage <= totalPages ? previousPage : totalPages;
		return "redirect:/admin/material/list?page=" + page;
	}

}
