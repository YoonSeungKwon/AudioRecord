package yoon.test.sttTest.service;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import org.springframework.stereotype.Service;

@Service
public class TranslateService {

    Translate translate = TranslateOptions.getDefaultInstance().getService();

    public String translateInput(String raw){
        Translation translation = translate.translate(raw, Translate.TranslateOption.sourceLanguage("ko"),
                Translate.TranslateOption.targetLanguage("en"));
        return translation.getTranslatedText();
    }

    public String translateOutput(String raw){
        Translation translation = translate.translate(raw, Translate.TranslateOption.sourceLanguage("en"),
                Translate.TranslateOption.targetLanguage("ko"));
        String text =  translation.getTranslatedText();
        text = text.replaceAll("&#39", "\'");
        return text;
    }
}
