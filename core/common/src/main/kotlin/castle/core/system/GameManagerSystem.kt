package castle.core.system

import castle.core.event.EventQueue
import castle.core.service.CameraService
import castle.core.ui.service.UIService
import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.systems.IntervalSystem
import com.badlogic.gdx.Gdx
import ktx.app.KtxInputAdapter
import ktx.app.KtxScreen

class GameManagerSystem(
    private val uiService: UIService,
    private val cameraService: CameraService,
    private val eventQueue: EventQueue
) : IntervalSystem(GAME_TICK), KtxInputAdapter, KtxScreen {
    companion object {
        private const val GAME_TICK: Float = 0.1f
        const val EXIT_GAME = "EXIT_GAME"
    }

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

    override fun resize(width: Int, height: Int) {
        cameraService.resize(width, height)
    }

    override fun dispose() {
        uiService.dispose()
    }
}