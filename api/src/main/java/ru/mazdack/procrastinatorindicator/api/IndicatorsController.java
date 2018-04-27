package ru.mazdack.procrastinatorindicator.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/indicators")
@CrossOrigin(maxAge = 3600)
public class IndicatorsController {
  @Autowired
  private IndicatorService indicatorService;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public List<Indicator> getIndicators(@RequestParam Integer year, @RequestParam Integer month) {
    return indicatorService.findByYearAndMonth(year, month);
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public void saveIndicator(@RequestBody Indicator indicator) {
    System.out.println(indicator);
    return;
  }
}
