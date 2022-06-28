package idle.way.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.gdx.math.Matrix4

class PositionComponent(
    val nodeName: String,
    val matrix4: Matrix4
) : Component {
    companion object {
        val mapper: ComponentMapper<PositionComponent> = ComponentMapper.getFor(PositionComponent::class.java)
    }

    fun setMatrix(positionComponent: PositionComponent) {
        matrix4.set(positionComponent.matrix4)
    }
}