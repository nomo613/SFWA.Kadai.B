package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.app.domain.Material;

@Mapper
public interface MaterialMapper {
	
	List<Material> selectAll() throws Exception;       // 機能: Material テーブルの全データを取得する 
	                                                   // 戻り値: Material オブジェクトのリスト
	
	Material selectById(Integer id) throws Exception;  // 機能: 指定した id に対応する Material の情報を取得する 
	                                                   // パラメータ: id（教材のID）
	                                                   // 戻り値: 該当する Material オブジェクト（見つからない場合は null）
	Material selectByName(String name) throws Exception; 
	                                                   // 機能: 指定した name に一致する Material を取得する
	                                                   // パラメータ: name（教材名）
	                                                   // 戻り値: 該当する Material オブジェクト（見つからない場合は null）
	
	void setDeleteById(Integer id) throws Exception;   // 機能: 指定した id の Material を削除（または論理削除）する 
	                                                   // パラメータ: id（削除対象の教材ID） 
	                                                   // 戻り値: なし
	
	void insert(Material material) throws Exception;   // 機能: 新しい Material をデータベースに登録する
	                                                   // パラメータ: Material オブジェクト（挿入するデータ）
	                                                   // 戻り値: なし
	
	void update(Material material) throws Exception;   // 機能: 既存の Material のデータを更新する
	                                                   // パラメータ: 更新する Material オブジェクト（IDを元に更新）
	                                                   // 戻り値: なし
	 
	List<Material> selectLimited(@Param("offset") int offset, @Param("num") int num) throws Exception;
                                                       // 機能: 指定した範囲の Material を取得（ページネーション用）
	                                                   // offset → 取得開始位置（スキップする行数）
	                                                   // num → 取得する件数（ページサイズ）
	                                                   // 戻り値: Material オブジェクトのリスト
	
	long countActive() throws Exception;               // 機能: アクティブな Material の件数を取得（ページネーション用）
	                                                   // 戻り値: long 型の件数

	// @Mapper アノテーションにより、このインターフェースが MyBatis のマッパーである ことを明示
	// @Param("offset") や @Param("num") は SQL クエリにパラメータを渡すため に使用
    // この MaterialMapper は、教材管理システム において、教材データの管理（取得・登録・更新・削除）を担当する部分
}