package com.rentit.common.domain.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QBusinessPeriod is a Querydsl query type for BusinessPeriod
 */
@Generated("com.mysema.query.codegen.EmbeddableSerializer")
public class QBusinessPeriod extends BeanPath<BusinessPeriod> {

    private static final long serialVersionUID = -1165128598L;

    public static final QBusinessPeriod businessPeriod = new QBusinessPeriod("businessPeriod");

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public QBusinessPeriod(String variable) {
        super(BusinessPeriod.class, forVariable(variable));
    }

    public QBusinessPeriod(Path<? extends BusinessPeriod> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBusinessPeriod(PathMetadata<?> metadata) {
        super(BusinessPeriod.class, metadata);
    }

}

