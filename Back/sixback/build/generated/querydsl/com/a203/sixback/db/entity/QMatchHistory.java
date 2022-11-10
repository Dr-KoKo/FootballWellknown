package com.a203.sixback.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMatchHistory is a Querydsl query type for MatchHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMatchHistory extends EntityPathBase<MatchHistory> {

    private static final long serialVersionUID = -1303212484L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMatchHistory matchHistory = new QMatchHistory("matchHistory");

    public final EnumPath<com.a203.sixback.db.enums.History> history = createEnum("history", com.a203.sixback.db.enums.History.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath info = createString("info");

    public final StringPath mainName = createString("mainName");

    public final QMatches matches;

    public final StringPath subName = createString("subName");

    public final EnumPath<com.a203.sixback.db.enums.TeamType> teamType = createEnum("teamType", com.a203.sixback.db.enums.TeamType.class);

    public final StringPath time = createString("time");

    public QMatchHistory(String variable) {
        this(MatchHistory.class, forVariable(variable), INITS);
    }

    public QMatchHistory(Path<? extends MatchHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMatchHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMatchHistory(PathMetadata metadata, PathInits inits) {
        this(MatchHistory.class, metadata, inits);
    }

    public QMatchHistory(Class<? extends MatchHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.matches = inits.isInitialized("matches") ? new QMatches(forProperty("matches"), inits.get("matches")) : null;
    }

}

