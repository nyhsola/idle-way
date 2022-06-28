package idle.way.ui.game

import com.badlogic.ashley.signals.Signal
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import idle.way.event.EventContext
import idle.way.event.EventQueue
import idle.way.service.CommonResources
import idle.way.service.PlayerService
import idle.way.service.SawMillService
import org.koin.core.annotation.Single
import org.koin.java.KoinJavaComponent

@Single
class SawMill : Table() {
    private val eventQueue: EventQueue by KoinJavaComponent.inject(EventQueue::class.java)
    private val commonResources: CommonResources by KoinJavaComponent.inject(CommonResources::class.java)
    private val sawMillService: SawMillService by KoinJavaComponent.inject(SawMillService::class.java)

    private val headerTemplate
        get() = "Sawmill Level ${sawMillService.getLevel()}"
    private val workersCountLabelTemplate
        get() = "Workers (Assigned): ${sawMillService.getWorkersCount()}"
    private val woodIncomeLabelTemplate
        get() = "${String.format("%.2f", sawMillService.getIncome())} Wood per ${String.format("%.2f", sawMillService.getTimeSpawn())} sec"
    private val woodLevelButtonTemplate
        get() = "Upgrade: (${sawMillService.getLevel() + 1} lvl)"
    private val assignWorkerButtonTemplate
        get() = "Assign worker x1"

    private val headerLabel = Label(headerTemplate, commonResources.skin)
    private val workersCountLabel = Label(workersCountLabelTemplate, commonResources.skin)
    private val woodIncomeLabel = Label(woodIncomeLabelTemplate, commonResources.skin)
    private val woodLevelButton = TextButton(woodLevelButtonTemplate, commonResources.skin)
    private val assignWorkerButton = TextButton(assignWorkerButtonTemplate, commonResources.skin)
    private val incomeBar = ProgressBar(0f, 100f, 1f, false, commonResources.skin)
    private val signal = Signal<EventContext>()

    init {
        signal.add(eventQueue)

        headerLabel.setAlignment(Align.center)
        workersCountLabel.setAlignment(Align.center)
        woodIncomeLabel.setAlignment(Align.center)

        add(headerLabel).grow()
        row()
        add(workersCountLabel).grow()
        row()
        add(woodIncomeLabel).grow()
        row()
        add(assignWorkerButton).grow()
        row()
        add(woodLevelButton).grow()
        row()
        add(incomeBar).grow()

        woodLevelButton.addCaptureListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                signal.dispatch(EventContext(PlayerService.UPGRADE_SAWMILL))
            }
        })

        assignWorkerButton.addCaptureListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                signal.dispatch(EventContext(PlayerService.ASSIGN_WORKER_SAWMILL))
            }
        })

        headerLabel.color.a = 0.75f
        workersCountLabel.color.a = 0.75f
        woodIncomeLabel.color.a = 0.75f
        assignWorkerButton.color.a = 0.75f
        woodLevelButton.color.a = 0.75f
    }

    override fun act(delta: Float) {
        headerLabel.setText(headerTemplate)
        workersCountLabel.setText(workersCountLabelTemplate)
        woodIncomeLabel.setText(woodIncomeLabelTemplate)
        woodLevelButton.setText(woodLevelButtonTemplate)
        incomeBar.value = 100 - 100 * (sawMillService.getTimeLeft() / sawMillService.getTimeSpawn())
        super.act(delta)
    }
}