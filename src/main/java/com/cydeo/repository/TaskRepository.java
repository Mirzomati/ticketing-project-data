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

    Integer countAllByTaskStatusIsAndProjectProjectCode(Status statusIs, String code);

    Integer countAllByTaskStatusIsNotAndProjectProjectCode(Status statusIsNot, String code);

//    @Query("SELECT t FROM Task t WHERE t.taskStatus = ?1 and t.project.projectCode = ?2 ")
//    Integer numberOfCompletedTasks(Status status, String codee);
//

}
