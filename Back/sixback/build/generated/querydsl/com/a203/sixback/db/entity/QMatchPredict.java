package com.a203.sixback.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMatchPredict is a Querydsl query type for MatchPredict
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMatchPredict extends EntityPathBase<MatchPredict> {

    private static final long serialVersionUID = 1746099841L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMatchPredict matchPredict = new QMatchPredict("matchPredict");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMatches matches;

    public final QUser user;

    public final StringPath whereWin = createString("whereWin");

    public QMatchPredict(String variable) {
        this(MatchPredict.class, forVariable(variable), INITS);
    }

    public QMatchPredict(Path<? extends MatchPredict> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMatchPredict(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMatchPredict(PathMetadata metadata, PathInits inits) {
        this(MatchPredict.class, metadata, inits);
    }

    public QMatchPredict(Class<? extends MatchPredict> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.matches = inits.isInitialized("matches") ? new QMatches(forProperty("matches"), inits.get("matches")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

