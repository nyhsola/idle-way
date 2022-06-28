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

    private val map: Map<String, () -> Unit> = hashMapOf(
        Pair(UPGRADE_CASTLE) {
            castleService.upgradeCastle()
        },
        Pair(ASSIGN_WORKER_MINE) {
            castleService.assignWorker()
            mineService.addWorker()
        },
        Pair(UPGRADE_MINE) {
            mineService.upgradeBuilding()
        },
        Pair(ASSIGN_WORKER_SAWMILL) {
            castleService.assignWorker()
            sawMillService.addWorker()
        },
        Pair(UPGRADE_SAWMILL) {
            sawMillService.upgradeBuilding()
        },
        Pair(ASSIGN_WORKER_FARM) {
            castleService.assignWorker()
            farmService.addWorker()
        },
        Pair(UPGRADE_FARM) {
            farmService.upgradeBuilding()
        }
    )

    private val operation: (EventContext) -> Boolean = {
        val function = map[it.eventType]
        function?.invoke()
        function != null
    }

    fun update(deltaTime: Float) {
        mineService.update(deltaTime)
        castleService.update(deltaTime)
        sawMillService.update(deltaTime)
        farmService.update(deltaTime)
        eventQueue.proceed(operation)
    }
}