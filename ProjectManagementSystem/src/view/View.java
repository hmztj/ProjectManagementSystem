package view;

import model.Projects;
import model.Tasks;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class View extends JFrame {

    private final JTextArea tab1_projectDetailsArea = new JTextArea();
    private final JComboBox<String> tab1_projectListBox = new JComboBox<>();
    private final JTextArea tab1_taskDetailsArea = new JTextArea();
    private final JButton tab1_completeTaskButton = new JButton("Mark as Completed");
    private final JPanel tab1_editTaskPanel = new JPanel();
    private final JTextField tab1_inputTaskName = new JTextField(20);
    private final JTextField tab1_inputTaskCostField = new JTextField(40);
    private final JButton tab1_updateTaskButton = new JButton("Update");
    private final JButton tab1_addTaskButton = new JButton("Add");
    private final JComboBox<String> tab1_teamsListBox = new JComboBox<>();
    /**
     * Tab 2 variables
     */
    private final JComboBox<String> tab3_teamsListBox = new JComboBox<>();
    private final JTextField tab2_inputTeamName = new JTextField(40);
    private final JButton tab2_addTeam = new JButton("Add Team");
    private final JButton tab2_deleteTeam = new JButton("Delete Team");
    private final JTextField tab3_inputProjectName = new JTextField(20);
    private final JButton tab3_createProjectButton = new JButton("Create");
    private final JTextField tab3_inputTaskName = new JTextField(20);
    private final JTextField tab3_inputTaskCostField = new JTextField(40);
    private final JButton tab3_taskButton = new JButton("Add Task");
    private final DefaultListModel<String> tab1_taskListModel = new DefaultListModel<>();
    private final JList<String> tab1_tasksList = new JList<>(tab1_taskListModel);
    private final DefaultListModel<String> tab2_teamsListModel = new DefaultListModel<>();
    private final JList<String> tab2_teamsList = new JList<>(tab2_teamsListModel);
    private final DefaultListModel<String> tab3_taskListModel = new DefaultListModel<>();
    private final JList<String> tab3_tasksList = new JList<>(tab3_taskListModel);

    /*
     * Main frame constructor
     */
    public View() {
        super("PMS");

        /*
         * set frame visibility and close operation
         */
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /*
         * grid bag constraints declaration and initialisation
         */
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        /*
         * tab 1 Source code
         * sets tab name and layout
         */
        /*
         * Tab 1 variables
         */
        JPanel tab_1 = new JPanel();
        tab_1.setName("Manage Projects");
        tab_1.setLayout(new BorderLayout());

        /*
         * North Panel contents (Project list combo box)
         */
        tab1_projectListBox.addItem("SELECT A PROJECT");
        tab1_projectListBox.setPreferredSize(new Dimension(450, 25));
        tab_1.add(tab1_projectListBox, BorderLayout.NORTH);


        /*
         * West Panel contents (Project Details text Area)
         */
        JScrollPane tab1_projectDetailScroll = new JScrollPane(tab1_projectDetailsArea);
        tab1_projectDetailScroll.setPreferredSize(new Dimension(230, 400));
        tab1_projectDetailScroll.setBorder(BorderFactory.createTitledBorder("Project Details"));
        tab_1.add(tab1_projectDetailScroll, BorderLayout.WEST);

        /*
         * Center Panel contents (task list, details and control buttons)
         */
        JPanel tab1_taskPanel = new JPanel();
        tab1_taskPanel.setPreferredSize(new Dimension(400, 400));
        tab1_taskPanel.setLayout(new BoxLayout(tab1_taskPanel, BoxLayout.Y_AXIS));

        JScrollPane tab1_taskScroll = new JScrollPane(tab1_tasksList);
        tab1_taskScroll.setBorder(BorderFactory.createTitledBorder("Tasks"));
        tab1_taskScroll.setPreferredSize(new Dimension(400, 300));

        /*
         * sets task details text area in scroll pane
         */
        JScrollPane tab1_taskDetailsScroll = new JScrollPane(tab1_taskDetailsArea);
        tab1_taskDetailsScroll.setPreferredSize(new Dimension(400, 180));
        tab1_taskDetailsScroll.setBorder(BorderFactory.createTitledBorder("Task Details"));

        /*
         * add components to the task Panel (center panel of the tab)
         */
        JButton tab1_addNewTaskButton = new JButton("Add Task");
        JPanel tab1_buttonPanel = new JPanel(new FlowLayout());
        tab1_buttonPanel.add(tab1_addNewTaskButton);
        JButton tab1_editTaskButton = new JButton("Edit Task");
        tab1_buttonPanel.add(tab1_editTaskButton);
        tab1_buttonPanel.add(tab1_completeTaskButton);

        tab1_taskPanel.add(tab1_taskScroll);
        tab1_taskPanel.add(tab1_taskDetailsScroll);
        tab1_taskPanel.add(tab1_buttonPanel);

        /*
         * add center panel to the tab
         */
        tab_1.add(tab1_taskPanel, BorderLayout.CENTER);

        /*
         * sets layout for editing panel and adds a border
         */
        tab1_editTaskPanel.setLayout(new GridBagLayout());
        tab1_editTaskPanel.setBorder(BorderFactory.createTitledBorder("Select a Task from the list above to Edit"));

        /*
         * sets component's attributes and coordinates for editing panel
         */

        /*
         * task Attributes
         */
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        JLabel tab1_taskNameLabel = new JLabel("Task: ");
        tab1_editTaskPanel.add(tab1_taskNameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.LAST_LINE_START;
        tab1_editTaskPanel.add(tab1_inputTaskName, gbc);

        /*
         * task Cost Attributes
         */
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        JLabel tab1_taskCostLabel = new JLabel("Duration (days): ");
        tab1_editTaskPanel.add(tab1_taskCostLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LAST_LINE_START;
        tab1_editTaskPanel.add(tab1_inputTaskCostField, gbc);

        /*
         * task teams attributes
         */
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        JLabel tab1_teamsLabel = new JLabel("Teams: ");
        tab1_editTaskPanel.add(tab1_teamsLabel, gbc);

        tab1_teamsListBox.addItem("SELECT A TEAM FOR THIS TASK");
        tab1_teamsListBox.setPreferredSize(new Dimension(450, 25));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LAST_LINE_START;
        tab1_editTaskPanel.add(tab1_teamsListBox, gbc);

        /*
         * Add/Update task button attributes
         */
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LAST_LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        tab1_editTaskPanel.add(tab1_updateTaskButton, gbc);

        tab1_addTaskButton.setVisible(false);
        tab1_addTaskButton.setPreferredSize(tab1_updateTaskButton.getPreferredSize());
        tab1_editTaskPanel.add(tab1_addTaskButton, gbc);

        /*
          adds south panel (editing panel) in the tab
         */
        tab_1.add(tab1_editTaskPanel, BorderLayout.SOUTH);

        /*
          Editing panel's control button actions listeners
         */
        tab1_addNewTaskButton.addActionListener((ActionEvent e) -> {

            tab1_addTaskButton.setVisible(true);
            tab1_updateTaskButton.setVisible(false);
            tab1_editTaskPanel.setBorder(BorderFactory.createTitledBorder("Select a Predecessor task from the list above (optional)"));
            tab1_inputTaskName.setText("");
            tab1_inputTaskCostField.setText("");

        });

        tab1_editTaskButton.addActionListener((ActionEvent e) -> {

            tab1_addTaskButton.setVisible(false);
            tab1_updateTaskButton.setVisible(true);
            tab1_editTaskPanel.setBorder(BorderFactory.createTitledBorder("Select a Task from the list above to Edit"));

        });


        /*
         *                                                TAB 2
         */

        JPanel tab_2 = new JPanel();
        tab_2.setName("Create Teams");

        JScrollPane tab2_teamScroll = new JScrollPane(tab2_teamsList);
        tab2_teamScroll.setPreferredSize(new Dimension(400, 450));

        JPanel ta2_teamsPanel = new JPanel();
        ta2_teamsPanel.setLayout(new GridBagLayout());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        JLabel tab2_teamNameLabel = new JLabel("Team Name: ");
        ta2_teamsPanel.add(tab2_teamNameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LAST_LINE_START;
        ta2_teamsPanel.add(tab2_inputTeamName, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        ta2_teamsPanel.add(tab2_addTeam, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        ta2_teamsPanel.add(tab2_teamScroll, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        ta2_teamsPanel.add(tab2_deleteTeam, gbc);


        tab_2.add(ta2_teamsPanel);

        /*
         *                                                TAB 3
         * sets tab name and layout
         */

        /*
         * tab 3 variables
         */
        JPanel tab_3 = new JPanel();
        tab_3.setName("Create Project");

        JPanel tab3_createProjectPanel = new JPanel();
        tab3_createProjectPanel.setLayout(new GridBagLayout());

        /*
          project Name Attributes
         */
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        JLabel tab3_projectNameLabel = new JLabel("Project Name: ");
        tab3_createProjectPanel.add(tab3_projectNameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LAST_LINE_START;
        tab3_createProjectPanel.add(tab3_inputProjectName, gbc);

        /*
         * Task Name attributes
         */
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        JLabel tab3_TaskNameLabel = new JLabel("Task: ");
        tab3_createProjectPanel.add(tab3_TaskNameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.LAST_LINE_START;
        tab3_createProjectPanel.add(tab3_inputTaskName, gbc);

        /*
         * task cost attributes
         */
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        JLabel tab3_taskCostLabel = new JLabel("Duration (days): ");
        tab3_createProjectPanel.add(tab3_taskCostLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LAST_LINE_START;
        tab3_createProjectPanel.add(tab3_inputTaskCostField, gbc);

        /*
          task add button
         */
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LAST_LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        tab3_createProjectPanel.add(tab3_taskButton, gbc);

        /*
          task teams attributes
         */
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        JLabel tab3_teamsLabel = new JLabel("Teams: ");
        tab3_createProjectPanel.add(tab3_teamsLabel, gbc);

        tab3_teamsListBox.addItem("SELECT A TEAM FOR THIS TASK");
        tab3_teamsListBox.setPreferredSize(new Dimension(450, 25));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LAST_LINE_START;
        tab3_createProjectPanel.add(tab3_teamsListBox, gbc);

        JScrollPane tab3_taskListScroll = new JScrollPane(tab3_tasksList);
        tab3_taskListScroll.setPreferredSize(new Dimension(100, 350));
        tab3_taskListScroll.setBorder(BorderFactory.createTitledBorder("Select a predecessor task from the list below (Optional)"));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        tab3_createProjectPanel.add(tab3_taskListScroll, gbc);

        /*
          Create project Button
         */
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        tab3_createProjectPanel.add(tab3_createProjectButton, gbc);

        /*
          add tab 3's content into the tab.
         */
        tab_3.add(tab3_createProjectPanel);

        /*
          add all the tabs into tab pane
         */
        JTabbedPane tabs = new JTabbedPane();
        tabs.add(tab_1);
        tabs.add(tab_2);
        tabs.add(tab_3);
        add(tabs);

        pack();

    }

    /*                                                 Tab 1 Methods                                                 */

    /*
     * load all the projects names into the combo box
     *
     * @param projects
     */
    public void updateProjectNames(ArrayList<Projects> projects) {

        projects.forEach(project -> tab1_projectListBox.removeItem(project.getName()));
        projects.forEach(project -> tab1_projectListBox.addItem(project.getName()));

    }

    /*
     * returns selected project name from the comboBox
     *
     * @return
     */
    public String getSelectedProject() {

        return (String) tab1_projectListBox.getSelectedItem();

    }

    /*
     * loads selected project's details into the text Area
     *
     * @param project
     */
    public void loadProjectDetailsIntoTextArea(Projects project) {

        if (project != null) {
            tab1_projectDetailsArea.setText("");
            tab1_projectDetailsArea.setText("ProjectName: " + "\n\t" + project.getName() + "\n" +
                    "\nCritical Cost(days) Kotlin: \n\n\t" + "|¬ " + project.getKotlinCriticalCost() + "\n" +
                    "\nCritical Cost(days)  Scala: \n\n\t" + "|¬ " + project.getScalaCriticalCost() + "\n" +
                    "\nCritical Path: \n"
            );

            if(project.getScalaCriticalCost()==0){

                tab1_projectDetailsArea.setText("");
                tab1_projectDetailsArea.setText("ProjectName: " + "\n\t" + project.getName() + "\n" +
                        "\nProject Status:\n\tProject Completed");

            }
            else {
                project.getKotlinCriticalPath();

                ArrayList<Tasks> sTasks = project.getScalaCriticalPath();
                int index = 0;

                tab1_projectDetailsArea.append("Kotlin " + "   |   " +" Scala  " + "\n" );
                for (Tasks task : project.getKotlinCriticalPath()){

                    if(task!=null) {
                        tab1_projectDetailsArea.append(  task.getName() + "   |   " +  sTasks.get(index).getName() + "\n" );
                    }else{
                        tab1_projectDetailsArea.append("");
                    }
                    index++;
                }
            }
        }

    }

    /*
     * load tasks list for the selected project.
     *
     * @param tasks
     */
    public void updateTasksListForSelectedProject(ArrayList<Tasks> tasks) {

        tab1_taskListModel.removeAllElements();
        tasks.forEach(task -> tab1_taskListModel.addElement(task.getName()));

    }


    /*
     * loads selected task's details into text Area
     *
     * @param task
     */
    public void loadTaskDetailsIntoTextArea(Tasks task) {

        String predecessor = task.getPredecessor() == null ? "this is a base task" : task.getPredecessor();

        if (task.getTaskStatus().equals("Incomplete")) {
            tab1_taskDetailsArea.setText("Task Name: " + task.getName() + "\n" +
                    "Task Status: " + task.getTaskStatus() + "\n" +
                    "predecessor: " + predecessor + "\n" +
                    "Assigned Team: " + task.getTeam() + "\n" +
                    "Task Duration: " + task.getDuration() + " days" + "\n" +
                    "Early Start Date: " + task.getEarlyStartTime() + "   Early Finish Date: " + task.getEarlyFinishTime()
            );
        } else {

            tab1_taskDetailsArea.setText("Task Name: " + task.getName() + "\n" +
                    "Task Status: " + task.getTaskStatus() + "\n" +
                    "predecessor: " + predecessor + "\n" +
                    "Assigned Team: " + task.getTeam() + "\n"
            );

        }
    }

    /*
     * load selected task attributes in the edit area.
     *
     * @param task
     */
    public void loadTaskDetailsIntoFields(Tasks task) {

        tab1_inputTaskName.setText(task.getName());
        tab1_inputTaskCostField.setText(Integer.toString(task.getDuration()));

    }

    /*
     * returns input from the team name text field
     *
     * @return
     */
    public String getInputTeamName() {

        return tab2_inputTeamName.getText();

    }

    /*
     * return team name based on the team type
     *
     * @param team_type - determines the combo box to get the requested team from.
     * @return
     */
    public String getTeamName(String team_type) {

        switch (team_type) {

            case "edit":
                return (String) tab1_teamsListBox.getSelectedItem();
            case "create":
                return (String) tab3_teamsListBox.getSelectedItem();
            default:
                return null;

        }

    }

    /*
     * returns the selected team name from JList
     *
     * @return
     */
    public String getSelectedTeamFromTheList() {

        return tab2_teamsList.getSelectedValue();

    }

    /*
     * updates the teams list from the file into JList and ComboBoxes
     *
     * @param teams
     */
    public void updateTeamsList(ArrayList<String> teams) {


        tab2_teamsListModel.removeAllElements();
        tab1_teamsListBox.removeAllItems();
        tab3_teamsListBox.removeAllItems();
        tab1_teamsListBox.addItem("Select a Team for This Task");
        tab3_teamsListBox.addItem("Select a Team for This Task");
        teams.forEach(tab1_teamsListBox::addItem);
        teams.forEach(tab3_teamsListBox::addItem);
        for (String team : teams) tab2_teamsListModel.addElement(team);

    }

    /*
     * returns input from project name field
     *
     * @return
     */
    public String getInputProjectName() {

        return tab3_inputProjectName.getText();

    }

    /*
     * returns input from task field
     *
     * @param task_type - determines which field to get the input from.
     * @return
     */
    public String getInputTaskName(String task_type) {

        switch (task_type) {

            case "edit":
                return tab1_inputTaskName.getText();
            case "create":
                return tab3_inputTaskName.getText();
            default:
                return null;
        }

    }

    /*
     * returns selected task(String) from the list
     *
     * @return - determines list type
     */
    public String getSelectedTask(String taskType) {

        switch (taskType) {
            case "edit":
                return tab1_tasksList.getSelectedValue();
            case "create":
                return tab3_tasksList.getSelectedValue();
            default:
                return "";
        }

    }


    /*
     * returns task duration based on the task type
     *
     * @param duration_type - determines which field to get the input from
     *                      returns 0 if passed parameter is incorrect
     * @return
     */
    public int getTaskDuration(String duration_type) {

        switch (duration_type) {

            case "edit":
                return Integer.parseInt(tab1_inputTaskCostField.getText());
            case "create":
                return Integer.parseInt(tab3_inputTaskCostField.getText());
            default:
                return 0;

        }

    }

    /*
     * add the new task into the JList
     *
     * @param taskName
     */
    public void addTaskIntoList(String taskName) {

        tab3_taskListModel.addElement(taskName);

    }

    /*
     * reset all input fields based on the field type
     *
     * @param fieldType
     */
    public void resetInputFields(String fieldType) {

        switch (fieldType) {
            case "project":

                tab3_inputProjectName.setText("");
                tab3_inputTaskCostField.setText("");
                tab3_inputTaskName.setText("");
                tab3_taskListModel.removeAllElements();
                break;
            case "task":

                tab3_inputTaskCostField.setText("");
                tab3_inputTaskName.setText("");
                tab3_tasksList.clearSelection();

                break;
            case "duration":

                tab3_inputTaskCostField.setText("");

                break;
            case "edit":

                tab1_inputTaskCostField.setText("");
                tab1_inputTaskName.setText("");
                tab1_teamsListBox.setSelectedIndex(0);
                break;
            case "list":

                tab1_taskListModel.removeAllElements();
                break;
        }

    }

    public void addNewTaskListener(ActionListener e) {

        tab3_taskButton.addActionListener(e);

    }

    public void addNewTeamListener(ActionListener e) {

        tab2_addTeam.addActionListener(e);

    }

    public void deleteTeamListener(ActionListener e) {

        tab2_deleteTeam.addActionListener(e);
    }

    public void createProjectsListener(ActionListener e) {

        tab3_createProjectButton.addActionListener(e);
    }

    public void projectsComboListener(ActionListener e) {

        tab1_projectListBox.addActionListener(e);

    }

    public void taskDetailsListener(ListSelectionListener e) {

        tab1_tasksList.addListSelectionListener(e);

    }

    public void updateTaskListener(ActionListener e) {

        tab1_updateTaskButton.addActionListener(e);
    }

    public void addTaskInExistingProject(ActionListener e) {

        tab1_addTaskButton.addActionListener(e);
    }

    public void completeTask(ActionListener e) {

        tab1_completeTaskButton.addActionListener(e);

    }

    public void showError(String s) {

        JOptionPane.showMessageDialog(this, s);
    }


}


