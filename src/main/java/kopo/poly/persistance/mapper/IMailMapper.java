package kopo.poly.persistance.mapper;

import kopo.poly.dto.MailDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IMailMapper {

    /**
     * 얘가 MailDTO 를 MAIL_INFO 테이블에 넣어줄거임
     * @param pDTO 저장하고 싶은 MailDTO 받아옴
     * @return 없데이트나 딜리트하면 나오는 결과값 나오는거 그거 갯수 리턴해줌
     */
    int insertMail(MailDTO pDTO);
    List<MailDTO> getMailList();
}
