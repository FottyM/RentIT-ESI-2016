package com.rentit.maintenance.domain.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QMaintenancePlanID is a Querydsl query type for MaintenancePlanID
 */
@Generated("com.mysema.query.codegen.EmbeddableSerializer")
public class QMaintenancePlanID extends BeanPath<MaintenancePlanID> {

    private static final long serialVersionUID = -1571576512L;

    public static final QMaintenancePlanID maintenancePlanID = new QMaintenancePlanID("maintenancePlanID");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QMaintenancePlanID(String variable) {
        super(MaintenancePlanID.class, forVariable(variable));
    }

    public QMaintenancePlanID(Path<? extends MaintenancePlanID> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMaintenancePlanID(PathMetadata<?> metadata) {
        super(MaintenancePlanID.class, metadata);
    }

}

