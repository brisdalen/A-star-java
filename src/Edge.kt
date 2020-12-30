class Edge(val start: NavigationPoint, val end: NavigationPoint) {
    var f: Int = 0
    private var g: Int = 0
    private var h: Int = 0
    // g = parent.g + 1 // successor.g = q.g + distance between successor and q; distance always 1

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

    override fun toString(): String {
        return "start: $start end: $end f:$f - g:$g h:$h"
    }
}