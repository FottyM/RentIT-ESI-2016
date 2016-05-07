package com.rentit.sales.domain.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QPurchaseOrderExtension is a Querydsl query type for PurchaseOrderExtension
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QPurchaseOrderExtension extends EntityPathBase<PurchaseOrderExtension> {

    private static final long serialVersionUID = 1866159874L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPurchaseOrderExtension purchaseOrderExtension = new QPurchaseOrderExtension("purchaseOrderExtension");

    public final QPurchaseOrderExtensionID id;

    public final QPurchaseOrderID purchaseOrder;

    public final com.rentit.common.domain.model.QBusinessPeriod rentalPeriod;

    public final EnumPath<POStatus> status = createEnum("status", POStatus.class);

    public QPurchaseOrderExtension(String variable) {
        this(PurchaseOrderExtension.class, forVariable(variable), INITS);
    }

    public QPurchaseOrderExtension(Path<? extends PurchaseOrderExtension> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPurchaseOrderExtension(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPurchaseOrderExtension(PathMetadata<?> metadata, PathInits inits) {
        this(PurchaseOrderExtension.class, metadata, inits);
    }

    public QPurchaseOrderExtension(Class<? extends PurchaseOrderExtension> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QPurchaseOrderExtensionID(forProperty("id")) : null;
        this.purchaseOrder = inits.isInitialized("purchaseOrder") ? new QPurchaseOrderID(forProperty("purchaseOrder")) : null;
        this.rentalPeriod = inits.isInitialized("rentalPeriod") ? new com.rentit.common.domain.model.QBusinessPeriod(forProperty("rentalPeriod")) : null;
    }

}

