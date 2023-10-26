package kopo.poly.service.impl;

import kopo.poly.dto.OcrDTO;
import kopo.poly.persistance.mapper.IOcrMapper;
import kopo.poly.service.IOcrService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;


//BufferdOutStream 쓰자~~~ 버퍼 안쓰면 나중에 에러날수 있음~~~
@Service
@Slf4j
@RequiredArgsConstructor
public class OcrService implements IOcrService {
    @Value("${ocr.model.data}")
    private String ocrModel;

    private final IOcrMapper ocrMapper;

    @Override
    public OcrDTO getReadForImageText(OcrDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getReadforImageText START!!!!!!!!!!");

        File imageFile = new File(
                CmmUtil.nvl(pDTO.getFilePath()) + "//" + CmmUtil.nvl(pDTO.getFileName())
        );
        log.info("imageFile : " + imageFile.getPath());

        ITesseract instance = new Tesseract();

        instance.setDatapath(ocrModel);

        instance.setLanguage("kor");

        String result = instance.doOCR(imageFile);

        pDTO.setTextFromImage(result);
        log.info("result : " + result);

        int insertResult = insertOcrIntoOCR_TABLE(pDTO);
        if(insertResult == 1 )
            log.info("insert 성공");

        log.info(this.getClass().getName() + ".getReadforImageText END!!!!!!!!!!");
        return pDTO;
    }

    private int insertOcrIntoOCR_TABLE(OcrDTO pDTO) {
        ocrMapper.insertOcr(pDTO);
        return 0;
    }

}
