package idle.way.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.systems.IntervalSystem
import com.badlogic.gdx.Gdx
import idle.way.event.EventContext
import idle.way.event.EventQueue
import idle.way.service.PlayerService
import idle.way.ui.service.UIService
import ktx.app.KtxInputAdapter
import ktx.app.KtxScreen
import org.koin.core.annotation.Single
import org.koin.java.KoinJavaComponent.inject

@Single
class GameManagerSystem : IntervalSystem(GAME_TICK), KtxInputAdapter, KtxScreen {
    companion object {
        private const val GAME_TICK: Float = 0.1f
        const val EXIT_GAME = "EXIT_GAME"
    }

    private val uiService: UIService by inject(UIService::class.java)
    private val playerService: PlayerService by inject(PlayerService::class.java)
    private val eventQueue: EventQueue by inject(EventQueue::class.java)

    private val operation: (EventContext) -> Boolean = {
        when (it.eventType) {
            EXIT_GAME -> {
                Gdx.app.postRunnable(Gdx.app::exit)
                true
            }
            else -> false
        }
    }

    override fun addedToEngine(engine: Engine) {
        uiService.init()
    }

    override fun update(deltaTime: Float) {
        playerService.update(deltaTime)
        super.update(deltaTime)
    }

    override fun updateInterval() {
        uiService.update()
        eventQueue.proceed(operation)
    }

    override fun keyDown(keycode: Int): Boolean {
        return uiService.keyDown(keycode)
    }

    override fun dispose() {
        uiService.dispose()
    }
}