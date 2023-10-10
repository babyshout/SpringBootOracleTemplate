package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MovieDTO {
    /**
     * 수집 시간
     */
    private String collectTime;
    /**
     *
     */
    private String seq;
    private String movieRank;
    private String movieNm;
    private String movieReserve;
    private String score;
    private String openDay;
    private String regId;
    private String regDt;
    private String chgId;
    private String chgDt;
}
