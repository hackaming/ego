package com.ego.manage.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;


public interface PictureService {
	Map<String,Object> upload(MultipartFile file);
}
