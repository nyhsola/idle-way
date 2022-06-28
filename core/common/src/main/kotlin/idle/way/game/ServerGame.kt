package idle.way.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import idle.way.config.GameModule
import idle.way.event.EventContext
import idle.way.event.EventQueue
import ktx.app.KtxGame
import org.koin.java.KoinJavaComponent
import kotlin.math.min

class ServerGame : KtxGame<Screen>() {
    private val gameModule = GameModule()
    private val startScreen: StartScreen by KoinJavaComponent.inject(StartScreen::class.java)
    private val gameScreen: GameScreen by KoinJavaComponent.inject(GameScreen::class.java)
    private val eventQueue: EventQueue by KoinJavaComponent.inject(EventQueue::class.java)

    private val operation: (EventContext) -> Boolean = {
        when (it.eventType) {
            StartScreen.GAME_EVENT -> {
                setScreen<GameScreen>()
            }
        }
        false
    }

    override fun create() {
        gameModule.start()
        addScreen(startScreen)
        addScreen(gameScreen)
        setScreen<StartScreen>()
    }

    override fun render() {
        eventQueue.proceed(operation)
        currentScreen.render(min(1f / 30f, Gdx.graphics.deltaTime))
    }

    override fun dispose() {
        gameModule.dispose()
        super.dispose()
    }
}