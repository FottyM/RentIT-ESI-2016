package com.rentit.maintenance.infrastructure.repository;

import static com.mysema.query.group.GroupBy.*;
import com.mysema.query.jpa.impl.JPAQuery;
import com.rentit.inventory.domain.model.PlantInventoryEntry;
import com.rentit.maintenance.domain.model.MaintenanceTask;
import com.rentit.maintenance.domain.model.QMaintenancePlan;
import com.rentit.maintenance.domain.model.QMaintenanceTask;
import com.rentit.maintenance.domain.model.TypeOfWork;
import com.rentit.maintenance.domain.repository.CustomMaintenanceRepository;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MaintenanceRepositoryImpl implements CustomMaintenanceRepository {
    @Autowired
    EntityManager em;

    QMaintenancePlan plan = QMaintenancePlan.maintenancePlan;
    QMaintenanceTask task = QMaintenanceTask.maintenanceTask;

    public List<Pair<Integer, Integer>> numberOfCorrectiveRepairsPerYear(Integer numberOfYears) {
        return new JPAQuery(em)
                .from(plan, task)
                .where(plan.tasks.contains(task.id), task.typeOfWork.eq(TypeOfWork.CORRECTIVE))
                .groupBy(plan)
                .list(plan.yearOfAction, task.count())
                // The following lines are to convert List<Tuple> to List<Pair<Integer, BigDecimal>>
                .stream()
                .map(t -> Pair.of(t.get(0, Integer.class), t.get(1, Integer.class)))
                .collect(Collectors.toList());
    }

    public List<Pair<Integer, BigDecimal>> costOfCorrectiveRepairsPerYear(Integer numberOfYears) {
        return new JPAQuery(em)
                .from(plan, task)
                .where(plan.tasks.contains(task.id), task.typeOfWork.eq(TypeOfWork.CORRECTIVE))
                .groupBy(plan)
                .list(plan.yearOfAction, task.price.sum())
                // The following lines are to convert List<Tuple> to List<Pair<Integer, BigDecimal>>
                .stream()
                .map(t -> Pair.of(t.get(0, Integer.class), t.get(1, BigDecimal.class)))
                .collect(Collectors.toList());
    }
}
