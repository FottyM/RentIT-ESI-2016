package com.rentit.sales.domain.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QInvoiceID is a Querydsl query type for InvoiceID
 */
@Generated("com.mysema.query.codegen.EmbeddableSerializer")
public class QInvoiceID extends BeanPath<InvoiceID> {

    private static final long serialVersionUID = 453520376L;

    public static final QInvoiceID invoiceID = new QInvoiceID("invoiceID");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QInvoiceID(String variable) {
        super(InvoiceID.class, forVariable(variable));
    }

    public QInvoiceID(Path<? extends InvoiceID> path) {
        super(path.getType(), path.getMetadata());
    }

    public QInvoiceID(PathMetadata<?> metadata) {
        super(InvoiceID.class, metadata);
    }

}

