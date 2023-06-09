package com.cydeo.service;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Task;
import com.cydeo.enums.Status;

import java.util.List;

public interface TaskService {

    List<TaskDTO> listAllTasks();

    void save(TaskDTO dto);
    void update(TaskDTO dto);
    void delete(Long id);

    TaskDTO findById(Long id);

    int totalNonCompletedTask(String projectCode);

    int totalCompletedTask(String projectCode);

    List<TaskDTO> listAllTasksByStatusIs(Status status);

    List<TaskDTO> listAllTasksByStatusIsNot(Status status);

    void updateTaskStatus(TaskDTO dto);

    void deleteByProject(ProjectDTO projectDTO);

    void completeByProject(ProjectDTO projectDTO);

    List<TaskDTO> listAllNonCompletedByAssignedManager(UserDTO assigendManager);
}
