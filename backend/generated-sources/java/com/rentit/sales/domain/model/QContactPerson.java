package com.rentit.sales.domain.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QContactPerson is a Querydsl query type for ContactPerson
 */
@Generated("com.mysema.query.codegen.EmbeddableSerializer")
public class QContactPerson extends BeanPath<ContactPerson> {

    private static final long serialVersionUID = -690154875L;

    public static final QContactPerson contactPerson = new QContactPerson("contactPerson");

    public final StringPath email = createString("email");

    public final StringPath name = createString("name");

    public QContactPerson(String variable) {
        super(ContactPerson.class, forVariable(variable));
    }

    public QContactPerson(Path<? extends ContactPerson> path) {
        super(path.getType(), path.getMetadata());
    }

    public QContactPerson(PathMetadata<?> metadata) {
        super(ContactPerson.class, metadata);
    }

}

