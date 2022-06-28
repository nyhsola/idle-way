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
import idle.way.service.MineService
import idle.way.service.PlayerService
import org.koin.core.annotation.Single
import org.koin.java.KoinJavaComponent

@Single
class Mine : Table() {
    private val eventQueue: EventQueue by KoinJavaComponent.inject(EventQueue::class.java)
    private val commonResources: CommonResources by KoinJavaComponent.inject(CommonResources::class.java)
    private val mineService: MineService by KoinJavaComponent.inject(MineService::class.java)

    private val headerTemplate
        get() = "Mine Level ${mineService.getLevel()}"
    private val workersCountLabelTemplate
        get() = "Workers (Assigned): ${mineService.getWorkersCount()}"
    private val stoneIncomeLabelTemplate
        get() = "${mineService.getIncomeStone()} Stone per ${String.format("%.2f", mineService.getTimeSpawn())} sec"
    private val stoneLevelButtonTemplate
        get() = "Upgrade: (${mineService.getLevel() + 1} lvl)"
    private val assignWorkerButtonTemplate
        get() = "Assign worker x1"

    private val headerLabel = Label(headerTemplate, commonResources.skin)
    private val workersCountLabel = Label(workersCountLabelTemplate, commonResources.skin)
    private val stoneIncomeLabel = Label(stoneIncomeLabelTemplate, commonResources.skin)
    private val stoneLevelButton = TextButton(stoneLevelButtonTemplate, commonResources.skin)
    private val assignWorkerButton = TextButton(assignWorkerButtonTemplate, commonResources.skin)
    private val signal = Signal<EventContext>()

    init {
        signal.add(eventQueue)

        headerLabel.setAlignment(Align.center)
        workersCountLabel.setAlignment(Align.center)
        stoneIncomeLabel.setAlignment(Align.center)

        add(headerLabel).grow()
        row()
        add(workersCountLabel).grow()
        row()
        add(stoneIncomeLabel).grow()
        row()
        add(assignWorkerButton).grow()
        row()
        add(stoneLevelButton).grow()

        stoneLevelButton.addCaptureListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                signal.dispatch(EventContext(PlayerService.UPGRADE_MINE))
            }
        })

        assignWorkerButton.addCaptureListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                signal.dispatch(EventContext(PlayerService.ASSIGN_WORKER))
            }
        })

        headerLabel.color.a = 0.75f
        workersCountLabel.color.a = 0.75f
        stoneIncomeLabel.color.a = 0.75f
        assignWorkerButton.color.a = 0.75f
        stoneLevelButton.color.a = 0.75f
    }

    override fun act(delta: Float) {
        headerLabel.setText(headerTemplate)
        workersCountLabel.setText(workersCountLabelTemplate)
        stoneIncomeLabel.setText(stoneIncomeLabelTemplate)
        stoneLevelButton.setText(stoneLevelButtonTemplate)
        super.act(delta)
    }
}