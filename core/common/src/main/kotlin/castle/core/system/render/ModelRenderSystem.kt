package castle.core.system.render

import castle.core.component.PositionComponent
import castle.core.component.render.ModelRenderComponent
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20

class ModelRenderSystem : IteratingSystem(Family.all(PositionComponent::class.java, ModelRenderComponent::class.java).get()) {
    override fun update(deltaTime: Float) {
        Gdx.gl.apply {
            glClearColor(0.3f, 0.3f, 0.3f, 1f)
            glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
        }
        super.update(deltaTime)
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
    }
}