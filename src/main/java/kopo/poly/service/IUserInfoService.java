package kopo.poly.service;

import kopo.poly.dto.UserInfoDTO;

import java.util.List;

public interface IUserInfoService {
    UserInfoDTO getUserIdExists(UserInfoDTO pDTO) throws Exception;

    UserInfoDTO getEmailExists(UserInfoDTO pDTO) throws Exception;

    int insertUserInfo(UserInfoDTO pDTO) throws Exception;

    List<UserInfoDTO> getUserInfoList() throws Exception;

    UserInfoDTO getUserInfo(UserInfoDTO pDTO) throws Exception;

    UserInfoDTO getLoginInfo(UserInfoDTO pDTO) throws Exception;

    UserInfoDTO searchUserIdOrPasswordProc(UserInfoDTO pDTO);


    int newPasswordProc(UserInfoDTO pDTO);

    UserInfoDTO passwordEmailAuthProc(UserInfoDTO pDTO) throws Exception;
}
