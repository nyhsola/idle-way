package idle.way.service

import idle.way.util.Task
import org.koin.core.annotation.Single

@Single
class CastleService {
    private var workersCount = 1
    private var incomeWorkers = 1
    private var level = 1

    private val task: Task = object : Task(1f) {
        override fun action() {
            incomePerTimeSpawn()
        }
    }

    fun update(deltaTime: Float) {
        task.update(deltaTime)
    }

    fun assignWorker() {
        workersCount -= 1
    }

    fun upgradeCastle() {
        level++
        incomeWorkers = 1 + level
    }

    private fun incomePerTimeSpawn() {
        workersCount += incomeWorkers
    }

    fun getTimeSpawn() = task.interval
    fun getIncomeWorkers() = incomeWorkers
    fun getLevel() = level
    fun getWorkersCount() = workersCount
}