package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.app.domain.Student;

// この StudentMapper インターフェースは、MyBatis を使用してデータベースの 
// Student テーブルにアクセスするためのマッパー（Mapper） です。@Mapper アノテーションが付いているため、MyBatis によって SQL マッピングが自動で行われます。
// 以下、それぞれのメソッドの説明をします

@Mapper
public interface StudentMapper {
	
	// 1. 学生データ取得に関するメソッド
	List<Student> selectAll() throws Exception;          // すべての学生データを取得するメソッド
	                                                     // List<Student> を返し、データベースにあるすべての Student レコードを取得します
	
	Student selectById(Integer id) throws Exception;     // id を使って特定の学生を取得するメソッド
	                                                     // id は主キーであり、一意の Student を取得します
	                                                     // 該当する id が存在しない場合、null または例外がスローされる可能性があります
	
	Student selectByLoginId(String loginId) throws Exception; // loginId を使って学生情報を取得するメソッド
	                                                         // loginId は、おそらく学生のログイン ID（ユーザー名のようなもの）であり、一意の Student を取得します
	
	// 2. 学生データの削除
    void setDeleteById(Integer id) throws Exception;     // 指定した id の学生データを削除するメソッド 
                                                      // void なので戻り値はなく、削除処理が成功すると該当データが削除されます
    // 3. 学生データの追加・更新
    void insert(Student student) throws Exception;    // 新しい学生データを追加するメソッド
                                                      // Student オブジェクトを引数に受け取り、それをデータベースに登録します
                                                      // void のため戻り値はありませんが、登録が成功すると新しい Student レコードが作成されます
    
    void update(Student student) throws Exception;    // 既存の学生データを更新するメソッド
                                                      // Student オブジェクトを引数に受け取り、その情報でデータベースのレコードを更新します
    
    //4. ページネーション関連
    List<Student> selectLimited(@Param("offset")int offset, @Param("num")int num)throws Exception;
                                                      // ページネーション（データの一部だけを取得）用のメソッド 
                                                      // offset: 取得開始位置（スキップする件数）
                                                      // num: 取得する件数（ページサイズ）
                                                      // @Param("offset") や @Param("num") は、SQL クエリで使用されるパラメータ名を指定するために使われます
    long countActive() throws Exception;
                                     // 「アクティブな学生」の総数を取得するメソッド
                                     // long を返すので、該当する学生の数を返します
                                     // "Active" ということは、おそらく 削除されていない学生データ の件数を取得する処理でしょう
    
}



// 学生データの取得 (selectAll, selectById)
// 学生データの追加 (insert) と更新 (update)
// 学生データの削除 (setDeleteById)
// ページネーション対応 (selectLimited)
// 学生の総数取得 (countActive)


