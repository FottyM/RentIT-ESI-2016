package com.rentit.inventory.domain.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QPlantInventoryEntry is a Querydsl query type for PlantInventoryEntry
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QPlantInventoryEntry extends EntityPathBase<PlantInventoryEntry> {

    private static final long serialVersionUID = -1223256607L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlantInventoryEntry plantInventoryEntry = new QPlantInventoryEntry("plantInventoryEntry");

    public final StringPath description = createString("description");

    public final QPlantInventoryEntryID id;

    public final StringPath name = createString("name");

    public final NumberPath<java.math.BigDecimal> price = createNumber("price", java.math.BigDecimal.class);

    public QPlantInventoryEntry(String variable) {
        this(PlantInventoryEntry.class, forVariable(variable), INITS);
    }

    public QPlantInventoryEntry(Path<? extends PlantInventoryEntry> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPlantInventoryEntry(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPlantInventoryEntry(PathMetadata<?> metadata, PathInits inits) {
        this(PlantInventoryEntry.class, metadata, inits);
    }

    public QPlantInventoryEntry(Class<? extends PlantInventoryEntry> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QPlantInventoryEntryID(forProperty("id")) : null;
    }

}

