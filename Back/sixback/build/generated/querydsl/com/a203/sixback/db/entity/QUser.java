package com.a203.sixback.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -628757512L;

    public static final QUser user = new QUser("user");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nickname = createString("nickname");

    public final NumberPath<Integer> point = createNumber("point", Integer.class);

    public final EnumPath<com.a203.sixback.db.enums.ProviderType> providerType = createEnum("providerType", com.a203.sixback.db.enums.ProviderType.class);

    public final EnumPath<com.a203.sixback.db.enums.RoleType> roleType = createEnum("roleType", com.a203.sixback.db.enums.RoleType.class);

    public final EnumPath<com.a203.sixback.db.enums.Status> status = createEnum("status", com.a203.sixback.db.enums.Status.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

