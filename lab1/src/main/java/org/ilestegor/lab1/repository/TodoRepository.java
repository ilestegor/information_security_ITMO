package org.ilestegor.lab1.repository;

import org.ilestegor.lab1.dto.ResponseTodoDto;
import org.ilestegor.lab1.model.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {


    Page<ResponseTodoDto> findTodosByUser_UserId(long userUserId, Pageable pageable);
}
