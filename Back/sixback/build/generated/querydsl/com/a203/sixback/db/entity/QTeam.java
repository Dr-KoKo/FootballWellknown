package com.a203.sixback.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTeam is a Querydsl query type for Team
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTeam extends EntityPathBase<Team> {

    private static final long serialVersionUID = -628800886L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTeam team = new QTeam("team");

    public final QCoach coach;

    public final NumberPath<Integer> draw = createNumber("draw", Integer.class);

    public final NumberPath<Integer> goals = createNumber("goals", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath image = createString("image");

    public final NumberPath<Integer> lose = createNumber("lose", Integer.class);

    public final NumberPath<Integer> loseGoals = createNumber("loseGoals", Integer.class);

    public final StringPath name = createString("name");

    public final ListPath<Player, QPlayer> players = this.<Player, QPlayer>createList("players", Player.class, QPlayer.class, PathInits.DIRECT2);

    public final NumberPath<Integer> win = createNumber("win", Integer.class);

    public QTeam(String variable) {
        this(Team.class, forVariable(variable), INITS);
    }

    public QTeam(Path<? extends Team> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTeam(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTeam(PathMetadata metadata, PathInits inits) {
        this(Team.class, metadata, inits);
    }

    public QTeam(Class<? extends Team> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.coach = inits.isInitialized("coach") ? new QCoach(forProperty("coach"), inits.get("coach")) : null;
    }

}

