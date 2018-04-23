package ru.mazdack.procrastinatorindicator.api;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "indicator_history")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class IndicatorValue {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private LocalDateTime date;
    private Integer value;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="indicator_id")
    Indicator indicator;

}
