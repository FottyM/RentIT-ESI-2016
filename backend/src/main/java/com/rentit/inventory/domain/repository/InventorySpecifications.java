package com.rentit.inventory.domain.repository;

import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.types.expr.BooleanExpression;
import com.rentit.common.domain.model.BusinessPeriod;
import com.rentit.inventory.domain.model.*;

public class InventorySpecifications {
    static QPlantInventoryItem plantInvItem = QPlantInventoryItem.plantInventoryItem;
    static QPlantInventoryEntry plantInvEntry = QPlantInventoryEntry.plantInventoryEntry;
    static QPlantReservation reservation = QPlantReservation.plantReservation;


    public static BooleanExpression plantInvItemIsAvailableFor(BusinessPeriod period) {
        return plantInvItem.id.notIn(new JPASubQuery()
                .from(reservation)
                .where(reservation.schedule.endDate.goe(period.getStartDate()),
                        reservation.schedule.startDate.loe(period.getEndDate()))
                .list(reservation.plant));
    }

    public static BooleanExpression entryHasAvailableItemFor(BusinessPeriod period, EquipmentCondition condition) {
        return plantInvEntry.id.in(new JPASubQuery().from(plantInvItem)
                .where(plantInvItemIsAvailableFor(period).and(plantInvItem.condition.eq(condition)))
                .list(plantInvItem.plantInfo));
    }

    public static BooleanExpression plantNameContains(String keyword) {
        return plantInvEntry.name.lower().contains(keyword.toLowerCase());
    }
}