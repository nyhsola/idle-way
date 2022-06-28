package idle.way.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.systems.IntervalSystem
import com.badlogic.gdx.Gdx
import idle.way.event.EventQueue
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
    private val eventQueue: EventQueue by inject(EventQueue::class.java)

    override fun addedToEngine(engine: Engine) {
        uiService.init()
    }

    override fun updateInterval() {
        uiService.update()
        proceedEvents()
    }

    private fun proceedEvents() {
        eventQueue.proceed {
            when (it.eventType) {
                EXIT_GAME -> {
                    Gdx.app.postRunnable(Gdx.app::exit)
                    true
                }
                else -> false
            }
        }
    }

    override fun keyDown(keycode: Int): Boolean {
        return uiService.keyDown(keycode)
    }

    override fun dispose() {
        uiService.dispose()
    }
}