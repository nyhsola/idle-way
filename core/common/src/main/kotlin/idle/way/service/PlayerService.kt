package idle.way.service

import idle.way.event.EventContext
import idle.way.event.EventQueue
import idle.way.util.Task
import org.koin.core.annotation.Single
import org.koin.java.KoinJavaComponent

@Single
class PlayerService {
    companion object {
        const val UPGRADE_CASTLE = "UPGRADE_CASTLE"
        const val ASSIGN_WORKER = "ASSIGN_WORKER"
    }

    private val eventQueue: EventQueue by KoinJavaComponent.inject(EventQueue::class.java)
    private val mineService: MineService by KoinJavaComponent.inject(MineService::class.java)

    private var workersCount = 1
    private var incomeWorkers = 1
    private var level = 1

    private val task: Task = object : Task(1f) {
        override fun action() {
            incomePerTimeSpawn()
        }
    }

    private val operation: (EventContext) -> Boolean = {
        when (it.eventType) {
            UPGRADE_CASTLE -> {
                upgradeCastle()
                true
            }
            ASSIGN_WORKER -> {
                assignWorker()
                true
            }
            else -> false
        }
    }

    private fun assignWorker() {
        mineService.addWorker()
        workersCount -= 1
    }

    fun update(deltaTime: Float) {
        mineService.update(deltaTime)
        eventQueue.proceed(operation)
        task.update(deltaTime)
    }

    private fun incomePerTimeSpawn() {
        workersCount += incomeWorkers
    }

    private fun upgradeCastle() {
        level++
        incomeWorkers = 1 + level
    }

    fun getTimeSpawn() = task.interval
    fun getIncomeWorkers() = incomeWorkers
    fun getLevel() = level
    fun getWorkersCount() = workersCount
}