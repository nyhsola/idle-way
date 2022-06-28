package idle.way.service

import idle.way.event.EventQueue
import org.koin.core.annotation.Single
import org.koin.java.KoinJavaComponent

@Single
class MineService {

    companion object {
        const val UPGRADE_MINE = "UPGRADE_MINE"
    }

    private val eventQueue: EventQueue by KoinJavaComponent.inject(EventQueue::class.java)
    var accumulate: Float = 0f

    private var timeSpawn = 1f
    private var incomeStone = 1.0
    private var mineLevel = 1
    private var numberOfWorkers = 1

    fun update(deltaTime: Float) {
        proceedEvents()

        accumulate += deltaTime

        while (accumulate > 0) {
            accumulate -= timeSpawn
            incomePerSec()
        }
    }

    private fun proceedEvents() {
        eventQueue.proceed {
            when (it.eventType) {
                UPGRADE_MINE -> {
                    upgradeMine()
                    true
                }
                else -> false
            }
        }
    }

    private fun incomePerSec() {
        incomeStone += numberOfWorkers * mineLevel * 0.1
    }

    private fun upgradeMine() {
        mineLevel++
    }

    fun addWorker() {
        numberOfWorkers++
    }

    fun getIncomeStone() = incomeStone
    fun getMineLevelStone() = mineLevel
    fun getNumberOfWorkersStone() = numberOfWorkers

}