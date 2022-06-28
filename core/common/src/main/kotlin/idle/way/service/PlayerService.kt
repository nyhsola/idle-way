package idle.way.service

import idle.way.event.EventQueue
import org.koin.java.KoinJavaComponent

class PlayerService {

    companion object {
        const val UPGRADE_CASTLE = "UPGRADE_CASTLE"
        const val ASSIGN_WORKER = "ASSIGN_WORKER"
    }

    private val eventQueue: EventQueue by KoinJavaComponent.inject(EventQueue::class.java)
    var accumulate: Float = 0f

    private var timeSpawn = 1f
    private var workersCount = 1
    private var incomeWorkers = 1
    private var castleLevel = 1

    fun update(deltaTime: Float) {
        proceedEvents()

        accumulate += deltaTime

        while (accumulate > 0) {
            accumulate -= timeSpawn
            updatePerSec()
        }
    }

    private fun proceedEvents() {
        eventQueue.proceed {
            when (it.eventType) {
                UPGRADE_CASTLE -> {
                    upgradeCastle()
                    true
                }
                else -> false
            }
        }
    }

    fun updatePerSec() {
        changeWorkers(incomeWorkers)
    }

    fun upgradeCastle() {
        castleLevel++
        incomeWorkers = 1 + castleLevel
        //timeSpawn = (180/(castleLevel + 5) + 30).toFloat()
    }


    private fun changeWorkers(numberOfWorkers: Int) {
        workersCount += numberOfWorkers
    }

    fun getIncomeWorkers() = incomeWorkers
    fun getCastleLevel() = castleLevel
    fun getWorkers() = workersCount
}