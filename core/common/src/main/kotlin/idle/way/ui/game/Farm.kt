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
import idle.way.service.FarmService
import idle.way.service.PlayerService
import org.koin.core.annotation.Single
import org.koin.java.KoinJavaComponent

@Single
class Farm : Table() {
    private val eventQueue: EventQueue by KoinJavaComponent.inject(EventQueue::class.java)
    private val commonResources: CommonResources by KoinJavaComponent.inject(CommonResources::class.java)
    private val farmService: FarmService by KoinJavaComponent.inject(FarmService::class.java)

    private val headerTemplate
        get() = "Farm Level ${farmService.getLevel()}"
    private val workersCountLabelTemplate
        get() = "Workers (Assigned): ${farmService.getWorkersCount()}"
    private val wheatIncomeLabelTemplate
        get() = "${String.format("%.2f", farmService.getIncome())} Stone per ${String.format("%.2f", farmService.getTimeSpawn())} sec"
    private val farmLevelButtonTemplate
        get() = "Upgrade: (${farmService.getLevel() + 1} lvl)"
    private val assignWorkerButtonTemplate
        get() = "Assign worker x1"

    private val headerLabel = Label(headerTemplate, commonResources.skin)
    private val workersCountLabel = Label(workersCountLabelTemplate, commonResources.skin)
    private val farmIncomeLabel = Label(wheatIncomeLabelTemplate, commonResources.skin)
    private val farmLevelButton = TextButton(farmLevelButtonTemplate, commonResources.skin)
    private val assignWorkerButton = TextButton(assignWorkerButtonTemplate, commonResources.skin)
    private val incomeBar = ProgressBar(0f, 100f, 1f, false, commonResources.skin)
    private val signal = Signal<EventContext>()

    init {
        signal.add(eventQueue)

        headerLabel.setAlignment(Align.center)
        workersCountLabel.setAlignment(Align.center)
        farmIncomeLabel.setAlignment(Align.center)

        add(headerLabel).grow()
        row()
        add(workersCountLabel).grow()
        row()
        add(farmIncomeLabel).grow()
        row()
        add(assignWorkerButton).grow()
        row()
        add(farmLevelButton).grow()
        row()
        add(incomeBar).grow()

        farmLevelButton.addCaptureListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                signal.dispatch(EventContext(PlayerService.UPGRADE_FARM))
            }
        })

        assignWorkerButton.addCaptureListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                signal.dispatch(EventContext(PlayerService.ASSIGN_WORKER_FARM))
            }
        })

        headerLabel.color.a = 0.75f
        workersCountLabel.color.a = 0.75f
        farmIncomeLabel.color.a = 0.75f
        assignWorkerButton.color.a = 0.75f
        farmLevelButton.color.a = 0.75f
    }

    override fun act(delta: Float) {
        headerLabel.setText(headerTemplate)
        workersCountLabel.setText(workersCountLabelTemplate)
        farmIncomeLabel.setText(wheatIncomeLabelTemplate)
        farmLevelButton.setText(farmLevelButtonTemplate)
        incomeBar.value = 100 - 100 * (farmService.getTimeLeft() / farmService.getTimeSpawn())
        super.act(delta)
    }
}