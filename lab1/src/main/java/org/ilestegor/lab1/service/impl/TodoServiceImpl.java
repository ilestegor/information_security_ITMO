package org.ilestegor.lab1.service.impl;

import jakarta.transaction.Transactional;
import org.ilestegor.lab1.configuration.ExtendedUserDetails;
import org.ilestegor.lab1.dto.RequestTodoDto;
import org.ilestegor.lab1.dto.ResponseTodoDto;
import org.ilestegor.lab1.exception.exceptions.DeadlineInPastException;
import org.ilestegor.lab1.exception.exceptions.UserNotfoundException;
import org.ilestegor.lab1.model.Todo;
import org.ilestegor.lab1.repository.TodoRepository;
import org.ilestegor.lab1.repository.UserRepository;
import org.ilestegor.lab1.service.TodoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;


    public TodoServiceImpl(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public ResponseTodoDto addTodo(RequestTodoDto requestTodoDto, Authentication authentication) {

        if (requestTodoDto.deadline().isBefore(LocalDateTime.now())) {
            throw new DeadlineInPastException();
        }

        var user = userRepository.findByUserName(authentication.getName());
        if (user.isEmpty()) {
            throw new UserNotfoundException(authentication.getName());
        }
        Todo todo = new Todo();
        todo.setUser(user.get());
        todo.setTaskName(requestTodoDto.taskName());
        todo.setDeadline(requestTodoDto.deadline());
        todo.setPriority(requestTodoDto.priority());
        todo.setCreated(Instant.now());
        todo.setDescription(requestTodoDto.description());
        todo.setCompleted(null);
        Todo savedTodo = todoRepository.save(todo);
        return new ResponseTodoDto(savedTodo.getId(), savedTodo.getTaskName(), savedTodo.getDescription(), savedTodo.getDeadline(), savedTodo.getPriority(), todo.getCreated(), todo.isCompleted());
    }

    @Override
    public Page<ResponseTodoDto> getAllTodos(Pageable pageable) {
        ExtendedUserDetails userDetails = (ExtendedUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var page = todoRepository.findTodosByUser_UserId(userDetails.getId(), pageable);
        return page.map(x -> x.toBuilder()
                .taskName(HtmlUtils.htmlEscape(x.getTaskName()))
                .description(HtmlUtils.htmlEscape(x.getDescription()))
                .build()
        );
    }
}
