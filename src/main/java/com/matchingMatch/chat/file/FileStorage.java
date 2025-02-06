package com.matchingMatch.chat.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorage {
	String uploadFile(MultipartFile multipartFile);
}
