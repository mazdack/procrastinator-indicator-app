package ru.mazdack.procrastinatorindicator.api;

import static org.hamcrest.CoreMatchers.containsString;
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
  public void testGetIndicators() throws Exception {
    this.mockMvc.perform(get("/indicators")).andExpect(status().isOk()).andExpect(content().string(containsString("[]")));
  }
}
