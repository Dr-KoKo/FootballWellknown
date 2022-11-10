package com.a203.sixback.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCoach is a Querydsl query type for Coach
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCoach extends EntityPathBase<Coach> {

    private static final long serialVersionUID = 1966606861L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCoach coach = new QCoach("coach");

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final StringPath country = createString("country");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath image = createString("image");

    public final StringPath name = createString("name");

    public final QTeam team;

    public QCoach(String variable) {
        this(Coach.class, forVariable(variable), INITS);
    }

    public QCoach(Path<? extends Coach> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCoach(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCoach(PathMetadata metadata, PathInits inits) {
        this(Coach.class, metadata, inits);
    }

    public QCoach(Class<? extends Coach> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.team = inits.isInitialized("team") ? new QTeam(forProperty("team"), inits.get("team")) : null;
    }

}

