package com.a203.sixback.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMatchDet is a Querydsl query type for MatchDet
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMatchDet extends EntityPathBase<MatchDet> {

    private static final long serialVersionUID = 788218299L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMatchDet matchDet = new QMatchDet("matchDet");

    public final NumberPath<Integer> corner = createNumber("corner", Integer.class);

    public final StringPath formation = createString("formation");

    public final NumberPath<Integer> foul = createNumber("foul", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMatches matches;

    public final NumberPath<Integer> offside = createNumber("offside", Integer.class);

    public final NumberPath<Integer> pass = createNumber("pass", Integer.class);

    public final NumberPath<Integer> passOn = createNumber("passOn", Integer.class);

    public final NumberPath<Integer> penalty = createNumber("penalty", Integer.class);

    public final StringPath possession = createString("possession");

    public final NumberPath<Integer> red = createNumber("red", Integer.class);

    public final NumberPath<Integer> save = createNumber("save", Integer.class);

    public final NumberPath<Integer> shot = createNumber("shot", Integer.class);

    public final NumberPath<Integer> shotOn = createNumber("shotOn", Integer.class);

    public final EnumPath<com.a203.sixback.db.enums.TeamType> teamType = createEnum("teamType", com.a203.sixback.db.enums.TeamType.class);

    public final NumberPath<Integer> yellow = createNumber("yellow", Integer.class);

    public QMatchDet(String variable) {
        this(MatchDet.class, forVariable(variable), INITS);
    }

    public QMatchDet(Path<? extends MatchDet> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMatchDet(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMatchDet(PathMetadata metadata, PathInits inits) {
        this(MatchDet.class, metadata, inits);
    }

    public QMatchDet(Class<? extends MatchDet> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.matches = inits.isInitialized("matches") ? new QMatches(forProperty("matches"), inits.get("matches")) : null;
    }

}

