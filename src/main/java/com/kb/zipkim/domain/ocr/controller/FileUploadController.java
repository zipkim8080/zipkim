package com.kb.zipkim.domain.ocr.controller;

import com.kb.zipkim.domain.ocr.service.OCRGeneralAPIDemo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

@RestController
@RequiredArgsConstructor
public class FileUploadController {

    private final OCRGeneralAPIDemo ocrService;


    @PostMapping("/upload-property-doc")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            // 파일이 비었는지 확인
            if (file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("파일이 없습니다.");
            }

            // 파일을 서버에 임시 저장
            File pdfFile = new File(file.getOriginalFilename()); // 업로드된 파일의 원래 이름 가져오기
            try (FileOutputStream fos = new FileOutputStream(pdfFile)) {
                fos.write(file.getBytes()); // 파일 이름 이용해서 새로운 객체 파일 만들기
            }

            // OCR 서비스 호출
            String ocrResult = ocrService.processOCR(pdfFile.getAbsolutePath());

            pdfFile.delete();

            return ResponseEntity.ok(ocrResult);

        } catch (Exception e) {
            e.printStackTrace();  // 오류 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 업로드 중 오류가 발생했습니다.");
        }
    }
}

