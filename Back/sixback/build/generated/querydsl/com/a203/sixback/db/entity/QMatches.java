package com.a203.sixback.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMatches is a Querydsl query type for Matches
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMatches extends EntityPathBase<Matches> {

    private static final long serialVersionUID = 25427430L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMatches matches = new QMatches("matches");

    public final QTeam away;

    public final NumberPath<Integer> awayScore = createNumber("awayScore", Integer.class);

    public final QTeam home;

    public final NumberPath<Integer> homeScore = createNumber("homeScore", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> matchDate = createDateTime("matchDate", java.time.LocalDateTime.class);

    public final ListPath<MatchDet, QMatchDet> matchDetList = this.<MatchDet, QMatchDet>createList("matchDetList", MatchDet.class, QMatchDet.class, PathInits.DIRECT2);

    public final ListPath<MatchHistory, QMatchHistory> matchHistoryList = this.<MatchHistory, QMatchHistory>createList("matchHistoryList", MatchHistory.class, QMatchHistory.class, PathInits.DIRECT2);

    public final EnumPath<com.a203.sixback.db.enums.MatchStatus> matchStatus = createEnum("matchStatus", com.a203.sixback.db.enums.MatchStatus.class);

    public final ListPath<PlayerMatch, QPlayerMatch> playerMatchList = this.<PlayerMatch, QPlayerMatch>createList("playerMatchList", PlayerMatch.class, QPlayerMatch.class, PathInits.DIRECT2);

    public final StringPath referee = createString("referee");

    public final NumberPath<Integer> round = createNumber("round", Integer.class);

    public final StringPath stadium = createString("stadium");

    public QMatches(String variable) {
        this(Matches.class, forVariable(variable), INITS);
    }

    public QMatches(Path<? extends Matches> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMatches(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMatches(PathMetadata metadata, PathInits inits) {
        this(Matches.class, metadata, inits);
    }

    public QMatches(Class<? extends Matches> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.away = inits.isInitialized("away") ? new QTeam(forProperty("away"), inits.get("away")) : null;
        this.home = inits.isInitialized("home") ? new QTeam(forProperty("home"), inits.get("home")) : null;
    }

}

