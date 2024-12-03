package com.softdevsix.presentation.controllers;

import com.softdevsix.domain.entities.file.File;
import com.softdevsix.application.dto.FileCoverageDto;
import com.softdevsix.application.services.IFileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/coverage/file/")
public class FileCoverageController {
    private final IFileService fileService;

    public FileCoverageController(IFileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/{idFile}")
    public ResponseEntity<FileCoverageDto> getFileCoverage(@PathVariable UUID idFile) {
        File file = fileService.getFileById(idFile);

        FileCoverageDto dto = new FileCoverageDto(
                file.getFileName(),
                file.getPath(),
                file.getLineCode(),
                fileService.calculateFileMethodCoverage(file),
                fileService.calculateFileCoverage(file),
                fileService.getUncoveredLines(file)
        );

        return ResponseEntity.ok(dto);
    }
}
