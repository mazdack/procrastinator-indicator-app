package ru.mazdack.procrastinatorindicator.api;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IndicatorRepository  extends JpaRepository<Indicator, Long> {
}
