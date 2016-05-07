package com.rentit.inventory.domain.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QPlantReservation is a Querydsl query type for PlantReservation
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QPlantReservation extends EntityPathBase<PlantReservation> {

    private static final long serialVersionUID = 666900033L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlantReservation plantReservation = new QPlantReservation("plantReservation");

    public final QPlantReservationID id;

    public final QPlantInventoryItemID plant;

    public final com.rentit.common.domain.model.QBusinessPeriod schedule;

    public QPlantReservation(String variable) {
        this(PlantReservation.class, forVariable(variable), INITS);
    }

    public QPlantReservation(Path<? extends PlantReservation> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPlantReservation(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPlantReservation(PathMetadata<?> metadata, PathInits inits) {
        this(PlantReservation.class, metadata, inits);
    }

    public QPlantReservation(Class<? extends PlantReservation> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QPlantReservationID(forProperty("id")) : null;
        this.plant = inits.isInitialized("plant") ? new QPlantInventoryItemID(forProperty("plant")) : null;
        this.schedule = inits.isInitialized("schedule") ? new com.rentit.common.domain.model.QBusinessPeriod(forProperty("schedule")) : null;
    }

}

