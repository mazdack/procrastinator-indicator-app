package ru.mazdack.procrastinatorindicator.api;

import org.springframework.data.repository.CrudRepository;

public interface IndicatorRepository  extends CrudRepository<Indicator, Long> {
}
