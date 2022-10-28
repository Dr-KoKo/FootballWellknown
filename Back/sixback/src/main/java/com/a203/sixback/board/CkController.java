package com.a203.sixback.board;

import com.a203.sixback.board.res.FileRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/editor")
public class CkController {

    private final FileUploadService fileUploadService;

    @PostMapping("/upload")
    public ResponseEntity<FileRes> fileUploadFromCKEditor(@RequestParam("upload") MultipartFile image) throws Exception {
        return new ResponseEntity<>(FileRes.builder()
                .uploaded(true)
                .url(fileUploadService.upload(image))
                .build(), HttpStatus.OK);
    }
}
