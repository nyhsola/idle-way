package idle.way.service

import idle.way.util.Task
import org.koin.core.annotation.Single

@Single
class MineService {
    private var stoneIncome = 1f
    private var level = 1
    private var workersCount = 1
    private var stoneCount = 0f
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
        stoneCount += stoneIncome
    }

    fun upgradeMine() {
        level++
        updateIncome()
    }

    fun addWorker() {
        workersCount++
        updateIncome()
    }

    private fun updateIncome() {
        stoneIncome += workersCount * level * 0.1f
    }

    fun getTimeLeft() = task.timeLeft
    fun getTimeSpawn() = task.interval
    fun getIncomeStone() = stoneIncome
    fun getLevel() = level
    fun getWorkersCount() = workersCount
    fun getStoneCount() = stoneCount
}