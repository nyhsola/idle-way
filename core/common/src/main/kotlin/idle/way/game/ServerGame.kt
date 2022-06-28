package idle.way.game

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.ScalingViewport
import idle.way.config.GameModule
import idle.way.event.EventContext
import idle.way.event.EventQueue
import ktx.app.KtxGame
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent
import org.koin.ksp.generated.module
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
        val defaultModule = module {
            single { PooledEngine() }
            single { SpriteBatch() }
            single {
                ScalingViewport(
                    Scaling.stretch,
                    Gdx.graphics.width.toFloat(),
                    Gdx.graphics.height.toFloat(),
                    OrthographicCamera()
                )
            }
        }

        startKoin {
            modules(defaultModule)
            modules(gameModule.module)
        }

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