package castle.core.config

import castle.core.service.CameraService
import castle.core.system.GameManagerSystem
import castle.core.system.render.ModelRenderSystem
import castle.core.system.render.StageRenderSystem
import castle.core.ui.service.UIService
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.ScalingViewport

class GameConfig(commonConfig: CommonConfig) : Disposable {
    private val scalingViewport = ScalingViewport(Scaling.stretch, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat(), OrthographicCamera())

    private val engine: PooledEngine = PooledEngine()

    private val eventQueue = commonConfig.eventQueue
    private val commonResources = commonConfig.commonResources

    private val modelBatch = ModelBatch()
    private val spriteBatch = commonConfig.spriteBatch

    private val uiService = UIService(engine, eventQueue, commonResources, spriteBatch, scalingViewport)
    private val cameraService = CameraService()
    private val stageRenderSystem = StageRenderSystem()
    private val modelRenderSystem = ModelRenderSystem()
    private val gameManagerSystem = GameManagerSystem(uiService, cameraService, eventQueue)

    val screenConfig = ScreenConfig(
        engine,
        linkedSetOf(
            modelRenderSystem,
            stageRenderSystem,
            gameManagerSystem
        )
    )

    override fun dispose() {
        modelBatch.dispose()
    }
}