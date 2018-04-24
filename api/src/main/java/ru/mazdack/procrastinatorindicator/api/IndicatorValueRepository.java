package ru.mazdack.procrastinatorindicator.api;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface IndicatorValueRepository extends CrudRepository<IndicatorValue, Long> {
}
