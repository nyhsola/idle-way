package idle.way.util

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.Viewport

class UIUtil {
    companion object {
        fun createStage(viewport: Viewport, spriteBatch: SpriteBatch) = Stage(viewport, spriteBatch)
    }
}