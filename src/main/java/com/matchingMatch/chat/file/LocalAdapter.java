package com.matchingMatch.chat.file;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.matchingMatch.match.exception.FileUploadException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LocalAdapter implements FileStorage {

	private static final String LOCAL_PATH = "src/main/resources/uploads";

	@Override
	public String uploadFile(MultipartFile multipartFile) {
		if (multipartFile.isEmpty()) {
			throw new FileUploadException("파일이 비어있습니다.");
		}

		Path uploadPath = Paths.get(LOCAL_PATH);

		if (!Files.exists(uploadPath)) {
			try {
				Files.createDirectories(uploadPath);
			} catch (Exception e) {
				String message = "디렉토리 생성 실패, 파일 이름:" + multipartFile.getOriginalFilename();
				log.error(message, e);
				throw new FileUploadException(message);
			}
		}

		String uniqueFileName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();

		Path filePath = uploadPath.resolve(uniqueFileName);

		try {
			multipartFile.transferTo(filePath.toFile());
		} catch (Exception e) {
			String message = "로컬 파일 변환 실패, 파일 이름:" + multipartFile.getOriginalFilename();
			log.error("로컬 파일 업로드 실패", e);
			throw new FileUploadException(message);
		}
		return filePath.toString();

	}
}
