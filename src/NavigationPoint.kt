import java.awt.Point

class NavigationPoint(parent: NavigationPoint?, x: Int, y: Int) {
    val position: Point = Point(x, y)
    var f: Int
        private set

    private var g: Int
    private var h: Int

    constructor(x: Int, y: Int) : this(null, x, y)

    constructor(point: Point) : this(null, point.x, point.y)

    constructor(parent: NavigationPoint, point: Point) : this(parent, point.x, point.y) {
        g = parent.getG() + 1
    }

    fun setG(g: Int) {
        this.g = g
        f = g + h
    }

    fun setH(h: Int) {
        this.h = h
        f = g + h
    }

    fun getG(): Int {
        return g
    }

    fun getH(): Int {
        return h
    }

    init {
        f = 0
        g = 0
        h = 0
    }
}