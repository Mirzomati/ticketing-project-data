package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.repository.TaskRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserMapper userMapper;
    private final TaskRepository taskRepository;
    private final UserService userService;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper, UserMapper userMapper, TaskRepository taskRepository, UserService userService) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.userMapper = userMapper;
        this.taskRepository = taskRepository;
        this.userService = userService;
    }


    @Override
    public ProjectDTO getByProjectCode(String code) {

        return projectMapper.convertToDto(projectRepository.findByProjectCode(code));
    }

    @Override
    public List<ProjectDTO> listAllProjects() {

        return projectRepository.findAll(Sort.by("projectCode")).stream()
                .map(projectMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void save(ProjectDTO dto) {

        Project project = projectMapper.convertToEntity(dto);
        project.setProjectStatus(Status.OPEN);
        projectRepository.save(project);

    }

    @Override
    public void update(ProjectDTO dto) {

        //1st convert the dto to entity
        Project project = projectMapper.convertToEntity(dto);
        //2nd set the id of the converted entity
        project.setId(projectRepository.findByProjectCode(dto.getProjectCode()).getId());
        project.setProjectStatus(projectRepository.findByProjectCode(dto.getProjectCode()).getProjectStatus());
        //save the entity
        projectRepository.save(project);

    }

    @Override
    public void delete(String code) {

        Project project = projectRepository.findByProjectCode(code);
        project.setIsDeleted(true);
        projectRepository.save(project);
    }

    @Override
    public void complete(String code) {


        Project project = projectRepository.findByProjectCode(code);
        project.setProjectStatus(Status.COMPLETE);
        projectRepository.save(project);
    }

//    @Override
//    public List<ProjectDTO> listAllProjectsByManger() {
//
////        UserDTO currentUserDTO = userService.findByUserName("harold@manager.com");
////        List<Project> projects = projectRepository.findAllByAssignedManager(userMapper.convertToEntity(currentUserDTO));
////
////        return projects.stream()
////                .map(projectMapper::convertToDto)
////                .collect(Collectors.toList());
//    }

    @Override
    public List<ProjectDTO> listAllProjectDetails() {

        UserDTO currentUserDTO = userService.findByUserName("harold@manager.com");
        User user = userMapper.convertToEntity(currentUserDTO);
        List<Project> projects = projectRepository.findAllByAssignedManager(user);

        return projects.stream()
                .map(projectMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void setUnfinishedCompleteCount(List<ProjectDTO> dtos) {



        dtos.stream()
                .forEach(p -> p.setCompleteTaskCounts(taskRepository.countAllByTaskStatusIsAndProjectProjectCode(Status.COMPLETE, p.getProjectCode())));

        dtos.stream()
                .forEach(p -> p.setUnfinishedTaskCounts(taskRepository.countAllByTaskStatusIsNotAndProjectProjectCode(Status.COMPLETE, p.getProjectCode())));





    }
}
