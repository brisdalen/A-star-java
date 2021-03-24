import java.awt.Point

class NavigationPoint(parent: NavigationPoint?, x: Int, y: Int, v: Variation) {
    val parent = parent
    val position: Point = Point(x, y)
    val variation = v
    var edges = arrayOfNulls<Edge>(8)

    constructor(x: Int, y: Int) : this(null, x, y, Variation.O)

    constructor(point: Point) : this(null, point.x, point.y, Variation.O)

    constructor(parent: NavigationPoint, point: Point) : this(parent, point.x, point.y, Variation.O)

    constructor(parent: NavigationPoint, point: Point, variation: Variation) : this(parent, point.x, point.y, variation)


    override fun toString(): String {
        return "[${position.x}:${position.y}]"
    }

    override fun equals(other: Any?): Boolean {
        if(other !is NavigationPoint) {
            return false
        }
        if(other.position.equals(position)) {
            return true
        }
        return false
    }
}