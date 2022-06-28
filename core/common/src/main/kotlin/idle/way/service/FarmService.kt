package idle.way.service

import idle.way.util.Task
import org.koin.core.annotation.Single

@Single
class FarmService {
    private var wheatIncome = 1f
    private var level = 1
    private var workersCount = 1
    private var wheatCount = 0f
    private var timeSpawn = 1f

    private val task: Task = object : Task(timeSpawn) {
        override fun action() {
            incomePerTimeSpawn()
        }
    }

    fun update(deltaTime: Float) {
        task.update(deltaTime)
    }

    private fun incomePerTimeSpawn() {
        wheatCount += wheatIncome
    }

    fun upgradeFarm() {
        level++
        updateIncome()
    }

    fun addWorker() {
        workersCount++
        updateIncome()
    }

    private fun updateIncome() {
        wheatIncome += workersCount * level * 0.1f
    }

    fun getTimeLeft() = task.timeLeft
    fun getTimeSpawn() = task.interval
    fun getIncomeWheat() = wheatIncome
    fun getLevel() = level
    fun getWorkersCount() = workersCount
    fun getWheatCount() = wheatCount
}