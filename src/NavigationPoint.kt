import java.awt.Point

class NavigationPoint(parent: NavigationPoint?, x: Int, y: Int) {
    val parent = parent
    val position: Point = Point(x, y)
    val edges = arrayOfNulls<Edge>(8)

    constructor(x: Int, y: Int) : this(null, x, y)

    constructor(point: Point) : this(null, point.x, point.y)

    constructor(parent: NavigationPoint, point: Point) : this(parent, point.x, point.y)

    override fun toString(): String {
        return "[${position.x}:${position.y}]"
    }

    override fun equals(other: Any?): Boolean {
        if(other !is NavigationPoint) {
            return false
        }
        if((other as NavigationPoint).position.equals(position)) {
            return true
        }
        return false
    }
}