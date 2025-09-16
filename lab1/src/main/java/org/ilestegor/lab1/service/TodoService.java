package org.ilestegor.lab1.service;

import org.ilestegor.lab1.dto.RequestTodoDto;
import org.ilestegor.lab1.dto.ResponseTodoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface TodoService {
    ResponseTodoDto addTodo(RequestTodoDto todoDto, Authentication authentication);

    Page<ResponseTodoDto> getAllTodos(Pageable pageable);
}
