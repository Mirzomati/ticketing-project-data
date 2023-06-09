package com.cydeo.service.impl;

import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.Task;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
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
    private  final ProjectMapper projectMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper, ProjectMapper projectMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.projectMapper = projectMapper;
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
        task.setTaskStatus(Status.OPEN);
        task.setAssignedDate(LocalDate.now());



        taskRepository.save(task);
    }

    @Override
    public void update(TaskDTO dto) {

        Task task = taskMapper.convertToEntity(dto);
        task.setId(taskRepository.findById(dto.getId()).get().getId());
        task.setTaskStatus(taskRepository.findById(task.getId()).get().getTaskStatus());
        task.setAssignedDate(LocalDate.now());
        taskRepository.save(task);

    }

    @Override
    public void delete(Long id) {

        Task task = taskRepository.findById(id).get();
        task.setIsDeleted(true);
        taskRepository.save(task);
    }

    @Override
    public TaskDTO findById(Long id) {




        return taskMapper.convertToDto(taskRepository.findById(id).get());
    }

    @Override
    public List<TaskDTO> findAllTasksByStatusIs(Status status) {
        List<Task> tasks = taskRepository.findAllByTaskStatusIs(status);

        return tasks.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> findAllTasksByStatusIsNot(Status status) {

        List<Task> tasks = taskRepository.findAllByTaskStatusIsNot(status);

        return tasks.stream().map(taskMapper::convertToDto).collect(Collectors.toList());

    }
}
