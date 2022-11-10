package com.a203.sixback.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlayer is a Querydsl query type for Player
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlayer extends EntityPathBase<Player> {

    private static final long serialVersionUID = 1204700110L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlayer player = new QPlayer("player");

    public final NumberPath<Integer> assists = createNumber("assists", Integer.class);

    public final StringPath birth = createString("birth");

    public final StringPath country = createString("country");

    public final NumberPath<Integer> goals = createNumber("goals", Integer.class);

    public final NumberPath<Integer> height = createNumber("height", Integer.class);

    public final StringPath history = createString("history");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath image = createString("image");

    public final NumberPath<Integer> joinMatches = createNumber("joinMatches", Integer.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> number = createNumber("number", Integer.class);

    public final ListPath<PlayerMatch, QPlayerMatch> playerMatchList = this.<PlayerMatch, QPlayerMatch>createList("playerMatchList", PlayerMatch.class, QPlayerMatch.class, PathInits.DIRECT2);

    public final StringPath position = createString("position");

    public final QTeam team;

    public final NumberPath<Integer> weight = createNumber("weight", Integer.class);

    public QPlayer(String variable) {
        this(Player.class, forVariable(variable), INITS);
    }

    public QPlayer(Path<? extends Player> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlayer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlayer(PathMetadata metadata, PathInits inits) {
        this(Player.class, metadata, inits);
    }

    public QPlayer(Class<? extends Player> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.team = inits.isInitialized("team") ? new QTeam(forProperty("team"), inits.get("team")) : null;
    }

}

