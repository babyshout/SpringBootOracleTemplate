package kopo.poly.persistance.mapper;

import kopo.poly.dto.UserInfoDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IUserInfoMapper {
    int insertUserInfo(UserInfoDTO pDTO) throws Exception;

    UserInfoDTO getUserIdExists(UserInfoDTO pDTO);

    UserInfoDTO getEmailExists(UserInfoDTO pDTO);

    List<UserInfoDTO> getUserInfoList();

    UserInfoDTO getUserInfo(UserInfoDTO pDTO);

    UserInfoDTO getLoginInfo(UserInfoDTO pDTO);


}
