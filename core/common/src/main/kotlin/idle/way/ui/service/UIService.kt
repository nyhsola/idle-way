package idle.way.ui.service

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.ashley.signals.Signal
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.ScalingViewport
import com.badlogic.gdx.utils.viewport.Viewport
import idle.way.event.EventContext
import idle.way.event.EventQueue
import idle.way.service.CommonResources
import idle.way.ui.menu.MenuUI
import ktx.app.KtxInputAdapter
import org.koin.core.annotation.Single
import org.koin.java.KoinJavaComponent.inject

@Single
class UIService : KtxInputAdapter, Disposable {
    companion object {
        const val MENU_ENABLE = "MENU_ENABLE"
    }

    private val engine: Engine by inject(PooledEngine::class.java)
    private val eventQueue: EventQueue by inject(EventQueue::class.java)
    private val commonResources: CommonResources by inject(CommonResources::class.java)
    private val spriteBatch: SpriteBatch by inject(SpriteBatch::class.java)
    private val viewport: Viewport by inject(ScalingViewport::class.java)
    private val menuUI = MenuUI(createStage(), commonResources, eventQueue)

    private val signal = Signal<EventContext>()

    private fun createStage() = Stage(viewport, spriteBatch)

    fun init() {
        signal.add(eventQueue)
        engine.addEntity(menuUI)
    }

    fun update() {
        proceedEvents()
    }

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Input.Keys.ESCAPE) {
            signal.dispatch(EventContext(MENU_ENABLE))
        }
        return super.keyDown(keycode)
    }

    private fun proceedEvents() {
        eventQueue.proceed {
            when (it.eventType) {
                MENU_ENABLE -> {
                    menuUI.isVisible = !menuUI.isVisible
                    true
                }
                else -> false
            }
        }
    }

    override fun dispose() {
        engine.removeEntity(menuUI)
    }
}