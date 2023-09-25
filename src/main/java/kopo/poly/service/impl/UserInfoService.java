package kopo.poly.service.impl;

import kopo.poly.dto.MailDTO;
import kopo.poly.dto.UserInfoDTO;
import kopo.poly.persistance.mapper.IUserInfoMapper;
import kopo.poly.service.IMailService;
import kopo.poly.service.IUserInfoService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import kopo.poly.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserInfoService implements IUserInfoService {
    private final IUserInfoMapper userInfoMapper;

    private final IMailService mailService;


    @Override
    public UserInfoDTO getUserIdExists(UserInfoDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getUserIdExists START!!!!!!!");

        UserInfoDTO rDTO = userInfoMapper.getUserIdExists(pDTO);

        log.info(this.getClass().getName() + ".getUserIdExists END!!!!!!!");
        return rDTO;
    }

    @Override
    public UserInfoDTO getEmailExists(UserInfoDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".emailAuth START!!!!!!!");

        UserInfoDTO rDTO = userInfoMapper.getEmailExists(pDTO);

        String existsYn = CmmUtil.nvl(rDTO.getExistsYn());
        log.info("existYn : " + existsYn);

//        Object obj = new Object();
//
//        System.out.println(
//                new String("asdf").equals(new String("asdf"))
//        );

        if (existsYn.equals("N")) {

            int authNumber = ThreadLocalRandom.current().nextInt(100000, 1000000);

            log.info("authNumber : " + authNumber);

            MailDTO dto = new MailDTO();

            dto.setTitle("이메일 중복 확인 인증번호 발송 메일");
            dto.setContents("인증번호는 " + authNumber + " 입니다.");
            dto.setToMail(EncryptUtil.decAES128CBC(CmmUtil.nvl(pDTO.getEmail())));

            mailService.doSendMail(dto);

            dto = null;

            rDTO.setAuthNumber(authNumber);
        }


        log.info(this.getClass().getName() + ".emailAuth END!!!!!!!");
        return rDTO;
    }

    @Override
    @Transactional
    public int insertUserInfo(UserInfoDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".insertUserInfo END!!!!!!!");
        int res = 0;

        int success = userInfoMapper.insertUserInfo(pDTO);

        if (success > 0) {
            res = 1;
            MailDTO mDTO = new MailDTO();

            mDTO.setToMail(EncryptUtil.decAES128CBC(CmmUtil.nvl(pDTO.getEmail())));

            mDTO.setTitle("회원가입을 축하드립니다");

            mDTO.setContents(CmmUtil.nvl(pDTO.getUserName() + "님의 회원가입을 진심으로 축하드립니다"));

            mailService.doSendMail(mDTO);
        } else {
            res = 0;
        }
        log.info(this.getClass().getName() + ".insertUserInfo END!!!!!!!");
        return res;
    }

    @Override
    public List<UserInfoDTO> getUserInfoList() throws Exception {
        log.info(this.getClass().getName() + ".getUserInfoList() START!!!!!!!!!!!!!!!!!!!!!!");

        List<UserInfoDTO> rList = Optional.ofNullable(
                userInfoMapper.getUserInfoList()
        ).orElseGet(ArrayList::new);

        for (UserInfoDTO dto :
                rList) {
            log.info(dto.toString());
        }

        log.info(this.getClass().getName() + ".getUserInfoList() END!!!!!!!!!!!!!!!!!!!!!!");
        return rList;
    }

    @Override
    public UserInfoDTO getUserInfo(UserInfoDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getUserInfo() START!!!!!!!!!!!!!!!");

        log.info(pDTO.toString());

        UserInfoDTO rDTO = userInfoMapper.getUserInfo(pDTO);

        log.info(this.getClass().getName() + ".getUserInfo() END!!!!!!!!!!!!!!!");
        return rDTO;
    }

    @Override
    public UserInfoDTO getLoginInfo(UserInfoDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + " 로그인 시작!");

        UserInfoDTO rDTO = Optional.ofNullable(
                userInfoMapper.getLoginInfo(pDTO)
        ).orElseGet(UserInfoDTO::new);

        if (CmmUtil.nvl(rDTO.getUserId()).length() > 0) {
            MailDTO mDTO = new MailDTO();

            mDTO.setToMail(EncryptUtil.decAES128CBC(
                            CmmUtil.nvl(rDTO.getEmail())
                    )
            );

            mDTO.setTitle("로그인 알림!");

            mDTO.setContents(
                    DateUtil.getDateTime("yyyy.MM.dd hh:mm:ss") + "에 " +
                            CmmUtil.nvl(rDTO.getUserName()) + "님이 로그인 하셨습니다"
            );

//            mailService.doSendMail(mDTO);
        }

        log.info(this.getClass().getName() + " 로그인 시작!");
        return rDTO;
    }
}
