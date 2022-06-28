package idle.way.system.render

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import org.koin.core.annotation.Single

@Single
class RenderSystem : EntitySystem() {
    override fun update(deltaTime: Float) {
        Gdx.gl.apply {
            glClearColor(0.3f, 0.3f, 0.3f, 1f)
            glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
        }
    }
}