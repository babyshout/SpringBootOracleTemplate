package kopo.poly.controller;

import kopo.poly.dto.OcrDTO;
import kopo.poly.persistance.mapper.IOcrMapper;
import kopo.poly.service.IOcrService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import kopo.poly.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequestMapping(value = "/ocr")
//@RequiredArgsConstructor
@Controller
public class OcrController {
    //    @Autowired
    private final IOcrService ocrService;

    private final IOcrMapper ocrMapper;

    /**
     * 서버에서 저장소 역할하는 위치는 정해져 있어가지고 해당 위치 절대경로 쓰거나,
     * 나중에 파일 저장하는 url 위치 쓰거나~~, 해가지고
     *
     */
    final private String FILE_UPLOAD_SAVE_PATH = "c:/upload";

    public OcrController(IOcrService ocrService, IOcrMapper ocrMapper) {
        this.ocrService = ocrService;
        this.ocrMapper = ocrMapper;
    }

    @GetMapping(value = "uploadImage")
    public String uploacImage() {
        log.info(this.getClass().getName() + ".uploadImage() START just showing the html page");
        return "ocr/uploadImage";
    }

    @PostMapping(value = "readImage")
    public String readImage(
            Model model,
            @RequestParam(value = "fileUpload") MultipartFile uploadedMultipartFile
    ) throws Exception {
        log.info(this.getClass().getName() + ".readImage START!!!!!!! reading image (OCR) and put model it");

        log.trace("trace test~~~~~");
        log.debug("debug test~~~~~~~~~~~~");
        log.warn("warn test~~~~~~~~~");

        String res = "";

        String originalFileName = uploadedMultipartFile.getOriginalFilename();

        assert originalFileName != null;
        String ext = originalFileName.substring(
                originalFileName.lastIndexOf(".") + 1
        ).toLowerCase();
//        String ext = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();

        if (!isValidImage(ext)) {
            res = "이미지 파일이 아니라서 인식이 불가능 합니다";
            model.addAttribute("res", res);
            log.trace("ext is not image FILE RETURN!!!!!!");
            return "/ocr/readImage";

//            throw new RuntimeException();
        }

        String saveFileName = DateUtil.getDateTime("HHmmss") + "." + ext;
        log.info("saveFilaName : " + saveFileName);
        String saveFilePath = FileUtil.mkdirForDate(FILE_UPLOAD_SAVE_PATH);
        log.info("saveFilePath : " + saveFilePath);

        String fullFileInfo = saveFilePath + "/" + saveFileName;
        log.info("fullFileInfo : " + fullFileInfo);

        uploadedMultipartFile.transferTo(
                new File(fullFileInfo)
        );


        /**
         * OcrDTO 시작
         */
        OcrDTO pDTO = new OcrDTO();

        pDTO.setFileName(saveFileName);
        pDTO.setFilePath(saveFilePath);
        pDTO.setExt(ext);
        pDTO.setOriginalFileName(originalFileName);
        pDTO.setFileName(saveFileName);

        OcrDTO rDTO = Optional.ofNullable(
                ocrService.getReadForImageText(pDTO)
        ).orElseGet(OcrDTO::new);

        res = CmmUtil.nvl(rDTO.getTextFromImage());

        rDTO = null;
        pDTO = null;

        model.addAttribute("res", res);
        log.info(this.getClass().getName() + ".readImage SUCCESSFULLY END!!!!!!!");

        return "ocr/readImage";
    }

    @GetMapping(value = "ocr-list")
    public String ocrList(
            HttpServletRequest request,
            Model model,
            HttpSession session
    ) {
        log.info(this.getClass().getName() + ".ocrList() START!!!!!!!");

        List<OcrDTO> ocrListFromOCR_TABLE = ocrMapper.getOcrList();

        log.info(this.getClass().getName() + ".ocrList() END!!!!!!!");
        return "/ocr/ocr-list";
    }

    private boolean isValidImage(String ext) {
        return ext.equals("jpeg") || ext.equals("jpg") || ext.equals("gif") || ext.equals("png");
    }


}
