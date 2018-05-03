package ru.mazdack.procrastinatorindicator.api;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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

  @ResponseBody
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public Indicator saveIndicator(@RequestBody Indicator indicator) {
    return indicatorService.save(indicator);
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/{indicatorId}")
  public void addIndicatorValue(@PathVariable Long indicatorId, @RequestBody IndicatorValue indicatorValue) {
    Indicator indicator;
    try {
      indicator = indicatorService.getOne(indicatorId);
    } catch (EntityNotFoundException e) {
      throw new IndicatorNotFound(indicatorId);
    }

    indicator.addValue(indicatorValue);
    indicatorService.save(indicator);
  }

  @ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such indicator")
  private class IndicatorNotFound extends RuntimeException {
    public IndicatorNotFound(Long indicatorId) {
    }
  }
}
