package ru.mazdack.procrastinatorindicator.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/indicators")
@CrossOrigin(maxAge = 3600)
public class IndicatorsController {
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public List<Indicator> getIndicators() {
    Indicator jedaiIndicator = new Indicator("Текущая страница книги Джедайские техники", new ArrayList<>(Collections.nCopies(30, 0)));
//    return jedaiIndicator;
    return Collections.singletonList(jedaiIndicator);
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  private class Indicator {
    private String name;
    private List<Integer> history;
  }
}
