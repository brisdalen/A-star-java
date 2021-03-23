import java.awt.Point

class NavigationPoint(parent: NavigationPoint?, x: Int, y: Int, v: Variation) {
    val parent = parent
    val position: Point = Point(x, y)
    val variation = v
    val edges = arrayOfNulls<Edge>(8)

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

    override fun hashCode(): Int {
        var result = parent?.hashCode() ?: 0
        result = 31 * result + position.hashCode()
        result = 31 * result + variation.hashCode()
        result = 31 * result + edges.contentHashCode()
        return result
    }
}