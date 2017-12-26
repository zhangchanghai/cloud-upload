package com.boot.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {


	@PostMapping(value = "/upload")
	public Object upload(@RequestParam(required = false) MultipartFile file) {
		Map<String, Object> map = new HashMap<>();
		if (file != null) {
			String[] arrpath = uploadpath(file);
			Boolean b = uploadPic(arrpath, file);
			map.put("success", b);
			map.put("path", arrpath);
		} else {
			map.put("success", false);
			map.put("msg", "上传的文件不能为空");
		}
		return map;
	}

	/**
	 * 生成图片路径 路径arr=文件夹，文件，相对
	 * 
	 * @author Changhai
	 * @date 2017-9-30 上午9:35:38
	 * @param file
	 * @param request
	 * @return
	 */
	public  String[] uploadpath(MultipartFile file) {
		try {
			// 文件夹，文件，相对
			String[] arrpath = new String[3];
			String classpath = ClassUtils.getDefaultClassLoader().getResource("").getPath();
			String realPath =classpath+ "static/upload/";
			if (file != null) {
				String fileName = file.getOriginalFilename();
				if (fileName == null || !fileName.matches("^.+\\.(?i)((png)|(jpg))$")) {
					return null;
				} else {
					SimpleDateFormat ad = new SimpleDateFormat("yyyyMMdd");
					String datepath = ad.format(new Date());
					String path = realPath + datepath;
					arrpath[0] = path;
					// 文件后缀名
					String suffixName = fileName.substring(fileName.lastIndexOf("."), fileName.length());
					String filename = UUID.randomUUID().toString() + suffixName;
					System.out.println("生成的文件路径:\t" + path + "\\" + filename);
					arrpath[1] = path + "/" + filename;
					arrpath[2] = "upload/" + datepath + "/" + filename;
					return arrpath;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 上传图片
	 * 
	 * @author Changhai
	 * @date 2017-9-30 上午9:35:30
	 * @param arr
	 * @param file
	 * @return
	 */
	public  Boolean uploadPic(String[] arrpath, MultipartFile file) {
		try {
			File dir = new File(arrpath[0]);
			if (!dir.exists()) {// 判断目录是否存在
				dir.mkdirs();
			}
			file.transferTo(new File(arrpath[1]));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
