package castle.core.ui.service

import castle.core.event.EventContext
import castle.core.event.EventQueue
import castle.core.service.CommonResources
import castle.core.ui.menu.MenuUI
import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.signals.Signal
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.app.KtxInputAdapter

class UIService(
    private val engine: Engine,
    private val eventQueue: EventQueue,
    commonResources: CommonResources,
    private val spriteBatch: SpriteBatch,
    private val viewport: Viewport
) : KtxInputAdapter, Disposable {
    companion object {
        const val MENU_ENABLE = "MENU_ENABLE"
    }

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