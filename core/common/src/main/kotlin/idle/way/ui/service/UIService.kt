package idle.way.ui.service

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.ashley.signals.Signal
import com.badlogic.gdx.Input
import com.badlogic.gdx.utils.Disposable
import idle.way.event.EventContext
import idle.way.event.EventQueue
import idle.way.ui.game.GameUI
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
    private val menuUI: MenuUI by inject(MenuUI::class.java)
    private val gameUI: GameUI by inject(GameUI::class.java)
    private val signal = Signal<EventContext>()
    private val operation: (EventContext) -> Boolean = {
        when (it.eventType) {
            MENU_ENABLE -> {
                menuUI.isVisible = !menuUI.isVisible
                true
            }
            else -> false
        }
    }

    fun init() {
        signal.add(eventQueue)
        engine.addEntity(gameUI)
        engine.addEntity(menuUI)
    }

    fun update() {
        eventQueue.proceed(operation)
    }

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Input.Keys.ESCAPE) {
            signal.dispatch(EventContext(MENU_ENABLE))
        }
        return super.keyDown(keycode)
    }

    override fun dispose() {
        engine.removeEntity(gameUI)
        engine.removeEntity(menuUI)
    }
}