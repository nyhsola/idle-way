package idle.way.game

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import idle.way.config.ScreenConfig
import idle.way.system.GameManagerSystem
import idle.way.system.render.RenderSystem
import idle.way.system.render.StageRenderSystem
import ktx.app.KtxScreen
import org.koin.core.annotation.Single
import org.koin.java.KoinJavaComponent

@Single
class GameScreen : KtxScreen {
    private val engine: PooledEngine by KoinJavaComponent.inject(PooledEngine::class.java)
    private val stageRenderSystem: StageRenderSystem by KoinJavaComponent.inject(StageRenderSystem::class.java)
    private val renderSystem: RenderSystem by KoinJavaComponent.inject(RenderSystem::class.java)
    private val gameManagerSystem: GameManagerSystem by KoinJavaComponent.inject(GameManagerSystem::class.java)

    private val screenConfig by lazy {
        ScreenConfig(
            engine,
            linkedSetOf(
                renderSystem,
                stageRenderSystem,
                gameManagerSystem
            )
        )
    }

    private val inputMultiplexer = screenConfig.inputMultiplexer
    private val screens = screenConfig.screens
    private val disposables = screenConfig.disposables

    override fun resize(width: Int, height: Int) {
        screens.forEach { it.resize(width, height) }
    }

    override fun render(delta: Float) {
        engine.update(delta)
    }

    override fun show() {
        Gdx.input.inputProcessor = inputMultiplexer
    }

    override fun hide() {
        Gdx.input.inputProcessor = null
    }

    override fun dispose() {
        disposables.forEach { it.dispose() }
    }
}