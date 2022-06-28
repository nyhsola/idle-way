package idle.way.config

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.ScalingViewport
import idle.way.service.CommonResources
import org.koin.core.annotation.ComponentScan
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.inject
import org.koin.ksp.generated.module

@org.koin.core.annotation.Module
@ComponentScan("idle.way")
class GameModule : Disposable {
    private val spriteBatch: SpriteBatch by inject(SpriteBatch::class.java)
    private val commonResources: CommonResources by inject(CommonResources::class.java)
    private val defaultModule = module {
        single { PooledEngine() }
        single { ShapeRenderer() }
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

    fun start() {
        startKoin {
            modules(defaultModule)
            modules(module)
        }
    }

    override fun dispose() {
        spriteBatch.dispose()
        commonResources.dispose()
    }
}