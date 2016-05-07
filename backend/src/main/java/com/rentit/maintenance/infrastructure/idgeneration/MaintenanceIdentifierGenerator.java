package com.rentit.maintenance.infrastructure.idgeneration;

import com.rentit.common.infrastructure.HibernateBasedIdentifierGenerator;
import com.rentit.maintenance.domain.model.MaintenancePlanID;
import com.rentit.maintenance.domain.model.MaintenanceTaskID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaintenanceIdentifierGenerator {
    @Autowired
    HibernateBasedIdentifierGenerator hibernateGenerator;

    public MaintenancePlanID nextMaintenancePlanID() {
        return MaintenancePlanID.of(hibernateGenerator.getID("MaintenancePlanIDSequence"));
    }

    public MaintenanceTaskID nextMaintenanceTaskID() {
        return MaintenanceTaskID.of(hibernateGenerator.getID("MaintenanceTaskIDSequence"));
    }
}
