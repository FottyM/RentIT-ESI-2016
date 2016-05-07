package com.rentit.maintenance.domain.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QMaintenancePlan is a Querydsl query type for MaintenancePlan
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QMaintenancePlan extends EntityPathBase<MaintenancePlan> {

    private static final long serialVersionUID = -1570348699L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMaintenancePlan maintenancePlan = new QMaintenancePlan("maintenancePlan");

    public final QMaintenancePlanID id;

    public final ListPath<MaintenanceTaskID, QMaintenanceTaskID> tasks = this.<MaintenanceTaskID, QMaintenanceTaskID>createList("tasks", MaintenanceTaskID.class, QMaintenanceTaskID.class, PathInits.DIRECT2);

    public final NumberPath<Integer> yearOfAction = createNumber("yearOfAction", Integer.class);

    public QMaintenancePlan(String variable) {
        this(MaintenancePlan.class, forVariable(variable), INITS);
    }

    public QMaintenancePlan(Path<? extends MaintenancePlan> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QMaintenancePlan(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QMaintenancePlan(PathMetadata<?> metadata, PathInits inits) {
        this(MaintenancePlan.class, metadata, inits);
    }

    public QMaintenancePlan(Class<? extends MaintenancePlan> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QMaintenancePlanID(forProperty("id")) : null;
    }

}

