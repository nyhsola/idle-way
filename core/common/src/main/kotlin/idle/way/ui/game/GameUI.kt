package idle.way.ui.game

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.signals.Signal
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ScalingViewport
import com.badlogic.gdx.utils.viewport.Viewport
import idle.way.component.render.StageRenderComponent
import idle.way.event.EventContext
import idle.way.event.EventQueue
import idle.way.service.CommonResources
import idle.way.ui.service.UIService.Companion.MENU_ENABLE
import idle.way.util.UIUtil
import org.koin.core.annotation.Single
import org.koin.java.KoinJavaComponent

@Single
class GameUI : Entity() {
    private val commonResources: CommonResources by KoinJavaComponent.inject(CommonResources::class.java)
    private val eventQueue: EventQueue by KoinJavaComponent.inject(EventQueue::class.java)
    private val spriteBatch: SpriteBatch by KoinJavaComponent.inject(SpriteBatch::class.java)
    private val viewport: Viewport by KoinJavaComponent.inject(ScalingViewport::class.java)
    private val stage = UIUtil.createStage(viewport, spriteBatch)
    private val stageRenderComponent: StageRenderComponent = StageRenderComponent(stage).also { this.add(it) }
    private val rootContainer = Container<Table>()
    private val rootTable = Table()
    private val castle: Castle by KoinJavaComponent.inject(Castle::class.java)
    private val mine: Mine by KoinJavaComponent.inject(Mine::class.java)
    private val sawMill: SawMill by KoinJavaComponent.inject(SawMill::class.java)
    private val farm: Farm by KoinJavaComponent.inject(Farm::class.java)
    private val panelResources: PanelResources by KoinJavaComponent.inject(PanelResources::class.java)
    private val signal = Signal<EventContext>()

    var debugEnabled: Boolean = false
        set(value) {
            stageRenderComponent.stage.isDebugAll = value
            field = value
        }

    init {
        signal.add(eventQueue)
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
        stack.add(buildings())
        stack.add(escButton())
        return container
    }

    private fun escButton(): Container<out Group> {
        val button = TextButton("ESC", commonResources.skin)
        button.color.a = 0.75f

        button.addCaptureListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                signal.dispatch(EventContext(MENU_ENABLE))
            }
        })

        val table = Table()
        val container = Container(table).fill()
        table.add(button)
            .height(Value.percentHeight(0.05f, container))
            .width(Value.percentWidth(0.05f, container))
            .pad(5f)
            .align(Align.topLeft)
            .expand()
        return container
    }

    private fun panelResources(): Container<out Group> {
        val table = Table()
        val container = Container(table).fill()
        table.add(panelResources)
            .width(Value.percentWidth(0.25f, container))
            .align(Align.top)
            .expand()
        return container
    }

    private fun buildings(): Container<out Group> {
        val table = Table()
        val container = Container(table).fill()
        table.add(castle())
            .width(Value.percentWidth(0.25f, container))
        table.add(mine())
            .width(Value.percentWidth(0.25f, container))
        table.add(sawmill())
            .width(Value.percentWidth(0.25f, container))
        table.add(farm())
            .width(Value.percentWidth(0.25f, container))
        return container
    }

    private fun mine(): Container<Table> {
        val table = Table()
        val container = Container(table).fill()
        table.add(mine)
            .width(Value.percentWidth(0.25f, container))
            .align(Align.center)
            .expand()
        return container
    }

    private fun sawmill(): Container<Table> {
        val table = Table()
        val container = Container(table).fill()
        table.add(sawMill)
            .width(Value.percentWidth(0.25f, container))
            .align(Align.center)
            .expand()
        return container
    }

    private fun farm(): Container<Table> {
        val table = Table()
        val container = Container(table).fill()
        table.add(farm)
            .width(Value.percentWidth(0.25f, container))
            .align(Align.center)
            .expand()
        return container
    }

    private fun castle(): Container<Table> {
        val table = Table()
        val container = Container(table).fill()
        table.add(castle)
            .width(Value.percentWidth(0.25f, container))
            .align(Align.center)
            .expand()
        return container
    }
}