package com.rentit.maintenance.domain.repository;

import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.util.List;

public interface CustomMaintenanceRepository {
    List<Pair<Integer, Integer>> numberOfCorrectiveRepairsPerYear(Integer numberOfYears);
    List<Pair<Integer, BigDecimal>> costOfCorrectiveRepairsPerYear(Integer numberOfYears);
}
