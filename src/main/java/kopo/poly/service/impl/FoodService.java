package kopo.poly.service.impl;

import kopo.poly.dto.FoodDTO;
import kopo.poly.service.IFoodService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class FoodService implements IFoodService {

    @Override
    public List<FoodDTO> todayFood() throws Exception {
        log.info(this.getClass().getName() + ".toDayFood START!!!!!!!!!!!!!!!!!!");

        String url = "https://www.kopo.ac.kr/kangseo/content.do?menu=262";

        Document doc = Jsoup.connect(url).get();

        Elements elements = doc.select("table.tbl_table tbody");

        log.info("elements :\n" + elements.text());

        Iterator<Element> foodIt = elements.select("tr").iterator();

        FoodDTO pDTO = null;
        List<FoodDTO> pList = new ArrayList<>();
        int idx = 0;

        Elements trList = elements.select("tr");
        log.info("trList : " + trList.text());

        for (Element el :
                trList) {
            log.info("idx : " + idx);
            if (idx++ > 4) {
                break;
            }
            pDTO = new FoodDTO();

            String food = el.text().trim();
            log.info("food : " + food);

            pDTO.setDay(
                    CmmUtil.nvl(food.substring(0, 3))
            );

            pDTO.setFood_nm(
                    CmmUtil.nvl(food.substring(4))
            );

            log.info("pDTO : " + pDTO.toString());

            pList.add(pDTO);
        }

//        while (foodIt.hasNext()) {
//            if (idx++ > 4) {
//                break;
//            }
//
//            pDTO = new FoodDTO();
//
//            String food = CmmUtil.nvl(foodIt.next().text()).trim();
//
//            log.info("food : " + food);
//
//            pDTO.setDay(food.substring(0, 3));
//
//            pDTO.setFood_nm(food.substring(4));
//
//            pList.add(pDTO);
//        }

        log.info(this.getClass().getName() + ".toDayFood START!!!!!!!!!!!!!!!!!!");
        return pList;
    }
}
