package ru.mazdack.procrastinatorindicator.api;

import java.util.List;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
  "spring.jpa.properties.hibernate.show_sql=true",
  "spring.jpa.properties.hibernate.format_sql=true"
})
public class DataBaseLoaderTest {

  @Autowired
  private DataBaseLoader dataBaseLoader;
  @Autowired
  private IndicatorRepository indicatorRepository;
  @Autowired
  private IndicatorValueRepository indicatorValueRepository;

  @After
  public void tearDown() {
    indicatorRepository.deleteAll();
    indicatorValueRepository.deleteAll();
  }

  @Test
  public void createsIndicator() {
    dataBaseLoader.run();
    List<Indicator> all = indicatorRepository.findAll();

    assertFalse(all.isEmpty());
  }

  @Test
  public void createsIndicatorWithValue() {
    dataBaseLoader.run();
    List<Indicator> all = indicatorRepository.findAll();

    assertFalse(all.isEmpty());
    assertFalse(all.get(0).getHistory().isEmpty());
  }
}
