package kopo.poly.service;

import kopo.poly.dto.MailDTO;

import java.util.List;

public interface IMailService {
    int doSendMail(MailDTO pDTO);

    /**
     * 얘로 mapper 랑 연결해서 mail 들 List 로 다 가져올거임
     * @return MailDTO 가 담긴 List
     */
    List<MailDTO> getMailList();
}
