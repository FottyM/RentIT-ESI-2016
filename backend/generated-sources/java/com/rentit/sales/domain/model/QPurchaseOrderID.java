package com.rentit.sales.domain.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPurchaseOrderID is a Querydsl query type for PurchaseOrderID
 */
@Generated("com.mysema.query.codegen.EmbeddableSerializer")
public class QPurchaseOrderID extends BeanPath<PurchaseOrderID> {

    private static final long serialVersionUID = 884676504L;

    public static final QPurchaseOrderID purchaseOrderID = new QPurchaseOrderID("purchaseOrderID");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QPurchaseOrderID(String variable) {
        super(PurchaseOrderID.class, forVariable(variable));
    }

    public QPurchaseOrderID(Path<? extends PurchaseOrderID> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPurchaseOrderID(PathMetadata<?> metadata) {
        super(PurchaseOrderID.class, metadata);
    }

}

