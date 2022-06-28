package idle.way.service

import idle.way.util.Task
import org.koin.core.annotation.Single

@Single
class MineService {
    private var stoneIncome = 1f
    private var level = 1
    private var workersCount = 1
    private var stoneCount = 0f

    private val task: Task = object : Task(1f) {
        override fun action() {
            incomePerTimeSpawn()
        }
    }

    fun update(deltaTime: Float) {
        task.update(deltaTime)
    }

    private fun incomePerTimeSpawn() {
        stoneCount += workersCount * level * 0.1f
    }

    fun upgradeMine() {
        level++
    }

    fun addWorker() {
        workersCount++
    }

    fun getTimeLeft() = task.timeLeft
    fun getTimeSpawn() = task.interval
    fun getIncomeStone() = stoneIncome
    fun getLevel() = level
    fun getWorkersCount() = workersCount
    fun getStoneCount() = stoneCount
}