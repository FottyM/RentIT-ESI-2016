package com.rentit.maintenance.domain.repository;

import com.rentit.maintenance.domain.model.MaintenancePlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintenanceRepository extends JpaRepository<MaintenancePlan, Long>, CustomMaintenanceRepository {
}
