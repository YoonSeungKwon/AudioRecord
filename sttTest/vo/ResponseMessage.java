package yoon.test.sttTest.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
public class ResponseMessage {

    private HttpStatus status;
    private String text;
    private byte[] audio;

    public ResponseMessage(){
        this.status = HttpStatus.BAD_REQUEST;
        this.text = null;
        this.audio = null;
    }

}
