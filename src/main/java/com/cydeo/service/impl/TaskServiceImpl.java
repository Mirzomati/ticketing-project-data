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
import java.util.Optional;
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

        Optional<Task> foundTask = taskRepository.findById(id);

        if(foundTask.isPresent()){
            foundTask.get().setIsDeleted(true);
            taskRepository.save(foundTask.get());
        }

    }

    @Override
    public TaskDTO findById(Long id) {

        Optional<Task> task = taskRepository.findById(id);

        if(task.isPresent()){
            return taskMapper.convertToDto(task.get());

        }

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

        return tasks.stream()
                .map(taskMapper::convertToDto)
                .collect(Collectors.toList());

    }

    @Override
    public void updateTaskStatus(TaskDTO dto) {

        Task task = taskMapper.convertToEntity(dto);
        task.setId(taskRepository.findById(dto.getId()).get().getId());
        task.setTaskDetail(taskRepository.findById(dto.getId()).get().getTaskDetail());
        task.setAssignedDate(taskRepository.findById(dto.getId()).get().getAssignedDate());

        taskRepository.save(task);
    }
}
