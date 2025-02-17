package com.example.app.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.app.login.AdminAuthFilter;
import com.example.app.login.StudentAuthFilter;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

	// バリデーションメッセージのカスタマイズ
	@Override
	public Validator getValidator() {
		var validator = new LocalValidatorFactoryBean();
		validator.setValidationMessageSource(messageSource());
		return validator;
	}

    @Bean
    MessageSource messageSource() {
		var messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("validation");
		return messageSource;
	}
    
 // 認証用フィルタの有効化  
    
 // 管理者のみアクセス可能にする
    @Bean   // AdminAuthFilter（管理者用の認証フィルタ）を登録
    FilterRegistrationBean<AdminAuthFilter> adminAuthFilter() {
 		var bean = new FilterRegistrationBean<AdminAuthFilter>(new AdminAuthFilter());
 		bean.addUrlPatterns("/admin/material/*");  // /admin/material/* → 材料管理 (material) の管理ページ
 		bean.addUrlPatterns("/admin/student/*");   // /admin/student/* → 学生管理 (student) の管理ページ
 		return bean;
 	}

 // StudentAuthFilter（学生用の認証フィルタ）を登録 
    @Bean
    FilterRegistrationBean<StudentAuthFilter> studentAuthFilter() {
 		var bean = new FilterRegistrationBean<StudentAuthFilter>(new StudentAuthFilter());
 		bean.addUrlPatterns("/");            //  / → トップページ（ホーム画面など）
 		bean.addUrlPatterns("/rental/*");    //  /rental/* → レンタル機能に関するページ
 		return bean;                         //  学生がログインしているかを確認
 	}

}
  

//  FilterRegistrationBean とは？
//  FilterRegistrationBean<T> を使うことで、Spring Boot で カスタムフィルタの登録 ができる
//  フィルタの適用範囲 (addUrlPatterns(...)) を設定可能
//  Spring Security を使わずに独自の認証処理を実装する場合に便利 
//  このコードは、Spring Boot で認証フィルタを設定するもの。
//    管理者 (AdminAuthFilter) → /admin/material/*, /admin/student/* のアクセス制限 
//    学生 (StudentAuthFilter) → / や /rental/* で認証チェック




