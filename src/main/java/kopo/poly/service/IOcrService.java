package kopo.poly.service;

import kopo.poly.dto.OcrDTO;

public interface IOcrService
{
    OcrDTO getReadForImageText(OcrDTO pDTO) throws Exception;
}
