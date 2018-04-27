package ru.mazdack.procrastinatorindicator.api;

import static java.lang.Math.toIntExact;
import java.time.LocalDate;
import java.time.Month;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import java.time.temporal.ValueRange;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class IndicatorsControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private IndicatorRepository indicatorRepository;

  @Before
  public void setUp() throws Exception {
    indicatorRepository.deleteAll();
  }

  @Test
  public void getIndicatorsSmoke() throws Exception {
    this.mockMvc.perform(get("/indicators").param("year", "2018").param("month", "1")).andExpect(status().isOk()).andExpect(content().string(containsString("[]")));
  }

  @Test
  public void testGetIndicatorsReturnsHistoryForSpecificMonth() throws Exception {
    Indicator indicator = indicatorRepository.save(Indicator.builder().name("TEST").build());
    int year = 2018;
    Month month = Month.APRIL;

    LocalDate date = LocalDate.of(year, month, 1);
    ValueRange daysRange = date.range(DAY_OF_MONTH);

    LongStream
      .range(daysRange.getMinimum(), daysRange.getMaximum() + 1)
      .forEach(day -> indicator.addValue(LocalDate.of(year, month, toIntExact(day)), toIntExact(day * 10)));

    indicatorRepository.save(indicator);

    this.mockMvc.perform(get("/indicators").param("year", String.valueOf(year)).param("month", String.valueOf(month.getValue())))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].name", is("TEST")))
      .andExpect(jsonPath("$[0].history", hasSize(Math.toIntExact(daysRange.getMaximum()))));
  }
}
