package idle.way.ui.game

import com.badlogic.ashley.signals.Signal
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import idle.way.event.EventContext
import idle.way.event.EventQueue
import idle.way.service.CommonResources
import idle.way.service.PlayerService
import org.koin.core.annotation.Single
import org.koin.java.KoinJavaComponent

@Single
class Castle : Table() {
    private val eventQueue: EventQueue by KoinJavaComponent.inject(EventQueue::class.java)
    private val commonResources: CommonResources by KoinJavaComponent.inject(CommonResources::class.java)
    private val playerService: PlayerService by KoinJavaComponent.inject(PlayerService::class.java)

    private val headerTemplate
        get() = "Castle Level ${playerService.getLevel()}"
    private val workersCountLabelTemplate
        get() = "Workers (Free): ${playerService.getWorkersCount()}"
    private val workersIncomeLabelTemplate
        get() = "${playerService.getIncomeWorkers()} Worker(s) per ${String.format("%.2f", playerService.getTimeSpawn())} sec"
    private val castleLevelButtonTemplate
        get() = "Upgrade: (${playerService.getLevel() + 1} lvl)"

    private val headerLabel = Label("Castle", commonResources.skin)
    private val workersCountLabel = Label(workersCountLabelTemplate, commonResources.skin)
    private val workersIncomeLabel = Label(workersIncomeLabelTemplate, commonResources.skin)
    private val castleLevelButton = TextButton(castleLevelButtonTemplate, commonResources.skin)
    private val signal = Signal<EventContext>()

    init {
        signal.add(eventQueue)

        headerLabel.setAlignment(Align.center)
        workersCountLabel.setAlignment(Align.center)
        workersIncomeLabel.setAlignment(Align.center)

        add(headerLabel).grow()
        row()
        add(workersCountLabel).grow()
        row()
        add(workersIncomeLabel).grow()
        row()
        add(castleLevelButton).grow()

        castleLevelButton.addCaptureListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                signal.dispatch(EventContext(PlayerService.UPGRADE_CASTLE))
            }
        })

        headerLabel.color.a = 0.75f
        workersCountLabel.color.a = 0.75f
        workersIncomeLabel.color.a = 0.75f
        castleLevelButton.color.a = 0.75f
    }

    override fun act(delta: Float) {
        headerLabel.setText(headerTemplate)
        workersCountLabel.setText(workersCountLabelTemplate)
        castleLevelButton.setText(castleLevelButtonTemplate)
        workersIncomeLabel.setText(workersIncomeLabelTemplate)
        super.act(delta)
    }
}