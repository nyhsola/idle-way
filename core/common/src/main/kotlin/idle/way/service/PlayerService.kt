package idle.way.service

import idle.way.event.EventContext
import idle.way.event.EventQueue
import org.koin.core.annotation.Single
import org.koin.java.KoinJavaComponent

@Single
class PlayerService {
    companion object {
        const val UPGRADE_CASTLE = "UPGRADE_CASTLE"
        const val ASSIGN_WORKER = "ASSIGN_WORKER"
        const val UPGRADE_MINE = "UPGRADE_MINE"
    }

    private val eventQueue: EventQueue by KoinJavaComponent.inject(EventQueue::class.java)
    private val mineService: MineService by KoinJavaComponent.inject(MineService::class.java)
    private val castleService: CastleService by KoinJavaComponent.inject(CastleService::class.java)

    private val operation: (EventContext) -> Boolean = {
        when (it.eventType) {
            UPGRADE_CASTLE -> {
                castleService.upgradeCastle()
                true
            }
            ASSIGN_WORKER -> {
                castleService.assignWorker()
                mineService.addWorker()
                true
            }
            UPGRADE_MINE -> {
                mineService.upgradeMine()
                true
            }
            else -> false
        }
    }

    fun update(deltaTime: Float) {
        mineService.update(deltaTime)
        castleService.update(deltaTime)
        eventQueue.proceed(operation)
    }
}