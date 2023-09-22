package kopo.poly.controller;

import kopo.poly.dto.MsgDTO;
import kopo.poly.dto.UserInfoDTO;
import kopo.poly.service.IUserInfoService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequestMapping(value = "/user")
@RequiredArgsConstructor
@Controller
public class UserInfoController {

    private final IUserInfoService userInfoService;

    @GetMapping(value = "userRegForm")
    public String userRegForm() {
        log.info(this.getClass().getName() + ".user/userRegForm START!!!!!!!!!");

        return "/user/userRegForm";
    }

    @ResponseBody
    @PostMapping(value = "getUserIdExists")
    public UserInfoDTO getUserExists(HttpServletRequest request) throws Exception {
        log.info(this.getClass().getName() + ".getUserIdExists START!!!!!!!!!!!!!");
        log.info(this.getClass().getName() + ".getUserIdExists START!!!!!!!!!!!!!");

        String userId = CmmUtil.nvl(request.getParameter("userId"));

        log.info("userId : " + userId);

        UserInfoDTO pDTO = new UserInfoDTO();
        pDTO.setUserId(userId);

        UserInfoDTO rDTO = Optional.ofNullable(
                userInfoService.getUserIdExists(pDTO)
        ).orElseGet(UserInfoDTO::new);


        return rDTO;
    }


    @ResponseBody
    @PostMapping(value = "getEmailExists")
    public UserInfoDTO getEmailExists(HttpServletRequest request) throws Exception {
        log.info(this.getClass().getName() + ".getEmailExists START!!!!!!!!!!!");

        String email = CmmUtil.nvl(request.getParameter("email"));

        log.info("email : " + email);

        UserInfoDTO pDTO = new UserInfoDTO();
        pDTO.setEmail(EncryptUtil.encAES128CBC(email));

        UserInfoDTO rDTO = Optional.ofNullable(
                userInfoService.getEmailExists(pDTO)
        ).orElseGet(UserInfoDTO::new);

        log.info(this.getClass().getName() + ".getEmailExists END!!!!!!!!!!!");
        return rDTO;
    }

    @ResponseBody
    @PostMapping(value = "insertUserInfo")
    public MsgDTO insertUserInfo(HttpServletRequest request) throws Exception {
        log.info(this.getClass().getName() + ".insertuserInfo START!!!!!!!!!");

        int res = 0;
        String msg = "";
        MsgDTO dto = null;

        UserInfoDTO pDTO = null;

        try {
            String userId = CmmUtil.nvl(request.getParameter("userId"));
            String userName = CmmUtil.nvl(request.getParameter("userName"));
            String password = CmmUtil.nvl(request.getParameter("password"));
            String email = CmmUtil.nvl(request.getParameter("email"));
            String addr1 = CmmUtil.nvl(request.getParameter("addr1"));
            String addr2 = CmmUtil.nvl(request.getParameter("addr2"));

            log.info("userId : " + userId);
            log.info("userName : " + userName);
            log.info("password : " + password);
            log.info("email : " + email);
            log.info("addr1 : " + addr1);
            log.info("addr2 : " + addr2);


            pDTO = new UserInfoDTO();

            pDTO.setUserId(userId);
            pDTO.setUserName(userName);

            pDTO.setPassword(EncryptUtil.encHashSHA256(password));

            pDTO.setEmail(EncryptUtil.encAES128CBC(email));
            pDTO.setAddr1(addr1);
            pDTO.setAddr2(addr2);


            res = userInfoService.insertUserInfo(pDTO);

            log.info("회원가입 결과(res) : " + res);

            if (res == 1) {
                msg = "회원가입 되었습니다";
            } else if (res == 2) {
                msg = "이미 가입된 아이디입니다.";
            } else {
                msg = "오류로 인해 회원가입이 실패하였습니다.";
            }


        } catch (Exception e) {
            msg = "실패하였습니다. : " + e;
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            dto = new MsgDTO();
            dto.setResult(res);
            dto.setMsg(msg);

            log.info(this.getClass().getName() + ".insertUserInfo END!!!!!!!!!!");
        }

        return dto;
    }


    @GetMapping(value = "userInfoList")
    public String userInfoList(
            HttpServletRequest request,
            ModelMap model
    ) throws Exception {
        log.info(this.getClass().getName() + ".userInfoList START!!!!!!!!!!!!!");

        List<UserInfoDTO> rList = userInfoService.getUserInfoList();

        for (UserInfoDTO dto :
                rList) {
            dto.setEmail(EncryptUtil.decAES128CBC(CmmUtil.nvl(dto.getEmail())));
            log.info(dto.getEmail());
        }

        model.addAttribute("rList", rList);


        log.info(this.getClass().getName() + ".userInfoList END!!!!!!!!!!!!!");
        return "user/userInfoList";
    }

