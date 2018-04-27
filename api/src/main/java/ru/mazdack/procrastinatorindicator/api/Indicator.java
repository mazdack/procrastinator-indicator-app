package ru.mazdack.procrastinatorindicator.api;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Indicator {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long id;

  private String name;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "indicator", fetch = FetchType.EAGER)
  @Builder.Default
  private List<IndicatorValue> history = new ArrayList<>();

  public void addValue(LocalDate date, int value) {
    this.history.add(new IndicatorValue(this, date, value));
  }
}
