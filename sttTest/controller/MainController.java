package yoon.test.sttTest.controller;

import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yoon.test.sttTest.service.Palm2Service;
import yoon.test.sttTest.service.SttService;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stt")
public class MainController {

    private final SttService sttService;
    private final Palm2Service palm2Service;
    @PostMapping("/")
    public ResponseEntity<String> stt(@RequestParam("audio") MultipartFile file) throws IOException {
        System.out.println(file.getContentType());
        String result1 = sttService.stt(file);
        System.out.println("Question: " + result1);
        String result2 = palm2Service.palm2(result1);
        System.out.println("Answer: " + result2);
        return ResponseEntity.ok(result2);
    }

}
