package org.example.expert.domain.todo.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.manager.entity.QManager;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.example.expert.domain.comment.entity.QComment.comment;
import static org.example.expert.domain.manager.entity.QManager.manager;
import static org.example.expert.domain.todo.entity.QTodo.todo;

@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Todo> findByIdWithUser(Long todoId) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(todo)
                        .leftJoin(todo.user).fetchJoin()
                        .where(todo.id.eq(todoId))
                        .fetchOne()
        );
    }

    @Override
    public Page<TodoSearchResponse> searchTodoSummaries(
            String keyword,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            String managerNickname,
            Pageable pageable
    ) {
        List<TodoSearchResponse> content = queryFactory
                .select(Projections.constructor(
                        TodoSearchResponse.class,
                        todo.title,
                        manager.id.countDistinct(),
                        comment.id.countDistinct()
                ))
                .from(todo)
                .leftJoin(todo.managers, manager)
                .leftJoin(todo.comments, comment)
                .where(
                        titleContains(keyword),
                        createdAtGoe(startDateTime),
                        createdAtLt(endDateTime),
                        managerNicknameExists(managerNickname)
                )
                .groupBy(todo.id, todo.title, todo.createdAt)
                .orderBy(todo.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(todo.id.count())
                .from(todo)
                .where(
                        titleContains(keyword),
                        createdAtGoe(startDateTime),
                        createdAtLt(endDateTime),
                        managerNicknameExists(managerNickname)
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, total == null ? 0 : total);
    }

    private BooleanExpression titleContains(String keyword) {
        return StringUtils.hasText(keyword) ? todo.title.containsIgnoreCase(keyword) : null;
    }

    private BooleanExpression createdAtGoe(LocalDateTime startDateTime) {
        return startDateTime == null ? null : todo.createdAt.goe(startDateTime);
    }

    private BooleanExpression createdAtLt(LocalDateTime endDateTime) {
        return endDateTime == null ? null : todo.createdAt.lt(endDateTime);
    }

    private BooleanExpression managerNicknameExists(String managerNickname) {
        if (!StringUtils.hasText(managerNickname)) {
            return null;
        }

        QManager managerSub = new QManager("managerSub");
        QUser userSub = new QUser("userSub");

        return JPAExpressions
                .selectOne()
                .from(managerSub)
                .join(managerSub.user, userSub)
                .where(
                        managerSub.todo.eq(todo),
                        userSub.nickname.containsIgnoreCase(managerNickname)
                )
                .exists();
    }

}
