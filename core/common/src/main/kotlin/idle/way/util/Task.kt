package idle.way.util

abstract class Task(var interval: Float) {
    var accumulate: Float = 0.0f

    fun update(delta: Float) {
        accumulate += delta
        while (accumulate >= interval) {
            accumulate -= interval
            action()
        }
    }

    fun reset() {
        accumulate = 0.0f
    }

    abstract fun action()
}