import React, { Component } from 'react'
import ProjectTask from './ProjectTasks/ProjectTask'

class Backlog extends Component {
    render() {
        const { project_tasks_prop } = this.props;
        const tasks = project_tasks_prop.map(project_task => (
            <ProjectTask key={project_task.id} project_task={project_task} />
        ));
        let todoItems = tasks.filter(t => t.props.project_task.status === "TO_DO");
        let inProgressItems = tasks.filter(t => t.props.project_task.status === "IN_PROGRESS");
        let doneItems = tasks.filter(t => t.props.project_task.status === "DONE");

        return (
            <div className="container">
                <div className="row">
                    <div className="col-md-4">
                        <div className="card text-center mb-2">
                            <div className="card-header bg-secondary text-white">
                                <h3>TO DO</h3>
                            </div>
                            {todoItems}
                        </div>
                    </div>
                    <div className="col-md-4">
                        <div className="card text-center mb-2">
                            <div className="card-header bg-primary text-white">
                                <h3>In Progress</h3>
                            </div>
                            {inProgressItems}
                        </div>
                    </div>
                    <div className="col-md-4">
                        <div className="card text-center mb-2">
                            <div className="card-header bg-success text-white">
                                <h3>Done</h3>
                            </div>
                            {doneItems}
                        </div>
                    </div>
                </div>
            </div >
        )
    }
}

export default Backlog; 
