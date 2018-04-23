package ru.mazdack.procrastinatorindicator.api;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataBaseLoader implements CommandLineRunner {
  @Autowired
  private IndicatorRepository indicatorRepository;
  @Override
  public void run(String... args) {
    indicatorRepository.save(Indicator.builder().name("First indicator").history(Collections.emptyList()).build());
  }
}
