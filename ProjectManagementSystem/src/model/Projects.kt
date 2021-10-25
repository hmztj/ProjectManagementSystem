package model

import java.util.*

data class Projects(
    val name: String,
    var tasks: ArrayList<Tasks>,

){
    lateinit var kotlinCriticalPath: ArrayList<Tasks>
    lateinit var scalaCriticalPath: ArrayList<Tasks>

    var kotlinCriticalCost: Int = 0
    var scalaCriticalCost: Int = 0
}