package com.rentit.inventory.domain.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QPlantInventoryItem is a Querydsl query type for PlantInventoryItem
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QPlantInventoryItem extends EntityPathBase<PlantInventoryItem> {

    private static final long serialVersionUID = -1701903420L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlantInventoryItem plantInventoryItem = new QPlantInventoryItem("plantInventoryItem");

    public final EnumPath<EquipmentCondition> condition = createEnum("condition", EquipmentCondition.class);

    public final QPlantInventoryItemID id;

    public final QPlantInventoryEntryID plantInfo;

    public final StringPath serialNumber = createString("serialNumber");

    public QPlantInventoryItem(String variable) {
        this(PlantInventoryItem.class, forVariable(variable), INITS);
    }

    public QPlantInventoryItem(Path<? extends PlantInventoryItem> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPlantInventoryItem(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPlantInventoryItem(PathMetadata<?> metadata, PathInits inits) {
        this(PlantInventoryItem.class, metadata, inits);
    }

    public QPlantInventoryItem(Class<? extends PlantInventoryItem> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QPlantInventoryItemID(forProperty("id")) : null;
        this.plantInfo = inits.isInitialized("plantInfo") ? new QPlantInventoryEntryID(forProperty("plantInfo")) : null;
    }

}

