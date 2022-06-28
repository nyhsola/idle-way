package idle.way.service

import idle.way.event.EventContext
import idle.way.event.EventQueue
import org.koin.core.annotation.Single
import org.koin.java.KoinJavaComponent

@Single
class PlayerService {
    companion object {
        const val UPGRADE_CASTLE = "UPGRADE_CASTLE"
        const val ASSIGN_WORKER_MINE = "ASSIGN_WORKER_MINE"
        const val UPGRADE_MINE = "UPGRADE_MINE"
        const val ASSIGN_WORKER_SAWMILL = "ASSIGN_WORKER_SAWMILL"
        const val UPGRADE_SAWMILL = "UPGRADE_SAWMILL"
        const val ASSIGN_WORKER_FARM = "ASSIGN_WORKER_FARM"
        const val UPGRADE_FARM = "UPGRADE_FARM"
    }

    private val eventQueue: EventQueue by KoinJavaComponent.inject(EventQueue::class.java)
    private val mineService: MineService by KoinJavaComponent.inject(MineService::class.java)
    private val castleService: CastleService by KoinJavaComponent.inject(CastleService::class.java)
    private val sawMillService: SawMillService by KoinJavaComponent.inject(SawMillService::class.java)
    private val farmService: FarmService by KoinJavaComponent.inject(FarmService::class.java)


    private val operation: (EventContext) -> Boolean = {
        when (it.eventType) {
            UPGRADE_CASTLE -> {
                castleService.upgradeCastle()
                true
            }
            ASSIGN_WORKER_MINE -> {
                castleService.assignWorker()
                mineService.addWorker()
                true
            }
            UPGRADE_MINE -> {
                mineService.upgradeMine()
                true
            }
            ASSIGN_WORKER_SAWMILL -> {
                castleService.assignWorker()
                sawMillService.addWorker()
                true
            }
            UPGRADE_SAWMILL -> {
                sawMillService.upgradeSawMill()
                true
            }
            ASSIGN_WORKER_FARM -> {
                castleService.assignWorker()
                farmService.addWorker()
                true
            }
            UPGRADE_FARM -> {
                farmService.upgradeFarm()
                true
            }
            else -> false
        }
    }

    fun update(deltaTime: Float) {
        mineService.update(deltaTime)
        castleService.update(deltaTime)
        sawMillService.update(deltaTime)
        farmService.update(deltaTime)
        eventQueue.proceed(operation)
    }
}