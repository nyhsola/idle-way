package idle.way.ui.menu

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.signals.Signal
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import idle.way.component.render.StageRenderComponent
import idle.way.event.EventContext
import idle.way.event.EventQueue
import idle.way.service.CommonResources
import idle.way.system.GameManagerSystem

class MenuUI(
    stage: Stage,
    private val commonResources: CommonResources,
    eventQueue: EventQueue
) : Entity() {
    private val stageRenderComponent: StageRenderComponent = StageRenderComponent(stage).also { this.add(it) }
    private val rootContainer = Container<Table>()
    private val rootTable = Table()
    private val signal = Signal<EventContext>()

    var isVisible: Boolean = false
        set(value) {
            rootContainer.isVisible = value
            field = value
        }

    init {
        signal.add(eventQueue)
        rootTable.add(main()).grow()
        rootContainer.setFillParent(true)
        rootContainer.fill()
        rootContainer.pad(10f)
        rootContainer.actor = rootTable
        stageRenderComponent.stage.addActor(rootContainer)
        isVisible = false
    }

    private fun main(): Container<out Group> {
        val table = Table()
        val container = Container<Table>().fill()
        table.add(label())
        table.row()
        table.add(exitButton()).width(100f).height(50f)
        container.actor = table
        return container
    }

    private fun label() : Label {
        val button = Label("Game not paused!", commonResources.skin)
        button.color.a = 0.75f
        return button
    }

    private fun exitButton() : TextButton {
        val button = TextButton("Exit", commonResources.skin)
        button.color.a = 0.75f
        button.addCaptureListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                signal.dispatch(EventContext(GameManagerSystem.EXIT_GAME))
            }
        })
        return button
    }
}