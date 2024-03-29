package io.agileintelligence.ppmtool.services;

import io.agileintelligence.ppmtool.domain.Backlog;
import io.agileintelligence.ppmtool.domain.Project;
import io.agileintelligence.ppmtool.domain.ProjectTask;
import io.agileintelligence.ppmtool.exceptions.ProjectNotFoundException;
import io.agileintelligence.ppmtool.repositories.BacklogRepository;
import io.agileintelligence.ppmtool.repositories.ProjectRepository;
import io.agileintelligence.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;


    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {

            //PTs to be added to a specific project, project != null, BL exists
            Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier,username).getBacklog();
            //set the BL to PT
            projectTask.setBacklog(backlog);
            //we want our proejct sequence to be like this : IDPRO-1, ....
            Integer backlogSequence = backlog.getPTSquence();
            // Update the BL SEQUENCE
            backlogSequence++;

            backlog.setPTSquence(backlogSequence);

            //Add Sequence to Project Task
            projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            //INITIAL priority when priority null

            //INITIAL status when status is null
            if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
                projectTask.setStatus("TO_DO");
            }

            if ( projectTask.getPriority() == null || projectTask.getPriority()==0 ) {
                projectTask.setPriority(3);
            }

            return projectTaskRepository.save(projectTask);

    }

    public Iterable<ProjectTask> findBacklogById(String id,String username) {

        Project project = projectService.findProjectByIdentifier(id,username);
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

   public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id, String username){

       Project project = projectService.findProjectByIdentifier(backlog_id,username);
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);

        if(projectTask == null){
            throw new ProjectNotFoundException("Project Task '"+pt_id+"' not found");
        }

        if(!projectTask.getProjectIdentifier().equals(backlog_id)){
           throw new ProjectNotFoundException("Project Task '"+pt_id+"' does not exist in project : '"+backlog_id);
        }
        return projectTask;
   }

   public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id,String username){
        ProjectTask projectTask = findPTByProjectSequence(backlog_id,pt_id,username);
        projectTask = updatedTask;
        return projectTaskRepository.save(projectTask);
   }

   public void deletePTByProjectSequence(String backlog_id, String pt_id,String username){
        ProjectTask projectTask = findPTByProjectSequence(backlog_id,pt_id,username);
        projectTaskRepository.delete(projectTask);
   }


}
