package model

import java.util
class Scala() {

  var finalTask: Tasks = _

  def calculateEarlyStartAndFinish(project: Projects): Unit = {
    // Early finish time = ES + duration
    val allTasks: util.ArrayList[Tasks] = project.getTasks

    allTasks.forEach { i => {

      if (i.getTaskStatus == "Completed") {

        i.setEarlyStartTime(0)
        i.setEarlyFinishTime(0)
        i.setDuration(0)
        i.setPredecessor(null)

      }
      if (i.getPredecessor == null || i.getPredecessor.isEmpty) {
        i.setEarlyFinishTime(i.getEarlyStartTime + i.getDuration)
      } else {
        val pTask: Tasks = getPredecessorTask(i.getPredecessor, allTasks)
        if (pTask != null) {
          i.setEarlyStartTime(pTask.getEarlyFinishTime)
          i.setEarlyFinishTime(i.getEarlyStartTime + i.getDuration)
        }
      }
    }
    }
  }


  def calculateCriticalCost(project: Projects): Int = {
    var criticalCost: Int = 0
    calculateEarlyStartAndFinish(project)
    project.getTasks.forEach(it => {
      if (criticalCost < it.getEarlyFinishTime) {
        criticalCost = it.getEarlyFinishTime
        finalTask = it
      }
    })
    criticalCost
  }

  def getCriticalPath(tasks: util.ArrayList[Tasks]): util.ArrayList[Tasks] = {
    val criticalPathList: util.ArrayList[Tasks] = new util.ArrayList[Tasks]()
    criticalPathList.add(finalTask)
    while (finalTask != null) {


      finalTask = getPredecessorTask(finalTask.getPredecessor, tasks)
      if(finalTask!=null){
      if (finalTask.getTaskStatus != "Completed"  ) {
        criticalPathList.add(finalTask)
      }}

    }
    finalTask = null
    criticalPathList
  }

  def getPredecessorTask(taskName: String, tasks: util.ArrayList[Tasks]): Tasks = {
    tasks.forEach(i => {
      if (i.getName.equals(taskName)) return i
    })
    null
  }

}
