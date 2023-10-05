package kopo.poly.service.impl;

import kopo.poly.dto.MailDTO;
import kopo.poly.dto.UserInfoDTO;
import kopo.poly.persistance.mapper.IMailMapper;
import kopo.poly.service.IMailService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailService implements IMailService {

    private final JavaMailSender mailSender;

    private final IMailMapper mailMapper;

    @Value("${spring.mail.username}")
    private String fromMail;

    @Transactional
    @Override
    public int doSendMail(MailDTO pDTO) {
        log.info(this.getClass().getName() + ".doSendMail start!");

        int res = 1;

        if (pDTO == null) {
            pDTO = new MailDTO();
        }
        pDTO.setSendTime(LocalDateTime.now());
        log.info("pDTO (MailDTO) : " + pDTO.toString());
        log.info("pDTO.sendTime : " + pDTO.getSendTime());

        String toMail = CmmUtil.nvl(pDTO.getToMail());
        String title = CmmUtil.nvl(pDTO.getTitle());
        String contents = CmmUtil.nvl(pDTO.getContents());

        log.info("toMail : " + toMail);
        log.info("title : " + title);
        log.info("contents : " + contents);

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper messageHelper = new MimeMessageHelper(message, "utf-8");

        try {
            messageHelper.setTo(toMail);
            messageHelper.setFrom(fromMail);
            messageHelper.setSubject(title);
            messageHelper.setText(contents);
//            messageHelper.setFileTypeMap();

            mailSender.send(message);

            mailMapper.insertMail(pDTO);
        } catch (MessagingException e) {
            res = 0;
            log.info("[ERROR] " + this.getClass().getName() + ".doSendMail : " + e);
            throw new RuntimeException(e);
        }

        log.info(this.getClass().getName() + ".doSendMail end!");

        return res;
    }

    /**
     * 얘로 mapper 랑 연결해서 mail 들 List 로 다 가져올거임
     *
     * @return MailDTO 가 담긴 List
     */
    @Override
    public List<MailDTO> getMailList() {
        log.info(this.getClass().getName() + ".getMailList START!");

//        List<MailDTO> rList = Optional.ofNullable(
//                mailMapper.getMailList()
//        ).orElseGet(ArrayList::new);

        List<MailDTO> rList = mailMapper.getMailList();

        log.info(rList.toString());

//        Optional.ofNullable(mailMapper.getMailList()).

        log.info(this.getClass().getName() + ".getMailList END!");
        return rList;
    }

//    @Override
//    public UserInfoDTO authorizeEmail(UserInfoDTO pDTO) {
//            log.info(this.getClass().getName() + ".authorizeEmail START!!!!!!!");
//
//            UserInfoDTO rDTO = userInfoMapper.getEmailExists(pDTO);
//
//            String existsYn = CmmUtil.nvl(rDTO.getExistsYn());
//            log.info("existYn : " + existsYn);
//
////        Object obj = new Object();
////
////        System.out.println(
////                new String("asdf").equals(new String("asdf"))
////        );
//
//            if (existsYn.equals("N")) {
//
//                int authNumber = ThreadLocalRandom.current().nextInt(100000, 1000000);
//
//                log.info("authNumber : " + authNumber);
//
//                MailDTO dto = new MailDTO();
//
//                dto.setTitle("이메일 중복 확인 인증번호 발송 메일");
//                dto.setContents("인증번호는 " + authNumber + " 입니다.");
//                dto.setToMail(EncryptUtil.decAES128CBC(CmmUtil.nvl(pDTO.getEmail())));
//
//                mailService.doSendMail(dto);
//
//                dto = null;
//
//                rDTO.setAuthNumber(authNumber);
//            }
//
//
//            log.info(this.getClass().getName() + ".emailAuth END!!!!!!!");
//            return rDTO;
//
//    }
}
