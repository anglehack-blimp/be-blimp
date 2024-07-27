package com.blimp.backend.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
public class DocumentController {

    @GetMapping(
            path = "/file",
            produces = {"video/mp4", MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE}
    )
    public byte[] getOrDownload(
            @RequestParam String directory,
            @RequestParam(
                    name = "download",
                    defaultValue = "false",
                    required = false
            )
            boolean wantToDownload,
            HttpServletResponse response
    ) {
        var filePath = Path.of(directory);
        try {
            if (wantToDownload) {
                response.setHeader(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"%s\"".formatted(filePath.getFileName())
                );
            }
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "'%s' is not found".formatted(directory));
        }
    }

}
