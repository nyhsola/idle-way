package idle.way.config

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Disposable
import idle.way.service.CommonResources
import org.koin.core.annotation.ComponentScan
import org.koin.java.KoinJavaComponent.inject

@org.koin.core.annotation.Module
@ComponentScan("idle.way")
class GameModule : Disposable {
    private val spriteBatch: SpriteBatch by inject(SpriteBatch::class.java)
    private val commonResources: CommonResources by inject(CommonResources::class.java)

    override fun dispose() {
        spriteBatch.dispose()
        commonResources.dispose()
    }
}