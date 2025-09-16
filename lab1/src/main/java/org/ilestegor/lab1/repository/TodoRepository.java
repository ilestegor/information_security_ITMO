package org.ilestegor.lab1.repository;

import org.ilestegor.lab1.dto.ResponseTodoDto;
import org.ilestegor.lab1.model.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query("""
               select new org.ilestegor.lab1.dto.ResponseTodoDto(
                 t.id, t.taskName, t.description, t.deadline, t.priority, t.created, t.isCompleted
               )
               from Todo t
               where t.user.userId = :userId
            """)
    Page<ResponseTodoDto> findTodosByUser_UserId(@Param("userId") long userId, Pageable pageable);
}
