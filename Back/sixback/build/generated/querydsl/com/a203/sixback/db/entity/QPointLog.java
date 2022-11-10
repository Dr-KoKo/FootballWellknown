package com.a203.sixback.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPointLog is a Querydsl query type for PointLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPointLog extends EntityPathBase<PointLog> {

    private static final long serialVersionUID = 957435201L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPointLog pointLog = new QPointLog("pointLog");

    public final DateTimePath<java.time.LocalDateTime> distribute_time = createDateTime("distribute_time", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QPredict mp;

    public final QPlayerEvaluate pe;

    public final NumberPath<Integer> point = createNumber("point", Integer.class);

    public final QUser user;

    public QPointLog(String variable) {
        this(PointLog.class, forVariable(variable), INITS);
    }

    public QPointLog(Path<? extends PointLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPointLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPointLog(PathMetadata metadata, PathInits inits) {
        this(PointLog.class, metadata, inits);
    }

    public QPointLog(Class<? extends PointLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.mp = inits.isInitialized("mp") ? new QPredict(forProperty("mp"), inits.get("mp")) : null;
        this.pe = inits.isInitialized("pe") ? new QPlayerEvaluate(forProperty("pe"), inits.get("pe")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

