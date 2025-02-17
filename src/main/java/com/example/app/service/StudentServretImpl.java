package com.example.app.service;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.domain.Student;
import com.example.app.mapper.StudentMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentServretImpl implements StudentService {
	// この StudentServiceImpl クラスは、StudentService インターフェースを実装し、
	// 学生情報の管理を行う サービス層 (Service Layer) のクラスです
	// Spring Boot で @Service アノテーションが付与されており、
	// トランザクション管理 (@Transactional) も適用されています
	
	
	// クラスの役割  このクラスは、StudentMapper（MyBatis の Mapper）を利用して
	// 学生データの取得・更新・削除などを行うビジネスロジックを担当します
	
	private final StudentMapper studentMapper;
	
	
	// 1. 学生一覧取得
	@Override
	public List<Student> getStudentList() throws Exception {      // データベースから すべての学生リスト を取得する。
		return studentMapper.selectAll();                         // studentMapper.selectAll() を呼び出している
	}

	
	// 2. 学生情報取得 (ID指定)
	@Override
	public Student getStudentById(Integer id) throws Exception {  // ID を指定して 学生情報を取得する
		 return studentMapper.selectById(id);                     // studentMapper.selectById(id) を呼び出している
	}

	@Override
	public Student getStudentByLoginId(String loginId) throws Exception {
		return studentMapper.selectByLoginId(loginId);
	}
	
	
	// 3. 学生削除
	@Override
	public void deleteStudentById(Integer id) throws Exception {  // ID を指定して学生を削除 する
		 studentMapper.setDeleteById(id);                         // setDeleteById(id) は、物理削除ではなく「論理削除（フラグを変更する）」可能性がある
		
	}

	
	// 4. 学生追加
	@Override
	public void addStudent(Student student) throws Exception {    // BCrypt を使って パスワードをハッシュ化 し、データベースに保存する
		String password = student.getLoginPass();
	    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
	    student.setLoginPass(hashedPassword);
	    studentMapper.insert(student);                            // studentMapper.insert(student) で新しい学生を登録する
		
	}

	
	//5. 学生情報更新
	@Override
	public void editStudent(Student student) throws Exception {   // パスワードをハッシュ化 してから、データを更新する
		String password = student.getLoginPass();
	    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
	    student.setLoginPass(hashedPassword);
	    studentMapper.update(student);                            // studentMapper.update(student) を実行して、データを変更する
		
	}

	// 6. 学生が既に存在するかチェック
	@Override
	public boolean isExistingStudent(String loginId) throws Exception {
		Student student = studentMapper.selectByLoginId(loginId);   // ログイン ID が既に登録されているか確認する
	    if(student != null) {                                       // studentMapper.selectByLoginId(loginId) を実行し、値が存在すれば true を返す
	        return true;
	    }
	    return false;
	}

	// 7. 学生一覧をページごとに取得
	@Override
	public List<Student> getStudentListPerPage(int page, int numPerPage) throws Exception {
		int offset = numPerPage * (page - 1);                        // offset を計算し、ページごとのデータを取得 する
	    return studentMapper.selectLimited(offset, numPerPage);      // studentMapper.selectLimited(offset, numPerPage) を実行
	}

	
	// 8. 学生の総ページ数を取得
	@Override
	public int getTotalPages(int numPerPage) throws Exception {      // countActive() で 有効な学生の総数を取得 する
		long count = studentMapper.countActive();                    // 総ページ数 を計算し、整数値で返す
	    return (int) Math.ceil((double) count / numPerPage);
	}




}

// この StudentServiceImpl クラスは、学生の管理を行うサービスレイヤーのクラスです
// StudentMapper を利用して 学生データの取得・登録・更新・削除 を行う
// BCrypt で パスワードをハッシュ化 して保存する
// ページネーション 機能をサポートしている
// @Transactional により、データの整合性を保つ
// データベースの処理は StudentMapper に委任しており、MyBatis を使って SQL を実行しています






