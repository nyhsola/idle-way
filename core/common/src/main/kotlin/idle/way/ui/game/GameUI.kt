package idle.way.ui.game

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Stack
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.Value
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ScalingViewport
import com.badlogic.gdx.utils.viewport.Viewport
import idle.way.component.render.StageRenderComponent
import idle.way.util.UIUtil
import org.koin.core.annotation.Single
import org.koin.java.KoinJavaComponent

@Single
class GameUI : Entity() {
    private val spriteBatch: SpriteBatch by KoinJavaComponent.inject(SpriteBatch::class.java)
    private val viewport: Viewport by KoinJavaComponent.inject(ScalingViewport::class.java)
    private val stage = UIUtil.createStage(viewport, spriteBatch)
    private val stageRenderComponent: StageRenderComponent = StageRenderComponent(stage).also { this.add(it) }
    private val rootContainer = Container<Table>()
    private val rootTable = Table()
    private val panelResources: PanelResources by KoinJavaComponent.inject(PanelResources::class.java)

    var debugEnabled: Boolean = false
        set(value) {
            stageRenderComponent.stage.isDebugAll = value
            field = value
        }

    init {
        rootTable.add(main()).grow()

        rootContainer.setFillParent(true)
        rootContainer.pad(10f)
        rootContainer.fill()
        rootContainer.actor = rootTable

        stageRenderComponent.stage.addActor(rootContainer)
    }

    private fun main(): Container<out Group> {
        val stack = Stack()
        val container = Container(stack).fill()
        stack.add(panelResources())
        return container
    }

//    private fun escButton(): Container<out Group> {
//        val button = TextButton("ESC", commonResources.skin)
//        button.color.a = 0.75f
//
//        button.addCaptureListener(object : ClickListener() {
//            override fun clicked(event: InputEvent, x: Float, y: Float) {
//                signal.dispatch(EventContext(MENU_ENABLE))
//            }
//        })
//
//        val table = Table()
//        val container = Container(table).fill()
//        table.add(button)
//            .height(Value.percentHeight(0.05f, container))
//            .width(Value.percentWidth(0.05f, container))
//            .pad(5f)
//            .align(Align.topLeft)
//            .expand()
//        return container
//    }

    private fun panelResources(): Container<Table> {
        val table = Table()
        val container = Container(table).fill()
        table.add(panelResources)
            .width(Value.percentWidth(0.25f, container))
            .align(Align.center)
            .expand()
        return container
    }
}