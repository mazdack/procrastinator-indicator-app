package ru.mazdack.procrastinatorindicator.api;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndicatorService {
  @Autowired
  private IndicatorRepository indicatorRepository;

  public List<Indicator> findByYearAndMonth(Integer year, Integer month) {
    LocalDate startDate = LocalDate.of(year, month, 1);
    LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

    List<Indicator> all = indicatorRepository.findAll();

    all.forEach(indicator -> indicator.setHistory(indicator.getHistory().stream().filter(item -> !item.getDate().isAfter(endDate) && !item.getDate().isBefore(startDate)).collect(Collectors.toList())));

    return all;
  }
}