    @GetMapping(value = "userInfoDetail")
    public String userInfoDetail(
            HttpServletRequest request,
            Model model
    ) throws Exception {
        log.info(this.getClass().getName() + ".userInfoDTO() START!!!!!!!!!!!!!!!!!!!!");

        String requestedUserId = request.getParameter("uId");

        UserInfoDTO pDTO = new UserInfoDTO();
        pDTO.setUserId(requestedUserId);

        UserInfoDTO rDTO = Optional.ofNullable(
                userInfoService.getUserInfo(pDTO)
        ).orElseGet(UserInfoDTO::new);

        rDTO.setEmail(
                EncryptUtil.decAES128CBC(
                        CmmUtil.nvl(rDTO.getEmail())
                )
        );

        log.info(rDTO.toString());

        model.addAttribute("rDTO", rDTO);

        log.info(this.getClass().getName() + ".userInfoDTO() END!!!!!!!!!!!!!!!!!!!!");
        return "user/userInfoDetail";
    }


    @GetMapping(value = "login")
    public String login() {
        log.info(this.getClass().getName() + ".user/login START!!!!!!!!!!!!!");

        log.info(this.getClass().getName() + ".user/login END!!!!!!!!!!!!!");
        return "user/login";
    }

    @ResponseBody
    @PostMapping(value = "loginProc")
    public MsgDTO loginProc(
            HttpServletRequest request, HttpSession session
    ) {
        log.info(this.getClass().getName() + ".loginProc START!!!!!!!!!!!!!!!!!!!!!!!!!!");

        int res = 0;
        String msg = "";
        MsgDTO dto = null;

        UserInfoDTO pDTO = null;

        try {
            String userId = CmmUtil.nvl(request.getParameter("userId"));
            String password = CmmUtil.nvl(request.getParameter("password"));

            pDTO = new UserInfoDTO();

            pDTO.setUserId(userId);
            pDTO.setPassword(
                    EncryptUtil.encHashSHA256(password)
            );

            log.info("pDTO : " + pDTO.toString());

            UserInfoDTO rDTO = userInfoService.getLoginInfo(pDTO);

            if (CmmUtil.nvl(
                    rDTO.getUserId()).length() > 0
            ) {
                res = 1;

                msg = "로그인이 성공했습니다";

                /**
                 * TODO
                 * session.setAttribute(SessionEnum.SS_USER_ID.STRING, userId);
                 * session.setAttribute(SessionEnum.SS_USER_NAME.STRING, rDTO.getUserName());
                 */

                session.setAttribute("SS_USER_ID", userId);
                session.setAttribute("SS_USER_NAME", rDTO.getUserName());
            } else {
                msg = "아이디와 비밀번호가 올바르지 않습니다";
            }


        } catch (Exception e) {
            msg = "시스템 문제로 로그인이 실패했습니다.";
            res = 2;
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            dto = new MsgDTO();
            dto.setResult(res);
            dto.setMsg(msg);

            log.info("dto : " + dto.toString());

            log.info(this.getClass().getName() + ".loginProc END!!!!!!!!!!!!");
        }

        log.info(this.getClass().getName() + ".loginProc START!!!!!!!!!!!!!!!!!!!!!!!!!!");


        return dto;
    }

    @GetMapping(value = "loginResult")
    public String loginResult(
            HttpServletRequest request,
            HttpSession session
    ) {
        return "user/loginResult";
    }


    /**
     * FIXME
     * 여기서 사용할수 있는 방법 3가지
     * 1. 정석적으로 model 에 값 넣어주기
     * 2. JSP 단에서 서비스 호출 해보기
     * 3. ajax 로 비동기통신 보내서 UserInfoDTO 값 가져오기
     *
     * @param request
     * @param session
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "profile")
    public String userInfoDetail(
            HttpServletRequest request,
            HttpSession session,
            Model model
    ) throws Exception {
        log.info(this.getClass().getName() + "/user/userInfoDetail START!!!!!!!!!!!!!!!!!!!!!!!");

        String ssUserId = Optional.ofNullable(
                (String) session.getAttribute("SS_USER_ID")
        ).orElseGet(String::new);

        if (ssUserId.equals("")) {
            log.info("SS_USER_ID is null! " + ssUserId);
            return "user/login";
        } else {
            log.info("SS_USER_ID is not null! " + ssUserId);

            UserInfoDTO pDTO = new UserInfoDTO();
            pDTO.setUserId(ssUserId);

            UserInfoDTO rDTO = userInfoService.getUserInfo(pDTO);

            model.addAttribute("rDTO", rDTO);


            model.addAttribute("userInfoService", userInfoService);

            log.info(this.getClass().getName() + "/user/userInfoDetail END!!!!!!!!!!!!!!!!!!!!!!!");
            return "user/profile/profileJSP";
        }


    }


}
