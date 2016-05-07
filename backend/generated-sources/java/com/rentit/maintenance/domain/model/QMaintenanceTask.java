package com.rentit.maintenance.domain.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QMaintenanceTask is a Querydsl query type for MaintenanceTask
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QMaintenanceTask extends EntityPathBase<MaintenanceTask> {

    private static final long serialVersionUID = -1570239551L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMaintenanceTask maintenanceTask = new QMaintenanceTask("maintenanceTask");

    public final QMaintenanceTaskID id;

    public final NumberPath<java.math.BigDecimal> price = createNumber("price", java.math.BigDecimal.class);

    public final com.rentit.inventory.domain.model.QPlantReservationID reservation;

    public final com.rentit.common.domain.model.QBusinessPeriod schedule;

    public final EnumPath<TypeOfWork> typeOfWork = createEnum("typeOfWork", TypeOfWork.class);

    public QMaintenanceTask(String variable) {
        this(MaintenanceTask.class, forVariable(variable), INITS);
    }

    public QMaintenanceTask(Path<? extends MaintenanceTask> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QMaintenanceTask(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QMaintenanceTask(PathMetadata<?> metadata, PathInits inits) {
        this(MaintenanceTask.class, metadata, inits);
    }

    public QMaintenanceTask(Class<? extends MaintenanceTask> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QMaintenanceTaskID(forProperty("id")) : null;
        this.reservation = inits.isInitialized("reservation") ? new com.rentit.inventory.domain.model.QPlantReservationID(forProperty("reservation")) : null;
        this.schedule = inits.isInitialized("schedule") ? new com.rentit.common.domain.model.QBusinessPeriod(forProperty("schedule")) : null;
    }

}

