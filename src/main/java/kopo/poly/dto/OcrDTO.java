package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @
 */
@Getter
@Setter
@ToString
public class OcrDTO {
    private int seq;
    /**
     * ㅁㄴㅇㄹㄴㅁㅇㄹ
     */
    private String filePath;
    private String fileName;
    private String textFromImage;

    private String originalFileName;
    private String ext;
    private String regId;
    private String chgId;

    public boolean isAllValid() {
        if (
                seq < 0 || filePath == null
                        || fileName == null || textFromImage == null || originalFileName == null || ext == null || regId == null
                        || chgId == null
        )
            return false;
        return true;
    }
}
