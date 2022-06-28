package idle.way.service

import idle.way.util.Task

abstract class Building {
    private var income = 1f
    private var level = 1
    private var workersCount = 1
    private var resourceCount = 0f
    private var timeSpawn = 1f

    private val task: Task = object : Task(timeSpawn) {
        override fun action() {
            resourceCount += income
        }
    }

    fun update(deltaTime: Float) {
        task.update(deltaTime)
    }

    fun upgradeBuilding() {
        level++
        updateIncome()
    }

    fun addWorker() {
        workersCount++
        updateIncome()
    }

    private fun updateIncome() {
        income += workersCount * level * 0.1f
    }

    fun getTimeLeft() = task.timeLeft
    fun getTimeSpawn() = task.interval
    fun getIncome() = income
    fun getLevel() = level
    fun getWorkersCount() = workersCount
    fun getResourcesCount() = resourceCount
}