package com.matchingMatch.match.exception;

public class FileUploadException extends IllegalStateException {

        private static final String MESSAGE = "파일 업로드에 실패했습니다.";

        public FileUploadException(String message) {
            super(MESSAGE+": "+ message);
        }
}
