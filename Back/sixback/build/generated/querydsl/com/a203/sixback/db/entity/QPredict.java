package com.a203.sixback.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPredict is a Querydsl query type for Predict
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPredict extends EntityPathBase<Predict> {

    private static final long serialVersionUID = -1134155380L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPredict predict = new QPredict("predict");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMatches matches;

    public final EnumPath<com.a203.sixback.db.enums.MatchResult> matchResult = createEnum("matchResult", com.a203.sixback.db.enums.MatchResult.class);

    public final QUser user;

    public QPredict(String variable) {
        this(Predict.class, forVariable(variable), INITS);
    }

    public QPredict(Path<? extends Predict> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPredict(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPredict(PathMetadata metadata, PathInits inits) {
        this(Predict.class, metadata, inits);
    }

    public QPredict(Class<? extends Predict> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.matches = inits.isInitialized("matches") ? new QMatches(forProperty("matches"), inits.get("matches")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

