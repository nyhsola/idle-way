package idle.way.service

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils.Disposable
import org.koin.core.annotation.Single

@Single
class CommonResources : Disposable {
    private val assets2d = listOf("start.png")

    val skin: Skin = loadSkin()
    val textures: Map<String, Texture> = loadTextures()

    private fun loadSkin(): Skin {
        return Skin(Gdx.files.internal("ui/uiskin.json"))
    }

    private fun loadTextures(): Map<String, Texture> {
        val map = HashMap<String, Texture>()
        for (entry in assets2d) {
            map[entry] = Texture(Gdx.files.internal("assets2d/$entry"))
        }
        return map
    }

    override fun dispose() {
        skin.dispose()
        textures.forEach { it.value.dispose() }
    }
}