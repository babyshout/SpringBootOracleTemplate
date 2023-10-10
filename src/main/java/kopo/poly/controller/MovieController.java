package kopo.poly.controller;

import kopo.poly.dto.MovieDTO;
import kopo.poly.service.IMovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequestMapping(value = "/movie")
@RequiredArgsConstructor
@Controller
public class MovieController {

    private final IMovieService movieService;


    /**
     * CGV 영화 수집을 위한 URL 호출
     */
    @GetMapping(value = "collectMovieRank")
    public String collectMovieRank(ModelMap model) {
        log.info(this.getClass().getName() + ".collectMovieRank START!!!!!!!!!!!!!!!!!");

        int res = 0;
        try {
            res = movieService.collectMovieRank();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        model.addAttribute("msg", "CGV 홈페이지로부터 수집된 영화는 총 " + res + "건입니다.");


        log.info(this.getClass().getName() + ".collectMovieRank END!!!!!!!!!!!!!!!!!");

        return "/movie/collectMovieRank";
    }


    @GetMapping(value = "getMovieRank")
    public String getMovieRank(
            HttpServletRequest request,
            ModelMap model) {
        log.info(this.getClass().getName() + ".getMovieRank START!!!!!!!!!!!!!!!");

        List<MovieDTO> rList = null;
        try {
            rList = Optional.ofNullable(
                    movieService.getMovieInfoList()
            ).orElseGet(ArrayList::new);
        } catch (Exception e) {
            rList = new ArrayList<>();

            // return "오류 페이지"

            throw new RuntimeException(e);


        }

        model.addAttribute("rList", rList);

        log.info(this.getClass().getName() + ".getMovieRank END!!!!!!!!!!!!!!!");

        return "/movie/movieList";
    }
}
