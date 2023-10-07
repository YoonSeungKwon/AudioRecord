package yoon.test.sttTest.service;

import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SttService {

    public String stt(MultipartFile file){

        String message = "";

        // Instantiates a client
        try (SpeechClient speechClient = SpeechClient.create()) {

            byte[] audioByte = file.getBytes();

            // Builds the sync recognize request
            RecognitionConfig config =
                    RecognitionConfig.newBuilder()
                            .setEncoding(RecognitionConfig.AudioEncoding.WEBM_OPUS)
                            .setSampleRateHertz(48000)
                            .setAudioChannelCount(2)
                            .setLanguageCode("en-US")
                            .build();

            RecognitionAudio audio = RecognitionAudio.newBuilder().setContent(ByteString.copyFrom(audioByte)).build();

            // Performs speech recognition on the audio file
            RecognizeResponse response = speechClient.recognize(config, audio);
            List<SpeechRecognitionResult> results = response.getResultsList();

            for (SpeechRecognitionResult result : results) {
                // There can be several alternative transcripts for a given chunk of speech. Just use the
                // first (most likely) one here.
                SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                System.out.printf("Transcription: %s%n", alternative.getTranscript());
                message = alternative.getTranscript();
            }
        } catch (Exception e){
            message = "error! Message:" + e.getMessage();
        }

        return message;
    }
}
