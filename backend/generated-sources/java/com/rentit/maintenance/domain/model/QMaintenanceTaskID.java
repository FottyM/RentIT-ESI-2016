package com.rentit.maintenance.domain.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QMaintenanceTaskID is a Querydsl query type for MaintenanceTaskID
 */
@Generated("com.mysema.query.codegen.EmbeddableSerializer")
public class QMaintenanceTaskID extends BeanPath<MaintenanceTaskID> {

    private static final long serialVersionUID = -1466685284L;

    public static final QMaintenanceTaskID maintenanceTaskID = new QMaintenanceTaskID("maintenanceTaskID");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QMaintenanceTaskID(String variable) {
        super(MaintenanceTaskID.class, forVariable(variable));
    }

    public QMaintenanceTaskID(Path<? extends MaintenanceTaskID> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMaintenanceTaskID(PathMetadata<?> metadata) {
        super(MaintenanceTaskID.class, metadata);
    }

}

