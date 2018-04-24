package ru.mazdack.procrastinatorindicator.api;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IndicatorRepository  extends JpaRepository<Indicator, Long> {
}
