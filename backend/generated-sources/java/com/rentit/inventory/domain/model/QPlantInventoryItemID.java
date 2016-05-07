package com.rentit.inventory.domain.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPlantInventoryItemID is a Querydsl query type for PlantInventoryItemID
 */
@Generated("com.mysema.query.codegen.EmbeddableSerializer")
public class QPlantInventoryItemID extends BeanPath<PlantInventoryItemID> {

    private static final long serialVersionUID = 853355487L;

    public static final QPlantInventoryItemID plantInventoryItemID = new QPlantInventoryItemID("plantInventoryItemID");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QPlantInventoryItemID(String variable) {
        super(PlantInventoryItemID.class, forVariable(variable));
    }

    public QPlantInventoryItemID(Path<? extends PlantInventoryItemID> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPlantInventoryItemID(PathMetadata<?> metadata) {
        super(PlantInventoryItemID.class, metadata);
    }

}

