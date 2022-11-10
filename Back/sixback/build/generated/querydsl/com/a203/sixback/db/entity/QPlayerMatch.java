package com.a203.sixback.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlayerMatch is a Querydsl query type for PlayerMatch
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlayerMatch extends EntityPathBase<PlayerMatch> {

    private static final long serialVersionUID = 563402391L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlayerMatch playerMatch = new QPlayerMatch("playerMatch");

    public final NumberPath<Integer> assist = createNumber("assist", Integer.class);

    public final NumberPath<Integer> clear = createNumber("clear", Integer.class);

    public final NumberPath<Integer> crossed = createNumber("crossed", Integer.class);

    public final NumberPath<Integer> crossedOn = createNumber("crossedOn", Integer.class);

    public final NumberPath<Integer> dribble = createNumber("dribble", Integer.class);

    public final NumberPath<Integer> dribbleOn = createNumber("dribbleOn", Integer.class);

    public final NumberPath<Integer> expertRate = createNumber("expertRate", Integer.class);

    public final NumberPath<Integer> foul = createNumber("foul", Integer.class);

    public final NumberPath<Integer> goal = createNumber("goal", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMatches matches;

    public final NumberPath<Integer> pass = createNumber("pass", Integer.class);

    public final NumberPath<Integer> passOn = createNumber("passOn", Integer.class);

    public final QPlayer player;

    public final NumberPath<Integer> position = createNumber("position", Integer.class);

    public final NumberPath<Integer> red = createNumber("red", Integer.class);

    public final NumberPath<Integer> shot = createNumber("shot", Integer.class);

    public final NumberPath<Integer> shotOn = createNumber("shotOn", Integer.class);

    public final NumberPath<Integer> tackle = createNumber("tackle", Integer.class);

    public final StringPath team = createString("team");

    public final NumberPath<Integer> userCount = createNumber("userCount", Integer.class);

    public final NumberPath<Integer> userRate = createNumber("userRate", Integer.class);

    public final NumberPath<Integer> yellow = createNumber("yellow", Integer.class);

    public QPlayerMatch(String variable) {
        this(PlayerMatch.class, forVariable(variable), INITS);
    }

    public QPlayerMatch(Path<? extends PlayerMatch> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlayerMatch(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlayerMatch(PathMetadata metadata, PathInits inits) {
        this(PlayerMatch.class, metadata, inits);
    }

    public QPlayerMatch(Class<? extends PlayerMatch> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.matches = inits.isInitialized("matches") ? new QMatches(forProperty("matches"), inits.get("matches")) : null;
        this.player = inits.isInitialized("player") ? new QPlayer(forProperty("player"), inits.get("player")) : null;
    }

}

