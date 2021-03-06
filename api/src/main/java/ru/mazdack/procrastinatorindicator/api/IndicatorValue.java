package ru.mazdack.procrastinatorindicator.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "indicator_history")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString(exclude = "indicator")
public class IndicatorValue {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private LocalDate date;
    private Integer value;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="indicator_id")
    @JsonIgnore
    private Indicator indicator;

  public IndicatorValue(Indicator indicator, LocalDate date, Integer value) {
    this.indicator = indicator;
    this.date = date;
    this.value = value;
  }
}
