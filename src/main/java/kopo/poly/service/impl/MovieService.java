package kopo.poly.service.impl;

import kopo.poly.dto.MovieDTO;
import kopo.poly.persistance.mapper.IMovieMapper;
import kopo.poly.service.IMovieService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MovieService implements IMovieService {

    private final IMovieMapper movieMapper;

    /**
     * 웹상(CGV 홈페이지) 에서 영화 순위정보 가져오기
     *
     * @return
     * @throws Exception
     */
    @Transactional
    @Override
    public int collectMovieRank() throws Exception {
        log.info(this.getClass().getName() + ".collectMovieRank START!!!!!!!!!!!!!!!!");

//        DateTimeFormatter dateTimeFormatter = DateTimeFo;
        String collectTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        log.info("collectTIme : " + collectTime);

        MovieDTO pDTO = new MovieDTO();
        pDTO.setCollectTime(collectTime);


        // 동일한 날짜에 기존 테이블에 데이터가 들어가있으면,,, 삭제

        movieMapper.deleteMovieInfo(pDTO);


        //기존 등록된 영화 순위 삭제 후, pDTO 값 제거하기

        pDTO = null;
        /**
         * 크롤링 결과 (0보다 크면 크롤링 성공)
         */
        int res = 0;


        // CGV 영화 순위 정보 가져올 사이트 주소
        String url = "http://www.cgv.co.kr/movies/";

        // jsoup 라이브러리를 통해 사이트 접속되면, 그 사이트의 전체 HTML 소스 저장할 변수
//        Document doc = null;

        final Document doc = Jsoup.connect(url).get();

        /*
          CGV 웹페이지의 전체 소스 중 일부 태그를 선택하기 위해 사용
          <div class="sect-movie-chart"> 이 태그 내에서 있는 HTML 소스만 element 에 저장됨
         */
        Elements element = doc.select("div.sect-movie-chart");

//        element.forEach(e -> {
//            log.info("");
//            log.info("");
//        });


        /*
        Iterator 를 사용하여 영화 순위 정보를 가져오기
        영화 순위는 기본적으로 1개 이상의 영화가 존재하기 때문에, 태그의 반복이 존재할 수 밖에 없음
         */

        Iterator<Element> movie_rank = element.select("strong.rank").iterator();
        Iterator<Element> movie_name = element.select("strong.title").iterator();
        Iterator<Element> movie_reserve = element.select("strong.percent span").iterator();
        Iterator<Element> score = element.select("span.percent").iterator();
        Iterator<Element> open_day = element.select("span.txt-info").iterator();


        log.info(String.valueOf(element.select("egg-page.percent")));

        while (movie_rank.hasNext()) {
            pDTO = new MovieDTO();

            pDTO.setCollectTime(collectTime);


            String rank = CmmUtil.nvl(movie_rank.next().text()).trim();
            pDTO.setMovieRank(rank.substring(3, rank.length()));

            pDTO.setMovieNm(CmmUtil.nvl(
                    movie_name.next().text()).trim()
            );

            pDTO.setMovieReserve(
                    CmmUtil.nvl(movie_reserve.next().text()).trim()
            );

            pDTO.setScore(
                    CmmUtil.nvl(score.next().text()).trim()
            );

            pDTO.setOpenDay(CmmUtil.nvl(open_day.next().text()).trim().substring(0, 10));

            pDTO.setRegId("admin");
            pDTO.setChgId("admin");

            log.info("pDTO : " + pDTO.toString());
            res += movieMapper.insertMovieInfo(pDTO);



        }


        log.info(this.getClass().getName() + ".collectMovieRank END!!!!!!!!!!!!!!!!");
        return res;
    }

    /**
     * 수집된 내용을 조회하기
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<MovieDTO> getMovieInfoList() throws Exception {
        log.info(this.getClass().getName() + ".getMovieInfoList() START!!!!!!!!!!!!");


        log.info(this.getClass().getName() + ".getMovieInfoList() END!!!!!!!!!!!!");
        return null;
    }
}
