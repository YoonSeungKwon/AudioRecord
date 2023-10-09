package yoon.test.sttTest.service;

import com.google.ai.generativelanguage.v1beta3.*;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.grpc.InstantiatingGrpcChannelProvider;
import com.google.api.gax.rpc.FixedHeaderProvider;
import com.google.api.gax.rpc.TransportChannelProvider;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class Palm2Service {

    @Value("${google.api-Key}")
    private String key;
    public String palm2(String text) throws IOException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("x-goog-api-key", key);

        TransportChannelProvider provider = InstantiatingGrpcChannelProvider.newBuilder()
                .setHeaderProvider(FixedHeaderProvider.create(headers))
                .build();

        TextServiceSettings settings = TextServiceSettings.newBuilder()
                .setTransportChannelProvider(provider)
                .setCredentialsProvider(FixedCredentialsProvider.create(null))
                .build();

        TextServiceClient client = TextServiceClient.create(settings);

        TextPrompt prompt = TextPrompt.newBuilder()
                .setText(text)
                .build();

        GenerateTextRequest request = GenerateTextRequest.newBuilder()
                .setModel("models/text-bison-001") // Required, which model to use to generate the result
                .setPrompt(prompt) // Required
                .setTemperature(0.5f) // Optional, controls the randomness of the output
                .setCandidateCount(1) // Optional, the number of generated texts to return
                .build();

        GenerateTextResponse response = client.generateText(request);

        TextCompletion returnedText = response.getCandidatesList().get(0);

        return returnedText.getOutput();
    }
}
