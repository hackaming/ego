package com.ego.manage.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ego.manage.service.PictureService;
import com.ego.commons.utils.FtpUtil;
import com.ego.commons.utils.IDUtils;

@Service
public class PictureServiceImpl implements PictureService{
	@Value("${ftp.client.host}")
	private String host;
	@Value("${ftp.client.port}")
	private String port;
	@Value("${ftp.client.user}")
	private String username;
	@Value("${ftp.client.password}")
	private String password;
	@Value("${ftp.client.basePath}")
	private String basePath;
	@Value("${ftp.client.filePath}")
	private String filePath;

	
	@Override
	public Map<String, Object> upload(MultipartFile file) {
		String filename = IDUtils.genImageName()+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			boolean f = FtpUtil.uploadFile(host, Integer.parseInt(port), username, password, basePath, filePath, filename, file.getInputStream());
			if (f){
				map.put("error", 0);
				map.put("url", "http://"+host+"/"+filename); //note that if the ginx runs at 80, then no needs to add :7080
			} else {
				map.put("error", 0);
				map.put("message", "File upload failure");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

}
