package yoon.test.sttTest.controller;

import com.google.protobuf.Message;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yoon.test.sttTest.service.Palm2Service;
import yoon.test.sttTest.service.SttService;
import yoon.test.sttTest.service.TranslateService;
import yoon.test.sttTest.service.TtsService;
import yoon.test.sttTest.vo.ResponseMessage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stt")
public class MainController {

    private final SttService sttService;
    private final TranslateService translateService;
    private final Palm2Service palm2Service;
    private final TtsService ttsService;
    @PostMapping("/")
    public ResponseEntity<Resource> stt(@RequestParam("audio") MultipartFile file) throws IOException {

        String input = sttService.stt(file);

        String text = translateService.translateInput(input);

        System.out.println("Question: " + text);

        String palm2 = palm2Service.palm2(text);

        String output = translateService.translateOutput(palm2);

        byte[] result = ttsService.tts(output);

        Path tempFile = Files.createTempFile("tts", ",mp3");
        Files.write(tempFile, result);

        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(tempFile));

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        System.out.println("Answer: " + output);

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

}
