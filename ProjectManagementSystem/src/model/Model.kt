package model

import com.google.gson.Gson
import java.io.File
import java.io.FileWriter

class Model {

    private var gson = Gson()


    private var projects: File = File("project.json")
    private var teams: File = File("teams.json")
    private var finalTask: Tasks? = null

    private fun calculateEarlyStartAndFinish(project: Projects) {

        // Early finish time = ES + duration
        project.tasks.forEach {

            if (it.taskStatus == "Completed") {

                it.earlyStartTime = 0
                it.earlyFinishTime = 0
                it.duration = 0
                it.predecessor = null

            }
            if (it.predecessor.isNullOrEmpty()) {
                it.earlyFinishTime = it.earlyStartTime + it.duration
            } else {
                // Early start time = early finish time of predecessor
                val pTask: Tasks? = getPredecessorTask(it.predecessor, project.tasks)
                if (pTask != null) {
                    it.earlyStartTime = pTask.earlyFinishTime
                    it.earlyFinishTime = it.earlyStartTime + it.duration
                }
            }
        }
    }

    fun calculateCriticalCost(project: Projects): Int {

        var criticalCost = 0
        calculateEarlyStartAndFinish(project)

        project.tasks.forEach {
            if (criticalCost < it.earlyFinishTime) {
                criticalCost = it.earlyFinishTime
                finalTask = it
            }
        }
        return criticalCost
    }

    fun getCriticalPath(tasks: ArrayList<Tasks>): ArrayList<Tasks?> {

        val criticalPathList: ArrayList<Tasks?> = ArrayList()

        criticalPathList.add(finalTask)

        while (finalTask?.predecessor != null) {

            finalTask = getPredecessorTask(finalTask?.predecessor, tasks)
            if (finalTask?.taskStatus != "Completed") {
                criticalPathList.add(finalTask)
            }

        }
        finalTask = null
        return criticalPathList

    }

    private fun getPredecessorTask(taskName: String?, tasks: ArrayList<Tasks>): Tasks? {

        tasks.forEach {
            if (it.name == taskName) return it
        }
        return null
    }

    // Lambda expression to store data in Json file
    var saveProjectsLambda: (ArrayList<Projects>) -> Unit = { projectsList: ArrayList<Projects> ->
        val writer = FileWriter(projects, false)
        projectsList.forEach {

            val jsonData: String = gson.toJson(it)
            writer.write(jsonData + "\n")

        }
        writer.close()

    }

    fun saveProjects(project: ArrayList<Projects>) {

        saveProjectsLambda(project)

    }

    fun loadProjectsFromFile(): ArrayList<Projects> {

        val project: ArrayList<Projects> = ArrayList()
        if (projects.exists()) {

            projects.forEachLine { project.add(gson.fromJson(it, Projects::class.java)) }

            return project

        }
        return project

    }

    var saveTeamsLambda: (ArrayList<String>) -> Unit = { teamsList: ArrayList<String> ->

        val writer = FileWriter(teams, false)

        teamsList.forEach {

            val jsonData: String = gson.toJson(it)
            writer.write(jsonData + "\n")

        }
        writer.close()
    }

    fun saveTeams(teamsList: ArrayList<String>) {

        saveTeamsLambda(teamsList)

    }

    fun loadTeamsFromFile(): ArrayList<String> {

        val team: ArrayList<String> = ArrayList()

        if (teams.exists()) {

            teams.forEachLine { team.add(gson.fromJson(it, String::class.java)) }
            return team
        }
        return team

    }


}