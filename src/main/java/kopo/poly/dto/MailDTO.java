package kopo.poly.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class MailDTO {
    String mailSeq;   // mail 시퀀스
    String toMail;  // 받는 사람
    String title;   // 보내는 메일 제목
    String contents;    // 보내는 메일 내용
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm:ss")
    LocalDateTime sendTime; // 보낸 날짜 (년 월 일 시 분 초)
}
