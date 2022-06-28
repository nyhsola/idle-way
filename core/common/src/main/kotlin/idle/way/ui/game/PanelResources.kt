package idle.way.ui.game

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import idle.way.service.*
import org.koin.core.annotation.Single
import org.koin.java.KoinJavaComponent

@Single
class PanelResources : Table() {
    private val commonResources: CommonResources by KoinJavaComponent.inject(CommonResources::class.java)

    private val castleService: CastleService by KoinJavaComponent.inject(CastleService::class.java)
    private val mineService: MineService by KoinJavaComponent.inject(MineService::class.java)
    private val sawMillService: SawMillService by KoinJavaComponent.inject(SawMillService::class.java)
    private val farmService: FarmService by KoinJavaComponent.inject(FarmService::class.java)

    private val workersCountTemplate
        get() = "Workers (Free): ${castleService.getWorkersCount()}"
    private val stoneCountTemplate
        get() = "Stone: ${String.format("%.2f", mineService.getStoneCount())}"
    private val woodCountTemplate
        get() = "Wood: ${String.format("%.2f", sawMillService.getWoodCount())}"
    private val wheatCountTemplate
        get() = "Wheat: ${String.format("%.2f", farmService.getWheatCount())}"

    private val workersCountLabel = TextButton(workersCountTemplate, commonResources.skin)
    private val stoneCountLabel = TextButton(stoneCountTemplate, commonResources.skin)
    private val woodCountLabel = TextButton(woodCountTemplate, commonResources.skin)
    private val wheatCountLabel = TextButton(woodCountTemplate, commonResources.skin)

    init {
        add(workersCountLabel).grow()
        add(stoneCountLabel).grow()
        add(woodCountLabel).grow()
        add(wheatCountLabel).grow()
        workersCountLabel.color.a = 0.75f
        wheatCountLabel.color.a = 0.75f
        stoneCountLabel.color.a = 0.75f
        woodCountLabel.color.a = 0.75f
    }

    override fun act(delta: Float) {
        workersCountLabel.setText(workersCountTemplate)
        stoneCountLabel.setText(stoneCountTemplate)
        woodCountLabel.setText(woodCountTemplate)
        wheatCountLabel.setText(wheatCountTemplate)
        super.act(delta)
    }
}