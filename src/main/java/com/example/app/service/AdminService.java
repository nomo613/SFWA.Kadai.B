package com.example.app.service;

import org.springframework.stereotype.Service;

import com.example.app.domain.Admin;

@Service
public interface AdminService {

	Admin getAdminByLoginId(String loginId)throws Exception;
}
