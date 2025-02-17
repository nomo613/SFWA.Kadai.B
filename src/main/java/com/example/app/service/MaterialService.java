package com.example.app.service;

import java.util.List;

import com.example.app.domain.Material;
import com.example.app.domain.MaterialType;

public interface MaterialService {
// このインターフェースは、材料（Material）と材料の種類（MaterialType）を
// 管理するためのメソッドを提供しています *ページ分割機能用
	
	// 1. 材料（Material）に関するメソッド

	List<Material> getMaterialList() throws Exception;       // 戻り値は List<Material> で、データベースに登録されているすべての Material（材料）のリストが返ります
	                                                         // すべての材料を取得するメソッドです
	                                                         // 例外処理 (throws Exception) が定義されているため、データ取得時にエラーが発生する可能性があります。
	
	Material getMaterialById(Integer id) throws Exception;   // 指定した id に対応する材料情報を取得するメソッドです
	                                                         // id は一意の識別子（主キー）であり、その id に対応する Material オブジェクトを返します。
	                                                         // 該当する id がない場合、null または例外がスローされる可能性があります
	
	void deleteMaterialById(Integer id) throws Exception;    // 指定した id の材料データを削除するメソッドです
	                                                         // データベースから該当の Material を削除する処理が行われます
	                                                         // void のため戻り値はなく、処理が成功すると該当データが削除されます 
	
	void addMaterial(Material material) throws Exception;    // 新しい材料を登録するメソッドです
	                                                         // 引数に Material オブジェクトを受け取り、それをデータベースに登録します
	                                                         // void のため戻り値はありませんが、登録処理が成功するとデータが追加されます
	
	void editMaterial(Material material) throws Exception;   // 既存の材料データを更新するメソッドです
	                                                         // Material オブジェクトを引数に取り、その情報でデータベースを更新します
	                                                         // 例えば、材料の名前や価格などの情報を変更する際に使用されます
	
	boolean isExistingMaterial(String name) throws Exception; // 指定された name の材料がデータベースに存在するか確認するメソッドです
	                                                          // true（存在する）または false（存在しない）を返します
	                                                          // 
	
	List<Material> getMaterialListPerPage(int page, int numPerPage) throws Exception;
                                                              // ページネーション（ページごとのデータ取得）を実装した材料リスト取得メソッドです
	                                                          // page（取得するページ番号）と numPerPage（1ページあたりの取得件数）を指定し、対応する Material のリストを返します
	                                                          // 例えば、getMaterialListPerPage(2, 10) を呼び出すと、2ページ目の 10 件のデータが取得されます
	
	int getTotalPages(int numPerPage) throws Exception;       // ページネーション用のメソッドで、総ページ数を取得します
	                                                          // numPerPage（1ページあたりのデータ件数）を指定し、全データを表示するのに必要なページ数を計算します
	                                                          // 例えば、データが 50 件あり、1ページに 10 件表示する場合、getTotalPages(10) の戻り値は 5 になります

	// 2. 材料の種類（MaterialType）に関するメソッド
	
	List<MaterialType> getMaterialTypeList() throws Exception; // 材料の種類一覧を取得するメソッド
	                                                           // MaterialType は、おそらく材料の分類情報を保持しているクラスでしょう
	                                                           // List<MaterialType> を返すので、登録されているすべての材料種類が取得できます	
}



