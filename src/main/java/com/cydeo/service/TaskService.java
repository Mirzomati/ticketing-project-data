package com.cydeo.service;

import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.Task;
import com.cydeo.enums.Status;

import java.util.List;

public interface TaskService {

    List<TaskDTO> listAllTasks();

    void save(TaskDTO dto);
    void update(TaskDTO dto);
    void delete(Long id);

    TaskDTO findById(Long id);

    List<TaskDTO> findAllTasksByStatusIs(Status status);

    List<TaskDTO> findAllTasksByStatusIsNot(Status status);

}
