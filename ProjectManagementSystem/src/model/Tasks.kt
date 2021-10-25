package model

data class Tasks(val name: String, var duration: Int, var predecessor: String? = null, val team: String) {

    var earlyStartTime: Int = 0
    var earlyFinishTime: Int = 0
    var taskStatus: String = "Incomplete"

}