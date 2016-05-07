package com.rentit.inventory.domain.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPlantInventoryEntryID is a Querydsl query type for PlantInventoryEntryID
 */
@Generated("com.mysema.query.codegen.EmbeddableSerializer")
public class QPlantInventoryEntryID extends BeanPath<PlantInventoryEntryID> {

    private static final long serialVersionUID = 1271442108L;

    public static final QPlantInventoryEntryID plantInventoryEntryID = new QPlantInventoryEntryID("plantInventoryEntryID");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QPlantInventoryEntryID(String variable) {
        super(PlantInventoryEntryID.class, forVariable(variable));
    }

    public QPlantInventoryEntryID(Path<? extends PlantInventoryEntryID> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPlantInventoryEntryID(PathMetadata<?> metadata) {
        super(PlantInventoryEntryID.class, metadata);
    }

}

