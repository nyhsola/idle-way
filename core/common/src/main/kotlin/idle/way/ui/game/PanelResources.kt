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
class PanelResources : Table() {
    private val eventQueue: EventQueue by KoinJavaComponent.inject(EventQueue::class.java)
    private val commonResources: CommonResources by KoinJavaComponent.inject(CommonResources::class.java)
    private val playerService: PlayerService by KoinJavaComponent.inject(PlayerService::class.java)

    private val workersTemplate
        get() = "Workers: ${playerService.getWorkers()}"
    private val workersLevelTemplate
        get() = "Upgrade (${playerService.getCastleLevel()} lvl):"
    private val workersIncomeTemplate
        get() = "${playerService.getIncomeWorkers()} Worker(s) per second):"

    private val workerLabel = Label(workersTemplate, commonResources.skin)
    private val workersIncome = Label(workersIncomeTemplate, commonResources.skin)
    private val upgradeButton = TextButton(workersLevelTemplate, commonResources.skin)
    private val signal = Signal<EventContext>()

    init {
        signal.add(eventQueue)

        workerLabel.setAlignment(Align.center)
        workersIncome.setAlignment(Align.center)
        add(workerLabel).grow()
        row()
        add(workersIncome).grow()
        row()
        add(upgradeButton).grow()

        upgradeButton.addCaptureListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                signal.dispatch(EventContext(PlayerService.UPGRADE_CASTLE))
            }
        })

        workerLabel.color.a = 0.75f
        upgradeButton.color.a = 0.75f
    }

    override fun act(delta: Float) {
        workerLabel.setText(workersTemplate)
        upgradeButton.setText(workersLevelTemplate)
        workersIncome.setText(workersIncomeTemplate)
        super.act(delta)
    }
}