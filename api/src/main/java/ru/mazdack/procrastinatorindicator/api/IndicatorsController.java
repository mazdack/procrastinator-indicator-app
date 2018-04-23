package ru.mazdack.procrastinatorindicator.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/indicators")
@CrossOrigin(maxAge = 3600)
public class IndicatorsController {
  @Autowired
  private IndicatorRepository indicatorRepository;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Iterable<Indicator> getIndicators() {
    return indicatorRepository.findAll();
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public void saveIndicator(@RequestBody Indicator indicator) {
    System.out.println(indicator);
    return;
  }
}
