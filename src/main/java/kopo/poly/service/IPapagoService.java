package kopo.poly.service;

import kopo.poly.dto.PapagoDTO;

public interface IPapagoService {
    /**
     * papago 언어감지 api
     */
    String detectLangsApiURL = "https://openapi.naver.com/v1/papago/detectLangs";

    /**
     * papago 번역 api
     */
    String translateApiURL = "https://openapi.naver.com/v1/papago/n2mt";

    /**
     * 네이버 파파고 API 를 호출하여 입력된 언어가 어느나라 언어인지 찾기
     */
    PapagoDTO detectLangs(PapagoDTO pDTO) throws Exception;

    /**
     * 네이버 Papago API를 호출하여 언언감지 후, 번역하기
     */
    PapagoDTO translate(PapagoDTO pDTO) throws Exception;
}
