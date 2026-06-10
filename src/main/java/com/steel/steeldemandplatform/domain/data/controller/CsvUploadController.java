package com.steel.steeldemandplatform.domain.data.controller;

import com.steel.steeldemandplatform.domain.data.service.CsvUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/data")
public class CsvUploadController {

    private final CsvUploadService csvUploadService;

    @PostMapping("/upload")
    public String uploadCsv(
            @RequestParam("file") MultipartFile file
    ) {

        csvUploadService.upload(file);

        return "CSV 업로드 완료";
    }
}