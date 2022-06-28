package idle.way.game

import com.badlogic.ashley.signals.Signal
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import idle.way.event.EventContext
import idle.way.event.EventQueue
import idle.way.service.CommonResources
import ktx.app.KtxInputAdapter
import ktx.app.KtxScreen
import org.koin.core.annotation.Single
import org.koin.java.KoinJavaComponent.inject

@Single
class StartScreen : KtxScreen, KtxInputAdapter {
    companion object {
        const val GAME_EVENT = "GAME_EVENT"
    }

    private val signal = Signal<EventContext>()
    private val spriteBatch: SpriteBatch by inject(SpriteBatch::class.java)
    private val eventQueue: EventQueue by inject(EventQueue::class.java)
    private val commonResources: CommonResources by inject(CommonResources::class.java)
    private val texture = commonResources.textures.getValue("start.png")

    private val totalDuration = 2f
    private var accumulated = 0f

    init {
        signal.add(eventQueue)
        setAlpha(spriteBatch, 0f)
    }

    override fun render(delta: Float) {
        Gdx.gl.apply {
            glClearColor(0.3f, 0.3f, 0.3f, 1f)
            glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
        }

        accumulated += delta
        val alpha = (accumulated / totalDuration)

        if (alpha <= 1f) {
            setAlpha(spriteBatch, alpha)
        }

        val widthScale: Float = Gdx.graphics.width / 1920f
        val heightScale: Float = Gdx.graphics.height / 1080f

        spriteBatch.begin()
        spriteBatch.draw(texture, 0f, 0f, texture.width * widthScale, texture.height * heightScale);
        spriteBatch.end()
    }

    override fun show() {
        Gdx.input.inputProcessor = this
    }

    override fun hide() {
        Gdx.input.inputProcessor = null
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        signal.dispatch(EventContext(GAME_EVENT))
        return super.touchDown(screenX, screenY, pointer, button)
    }

    override fun keyDown(keycode: Int): Boolean {
        signal.dispatch(EventContext(GAME_EVENT))
        return super.keyDown(keycode)
    }

    private fun setAlpha(spriteBatchL: SpriteBatch, alpha: Float) {
        spriteBatchL.setColor(spriteBatchL.color.r, spriteBatchL.color.g, spriteBatchL.color.b, alpha)
    }
}