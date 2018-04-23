package ru.mazdack.procrastinatorindicator.api;

import java.util.List;
import javax.persistence.Entity;
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

  @OneToMany(mappedBy = "indicator")
  private List<IndicatorValue> history;
}
