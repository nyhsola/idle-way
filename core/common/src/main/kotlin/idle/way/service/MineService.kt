package idle.way.service

import idle.way.event.EventContext
import idle.way.event.EventQueue
import idle.way.util.Task
import org.koin.core.annotation.Single
import org.koin.java.KoinJavaComponent

@Single
class MineService {
    companion object {
        const val UPGRADE_MINE = "UPGRADE_MINE"
    }

    private val eventQueue: EventQueue by KoinJavaComponent.inject(EventQueue::class.java)

    private var stoneIncome = 1f
    private var level = 1
    private var workersCount = 1
    private var stoneCount = 0f

    private val task: Task = object : Task(1f) {
        override fun action() {
            incomePerTimeSpawn()
        }
    }

    private val operation: (EventContext) -> Boolean = {
        when (it.eventType) {
            UPGRADE_MINE -> {
                upgradeMine()
                true
            }
            else -> false
        }
    }

    fun update(deltaTime: Float) {
        eventQueue.proceed(operation)
        task.update(deltaTime)
    }

    private fun incomePerTimeSpawn() {
        stoneCount += workersCount * level * 0.1f
    }

    private fun upgradeMine() {
        level++
    }

    fun addWorker() {
        workersCount++
    }

    fun getTimeSpawn() = task.interval
    fun getIncomeStone() = stoneIncome
    fun getLevel() = level
    fun getWorkersCount() = workersCount
    fun getStoneCount() = stoneCount
}