import java.awt.Point
import java.util.*

class Main {
    /*
    What A* Search Algorithm does is that at each step it picks the node according to a value-‘f’
    which is a parameter equal to the sum of two other parameters – ‘g’ and ‘h’.
    At each step it picks the node/cell having the lowest ‘f’, and process that node/cell.

    g = the movement cost to move from the starting point to a given square on the grid,
    following the path generated to get there.

    h = the estimated movement cost to move from that given square on the grid to the final destination.
    This is often referred to as the heuristic, which is nothing but a kind of smart guess.
    We really don’t know the actual distance until we find the path, because all sorts of things can be in the
    way (walls, water, etc.).
    There can be many ways to calculate this ‘h’ which are discussed in the later sections.
     */
    private fun A_Star(input: Array<CharArray>) {
        val copy = arrayOfNulls<CharArray>(input.size)
        for (i in input.indices) {
            copy[i] = input[i].clone()
        }
        val len = input.size
        val startingPos = NavigationPoint(2, 2)
        val goalPos = NavigationPoint(len - 3, 2)
        if (len > 3) {
            copy[startingPos.position.y]!![startingPos.position.x] = 'C'
            copy[goalPos.position.y]!![goalPos.position.x] = 'o'
            for (i in 0 until len / 2 + 4) {
                copy[i]!![len / 2 - 1] = 'X'
                copy[i]!![len / 2] = 'X'
            }
        }
        display(copy)
        val closedSet = HashSet<NavigationPoint>()
        val openSet = PriorityQueue(NavigationPointComparator()) // TODO: override Comparator
        openSet.add(startingPos)
        while (!openSet.isEmpty() && startingPos != goalPos) {
            val q = openSet.poll()
        }
    }

    private fun getSurroundingNodes(centre: NavigationPoint, input: Array<CharArray>): Array<NavigationPoint> {}
    fun display(grid: Array<CharArray?>) {
        for (ca in grid) {
            val builder = StringBuilder()
            for (c in ca!!) {
                builder.append(if (c == 'X') "X" else if (c == 'C') "C" else c.toString()).append(" ")
            }
            println(builder.toString())
        }
    }

    internal inner class NavigationPoint(private val parent: NavigationPoint?, x: Int, y: Int) {
        val position: Point
        var f: Double
            private set
        private var g: Double
        private var h: Double

        constructor(x: Int, y: Int) : this(null, x, y) {}

        fun setG(g: Double) {
            this.g = g
            f = g + h
        }

        fun setH(h: Double) {
            this.h = h
            f = g + h
        }

        fun getG(): Double {
            return g
        }

        fun getH(): Double {
            return h
        }

        init {
            position = Point(x, y)
            f = 0.0
            g = 0.0
            h = 0.0
        }
    }

    internal inner class NavigationPointComparator : Comparator<NavigationPoint> {
        override fun compare(o1: NavigationPoint, o2: NavigationPoint): Int {
            return if (o1.f - o2.f < 0) -1 else if (o1.f == o2.f) 0 else 1
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Main()
        }
    }

    init {
        val size = 16
        val grid = Array(size) { CharArray(size) }
        for (ca in grid) {
            Arrays.fill(ca, '.')
        }
        for (i in grid.indices) {
            grid[i][0] = 'X' // 'X' = obstacle
            grid[i][grid.size - 1] = 'X'
            grid[0][i] = 'X'
            grid[grid.size - 1][i] = 'X'
        }
        A_Star(grid)
    }
}