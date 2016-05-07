package com.rentit.inventory.infrastructure.repository;

import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.rentit.common.domain.model.BusinessPeriod;
import com.rentit.inventory.domain.model.*;
import com.rentit.inventory.domain.repository.CustomInventoryRepository;
import com.rentit.maintenance.domain.model.QMaintenanceTask;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.rentit.inventory.domain.repository.InventorySpecifications.*;

public class InventoryRepositoryImpl implements CustomInventoryRepository {
    @Autowired
    EntityManager em;

    QPlantInventoryEntry entry = QPlantInventoryEntry.plantInventoryEntry;
    QPlantInventoryItem item = QPlantInventoryItem.plantInventoryItem;
    QPlantReservation reservation = QPlantReservation.plantReservation;
    QMaintenanceTask maintTask = QMaintenanceTask.maintenanceTask;


    public List<Pair<PlantInventoryEntry, Integer>> findNumberOfAvailable(String name, BusinessPeriod period) {
        return new JPAQuery(em)
                .from(entry, item)
                .where(item.plantInfo.eq(entry.id)
                        .and(plantNameContains(name)
                        .and(entryHasAvailableItemFor(period, EquipmentCondition.SERVICEABLE)))
                ).groupBy(entry)
                .list(entry, item.count())
                // The following lines are to convert List<Tuple> to List<Pair<PlantInventoryEntry, Integer>>
                .stream()
                .map(t -> Pair.of(t.get(0, PlantInventoryEntry.class), t.get(1, Integer.class)))
                .collect(Collectors.toList());
    }

    public List<PlantInventoryEntry> findAvailablePlants(String name, BusinessPeriod period) {
        return new JPAQuery(em)
                .from(entry, item)
                .where(item.plantInfo.eq(entry.id)
                        .and(plantNameContains(name)
                                .and(entryHasAvailableItemFor(period, EquipmentCondition.SERVICEABLE)))
                ).distinct().list(entry);
    }

    public List<PlantInventoryItem> findAvailableInventoryItems(String name, BusinessPeriod period) {
        return new JPAQuery(em)
                .from(entry, item)
                .where(item.plantInfo.eq(entry.id)
                        .and(item.condition.eq(EquipmentCondition.SERVICEABLE).and(plantInvItemIsAvailableFor(period)))
                        .and(plantNameContains(name))
                ).distinct().list(item);
    }

    public boolean checkIfAvailableFor(PlantInventoryEntry plant, BusinessPeriod period) {
        return new JPAQuery(em)
                .from(item)
                .where(item.plantInfo.eq(plant.getId())
                        .and(plantInvItemIsAvailableFor(period))
                        .and(item.condition.eq(EquipmentCondition.SERVICEABLE))
                ).exists();
    }

    public boolean checkIfPossiblyAvailableFor(PlantInventoryEntry plant, BusinessPeriod period) {
        return new JPAQuery(em)
                .from(item, reservation, maintTask)
                .where(item.plantInfo.eq(plant.getId()).and(plantInvItemIsAvailableFor(period)),
                        item.condition.eq(EquipmentCondition.SERVICEABLE).or(
                                reservation.plant.eq(item.id).and(maintTask.reservation.eq(reservation.id))
                                        .and(reservation.schedule.endDate.before(period.getStartDate().minusWeeks(1)))
                                        .and(reservation.schedule.endDate.after(LocalDate.now().plusWeeks(2)))
                        )
                ).exists();
    }

    public List<PlantInventoryItem> notHiredForMonths(Integer numberOfMonths) {
        return new JPAQuery(em)
                .from(item)
                .where(item.id.notIn(
                        new JPASubQuery()
                                .from(reservation)
                                .where(reservation.schedule.endDate.after(LocalDate.now().minusMonths(numberOfMonths)))
                                .list(reservation.plant)))
                .list(item);
    }
}
