package kopo.poly.persistance.mapper;

import kopo.poly.dto.OcrDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IOcrMapper {
    public List<OcrDTO> getOcrList();

    public List<OcrDTO> insertOcr(OcrDTO pDTO);
}
