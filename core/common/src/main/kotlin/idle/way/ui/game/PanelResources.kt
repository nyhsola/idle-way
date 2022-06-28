package idle.way.ui.game

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import idle.way.service.CommonResources
import idle.way.service.MineService
import idle.way.service.PlayerService
import org.koin.core.annotation.Single
import org.koin.java.KoinJavaComponent

@Single
class PanelResources : Table() {
    private val commonResources: CommonResources by KoinJavaComponent.inject(CommonResources::class.java)

    private val playerService: PlayerService by KoinJavaComponent.inject(PlayerService::class.java)
    private val mineService: MineService by KoinJavaComponent.inject(MineService::class.java)

    private val workersCountTemplate
        get() = "Workers (Free): ${playerService.getWorkersCount()}"
    private val stoneCountTemplate
        get() = "Stone: ${String.format("%.2f", mineService.getStoneCount())}"

    private val workersCountLabel = TextButton(workersCountTemplate, commonResources.skin)
    private val stoneCountLabel = TextButton(stoneCountTemplate, commonResources.skin)

    init {
        add(workersCountLabel).grow()
        add(stoneCountLabel).grow()
        workersCountLabel.color.a = 0.75f
        stoneCountLabel.color.a = 0.75f
    }

    override fun act(delta: Float) {
        workersCountLabel.setText(workersCountTemplate)
        stoneCountLabel.setText(stoneCountTemplate)
        super.act(delta)
    }
}