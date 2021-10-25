package controller;

import model.Model;
import model.Projects;
import model.Scala;
import model.Tasks;
import view.View;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class Controller {

    private Projects project;
    private Tasks selectedTask;
    final private View view;
    private final Model model;
    final private Scala scala;
    private ArrayList<String> teamsList;
    private ArrayList<Projects> projectsList;
    private ArrayList<Tasks> tasksList = new ArrayList<>();
    final private ArrayList<Tasks> tempTasksList = new ArrayList<>();

    public Controller(View view, Model model, Scala scala) {

        this.view = view;
        this.model = model;
        this.scala = scala;

        this.projectsList = this.model.loadProjectsFromFile();
        this.teamsList = this.model.loadTeamsFromFile();
        this.view.loadProjectDetailsIntoTextArea(null);
        this.view.updateProjectNames(this.model.loadProjectsFromFile());
        this.view.updateTeamsList(this.model.loadTeamsFromFile());

        this.view.addNewTaskListener(new addTask());
        this.view.addNewTeamListener(new addTeam());
        this.view.deleteTeamListener(new deleteTeam());
        this.view.createProjectsListener(new createProjects());
        this.view.projectsComboListener(new projectsComboListener());
        this.view.taskDetailsListener(new taskDetailsListener());
        this.view.updateTaskListener(new updateTaskButton());
        this.view.addTaskInExistingProject(new addTaskInExistingProject());
        this.view.completeTask(new completeTask());

    }

    class addTask implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {

            int taskDuration;
            String taskName, predecessor, team;

            try {

                taskDuration = view.getTaskDuration("create");
                taskName = view.getInputTaskName("create");
                predecessor = view.getSelectedTask("create");
                team = view.getTeamName("create");

                if (!taskName.isEmpty() && !view.getInputProjectName().isEmpty() && !view.getTeamName("create").contains("Select")) {

                    tempTasksList.add(new Tasks(taskName, taskDuration, predecessor, team));
                    view.addTaskIntoList(taskName);
                    view.resetInputFields("task");

                } else {

                    view.showError("Please fill all the required fields");
                }


            } catch (NumberFormatException exception) {

                view.resetInputFields("duration");
                view.showError("Please fill all the required fields (including selecting a team)" +
                        " and type a valid number for task duration.");

            }

        }
    }

    class createProjects implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {

            projectsList.add(new Projects(view.getInputProjectName(), tempTasksList));

            model.saveProjects(projectsList);
            projectsList = model.loadProjectsFromFile();

            view.updateProjectNames(projectsList);
            tempTasksList.clear();
            view.resetInputFields("project");

        }
    }

    class addTeam implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {

            teamsList.add(view.getInputTeamName());

            model.saveTeams(teamsList);
            teamsList = model.loadTeamsFromFile();

            view.updateTeamsList(teamsList);

        }
    }

    class deleteTeam implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {


            teamsList.remove(view.getSelectedTeamFromTheList());

            model.saveTeams(teamsList);
            teamsList = model.loadTeamsFromFile();

            view.updateTeamsList(teamsList);


        }
    }

    class projectsComboListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {


            if (!view.getSelectedProject().contains("SELECT") ) {

                projectsList = model.loadProjectsFromFile();

                for (Projects it : projectsList) {

                    if (it.getName().equals(view.getSelectedProject())) {

                        project = it;
                        tasksList = project.getTasks();

                        view.updateTasksListForSelectedProject(tasksList);

                        project.setKotlinCriticalCost(model.calculateCriticalCost(project));
                        project.setKotlinCriticalPath(model.getCriticalPath(tasksList));
                        project.setScalaCriticalCost(scala.calculateCriticalCost(project));
                        project.setScalaCriticalPath(scala.getCriticalPath(project.getTasks()));

                        view.loadProjectDetailsIntoTextArea(project);
                        break;

                    }

                }


            }else if(view.getSelectedProject().contains("SELECT")){

                view.resetInputFields("list");
                view.loadProjectDetailsIntoTextArea(null);
            }

        }
    }

    class taskDetailsListener implements ListSelectionListener {


        @Override
        public void valueChanged(ListSelectionEvent e) {


            if (tasksList != null) {

                for (Tasks it : tasksList) {

                    if (it.getName().equals(view.getSelectedTask("edit"))) {

                        selectedTask = it;

                        view.loadTaskDetailsIntoTextArea(selectedTask);
                        view.loadTaskDetailsIntoFields(selectedTask);
                        break;

                    }
                }

            }

        }

    }

    class updateTaskButton implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {

            if (selectedTask != null && view.getSelectedTask("edit")!= null && !view.getTeamName("edit").contains("Select")) {


                tasksList.add(new Tasks(view.getInputTaskName("edit"), view.getTaskDuration("edit"), selectedTask.getPredecessor(), view.getTeamName("edit")));

                tasksList.remove(selectedTask);
                project.setTasks(tasksList);

                project.setKotlinCriticalCost(model.calculateCriticalCost(project));
                project.setKotlinCriticalPath(model.getCriticalPath(project.getTasks()));

                project.setScalaCriticalCost(scala.calculateCriticalCost(project));
                project.setScalaCriticalPath(scala.getCriticalPath(project.getTasks()));

                model.saveProjects(projectsList);

                view.updateTasksListForSelectedProject(project.getTasks());
                view.loadProjectDetailsIntoTextArea(project);

                view.resetInputFields("edit");


            }else if (view.getTeamName("edit").contains("Select")){

                view.showError("Please select a team");

            }else{

                view.showError("Please select a task to edit");
            }

        }
    }

    class addTaskInExistingProject implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {

            if(!view.getTeamName("edit").contains("Select")) {

                tasksList.add(new Tasks(view.getInputTaskName("edit"), view.getTaskDuration("edit"), view.getSelectedTask("edit"), view.getTeamName("edit")));

                project.setTasks(tasksList);
                project.setKotlinCriticalCost(model.calculateCriticalCost(project));
                project.setKotlinCriticalPath(model.getCriticalPath(tasksList));

                project.setScalaCriticalCost(scala.calculateCriticalCost(project));
                project.setScalaCriticalPath(scala.getCriticalPath(project.getTasks()));

                model.saveProjects(projectsList);

                view.updateTasksListForSelectedProject(project.getTasks());
                view.loadProjectDetailsIntoTextArea(project);

                view.resetInputFields("edit");

            }else{

                view.showError("Please select a team");
            }

        }
    }

    class completeTask implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {

            tasksList.get(tasksList.indexOf(selectedTask)).setDuration(0);
            tasksList.get(tasksList.indexOf(selectedTask)).setEarlyFinishTime(0);
            tasksList.get(tasksList.indexOf(selectedTask)).setEarlyFinishTime(0);
            tasksList.get(tasksList.indexOf(selectedTask)).setTaskStatus("Completed");
            tasksList.get(tasksList.indexOf(selectedTask)).setPredecessor(null);


            project.setTasks(tasksList);
            project.setKotlinCriticalCost(model.calculateCriticalCost(project));
            project.setKotlinCriticalPath(model.getCriticalPath(project.getTasks()));

            project.setScalaCriticalCost(scala.calculateCriticalCost(project));
            project.setScalaCriticalPath(scala.getCriticalPath(project.getTasks()));

            model.saveProjects(projectsList);

            view.updateTasksListForSelectedProject(project.getTasks());
            view.loadProjectDetailsIntoTextArea(project);

        }
    }


}
