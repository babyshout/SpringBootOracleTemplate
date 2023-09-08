package kopo.poly.controller;

import kopo.poly.dto.MailDTO;
import kopo.poly.dto.MsgDTO;
import kopo.poly.service.IMailService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RequestMapping(value = "/mail")
@RequiredArgsConstructor
@Controller
public class MailController {

    private final IMailService mailService;

    @GetMapping(value = "mailForm")
    public String mailForm() {
        log.info(this.getClass().getName() + "mailForm Start!");

        return "/mail/mailForm";
    }

    @ResponseBody
    @PostMapping(value = "sendMail")
    public MsgDTO sendMail(HttpServletRequest request, ModelMap model) {
        log.info(this.getClass().getName() + ".sendMail start!");

        String msg = "";

        String toMail = CmmUtil.nvl(request.getParameter("toMail"));
        String title = CmmUtil.nvl(request.getParameter("title"));
        String contents = CmmUtil.nvl(request.getParameter("contents"));

        log.info("toMail : " + toMail);
        log.info("title : " + title);
        log.info("contents : " + contents);

        MailDTO pDTO = new MailDTO();

        pDTO.setToMail(toMail);
        pDTO.setTitle(title);
        pDTO.setContents(contents);


        int res = mailService.doSendMail(pDTO);

        if (res == 1) {
            msg = "메일 발송하였습니다";
        } else {
            msg = "메일 발송 실패하였습니다";
        }

        log.info(msg);

        MsgDTO dto = new MsgDTO();
        dto.setMsg(msg);


        log.info(this.getClass().getName() + ".sendMail end!");

        return dto;
    }

    @GetMapping(value = "/mailSendList")
    public String mailSendList(
            HttpServletRequest request, ModelMap model, HttpSession session) {
        log.info(this.getClass().getName() + ".mailSendList START!");

        // TODO 여기에 session 에서 유저 정보 가져와서 해당 유저 mail 만 가져오게
        List<MailDTO> rList = mailService.getMailList();

        for (MailDTO dto:
             rList) {
            log.info("get DTO : " + dto.toString());
        }

        model.addAttribute("rList", rList);



        log.info(this.getClass().getName() + ".mailSendList END!");
        return "/mail/mailSendList";
    }
}
