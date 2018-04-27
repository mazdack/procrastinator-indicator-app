package ru.mazdack.procrastinatorindicator.api;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataBaseLoader implements CommandLineRunner {
  @Autowired
  private IndicatorRepository indicatorRepository;

  @Override
  public void run(String... args) {
    Indicator firstIndicator = Indicator.builder()
      .name("First indicator")
      .history(new ArrayList<>())
      .build();
    LocalDate date = LocalDate.of(2018, 4, 10);
    firstIndicator.addValue(date, 10);
    indicatorRepository.save(firstIndicator);
  }
}
