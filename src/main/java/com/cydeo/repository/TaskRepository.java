package com.cydeo.repository;

import com.cydeo.entity.Project;
import com.cydeo.entity.Task;
import com.cydeo.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository  extends JpaRepository<Task, Long> {

    List<Task> findAllByTaskStatusIs(Status status);

    List<Task> findAllByTaskStatusIsNot(Status status);


    @Query("SELECT COUNT(t) FROM Task t WHERE t.project.projectCode = ?1 AND t.taskStatus <> 'COMPLETE' ")
    int totalNonCompletedTasks( String codee);

    @Query(value = "SELECT COUNT(*)" + "FROM tasks t JOIN projects p on t.project_id=p.id" + "WHERE p.project_code=?1 AND t.task.status='COMPLETE'", nativeQuery = true )
    int totalCompletedTasks( String projectCode);

    List<Task> findAllByProject(Project project);




}
