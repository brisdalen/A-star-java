import java.awt.Point

class NavigationPoint(parent: NavigationPoint?, x: Int, y: Int) {
    val parent = parent
    val position: Point = Point(x, y)
    var f: Int

    private var g: Int
    private var h: Int

    constructor(x: Int, y: Int) : this(null, x, y)

    constructor(point: Point) : this(null, point.x, point.y)

    constructor(parent: NavigationPoint, point: Point) : this(parent, point.x, point.y) {
        g = parent.g + 1 // successor.g = q.g + distance between successor and q; distance always 1
    }

    fun setG(g: Int) {
        this.g = g
        f = g + h
    }

    fun setH(h: Int) {
        this.h = h
        f = g + h // successor.f = successor.g + successor.h
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

    override fun toString(): String {
        return "[${position.x}:${position.y}] f:$f - g:$g h:$h"
    }

    override fun equals(other: Any?): Boolean {
        if(!(other is NavigationPoint)) {
            return false
        }
        if((other as NavigationPoint).position.equals(position)) {
            return true
        }
        return false
    }
}