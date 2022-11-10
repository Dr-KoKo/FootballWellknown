package com.a203.sixback.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlayerEvaluate is a Querydsl query type for PlayerEvaluate
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlayerEvaluate extends EntityPathBase<PlayerEvaluate> {

    private static final long serialVersionUID = -546593145L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlayerEvaluate playerEvaluate = new QPlayerEvaluate("playerEvaluate");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMatches matches;

    public final QPlayer player;

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    public final QUser user;

    public QPlayerEvaluate(String variable) {
        this(PlayerEvaluate.class, forVariable(variable), INITS);
    }

    public QPlayerEvaluate(Path<? extends PlayerEvaluate> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlayerEvaluate(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlayerEvaluate(PathMetadata metadata, PathInits inits) {
        this(PlayerEvaluate.class, metadata, inits);
    }

    public QPlayerEvaluate(Class<? extends PlayerEvaluate> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.matches = inits.isInitialized("matches") ? new QMatches(forProperty("matches"), inits.get("matches")) : null;
        this.player = inits.isInitialized("player") ? new QPlayer(forProperty("player"), inits.get("player")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

