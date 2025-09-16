package org.ilestegor.lab1.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ilestegor.lab1.dto.RequestTodoDto;
import org.ilestegor.lab1.dto.ResponseTodoDto;
import org.ilestegor.lab1.service.TodoService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;


    @PostMapping("/todos")
    public ResponseEntity<ResponseTodoDto> addTodo(@RequestBody @Valid RequestTodoDto requestTodoDto, Authentication authentication) {
        ResponseTodoDto saved = todoService.addTodo(requestTodoDto, authentication);
        if (saved == null) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @GetMapping("/data/todos")
    public PagedModel<ResponseTodoDto> getAllTodos(Pageable pageable) {
        return new PagedModel<>(todoService.getAllTodos(pageable));
    }
}
