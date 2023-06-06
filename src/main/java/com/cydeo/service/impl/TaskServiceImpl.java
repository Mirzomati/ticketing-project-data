package com.cydeo.service.impl;

import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.Task;
import com.cydeo.enums.Status;
import com.cydeo.mapper.TaskMapper;
import com.cydeo.repository.TaskRepository;
import com.cydeo.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class TaskServiceImpl implements TaskService {

    private  final TaskRepository taskRepository;
    private  final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public List<TaskDTO> listAllTasks() {

        return taskRepository.findAll().stream()
                .map(taskMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void save(TaskDTO dto) {

        Task task = taskMapper.convertToEntity(dto);

        taskRepository.save(task);
    }

    @Override
    public void update(TaskDTO dto) {

    }

    @Override
    public void delete(String subject) {

        Task task = taskRepository.findByTaskSubjectContainingIgnoreCase(subject);
        task.setIsDeleted(true);
        taskRepository.save(task);
    }
}
