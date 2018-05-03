package ru.mazdack.procrastinatorindicator.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import static java.lang.Math.toIntExact;
import java.time.LocalDate;
import java.time.Month;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import java.time.temporal.ValueRange;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import org.hibernate.Hibernate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class IndicatorsControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private IndicatorRepository indicatorRepository;
  @Autowired
  private ObjectMapper objectMapper;

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
    Indicator indicator = createAndSaveTestIndicator();
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
      .andExpect(jsonPath("$[0].name", is(indicator.getName())))
      .andExpect(jsonPath("$[0].history", hasSize(Math.toIntExact(daysRange.getMaximum()))));
  }

  @Test
  public void testCreateIndicator() throws Exception {
    Indicator indicator = createTestIndicator();

    String contentAsString = this.mockMvc.perform(post("/indicators").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(indicator)))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.name", is(indicator.getName())))
      .andExpect(jsonPath("$.id", is(notNullValue())))
      .andReturn().getResponse().getContentAsString();

    indicator = objectMapper.readValue(contentAsString, Indicator.class);
    assertNotNull(indicatorRepository.getOne(indicator.getId()));
  }

  @Test
  @Transactional
  public void testAddIndicatorValue() throws Exception {
    Long indicatorId = createAndSaveTestIndicator().getId();

    IndicatorValue indicatorValue = IndicatorValue.builder().date(LocalDate.of(2018, 4, 20)).value(10).build();

    this.mockMvc.perform(post("/indicators/{id}", indicatorId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(indicatorValue)))
      .andExpect(status().isOk());

    Indicator indicator = indicatorRepository.getOne(indicatorId);
    assertEquals(1, indicator.getHistory().size());
    assertEquals(indicatorValue.getValue(), indicator.getHistory().get(0).getValue());
    assertEquals(indicatorValue.getDate(), indicator.getHistory().get(0).getDate());
  }

  @Test
  @Ignore
  public void testChangeIndicator() {

  }

  @Test
  @Ignore
  public void testChangeIndicatorValue() {

  }

  private Indicator createAndSaveTestIndicator() {
    return indicatorRepository.save(createTestIndicator());
  }

  private Indicator createTestIndicator() {
    String name = "TEST INDICATOR";
    return Indicator.builder().name(name).build();
  }
}
