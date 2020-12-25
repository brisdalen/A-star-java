import java.awt.Point
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs
import kotlin.math.max

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
     */
    private fun A_Star(input: Array<CharArray>) {
        val copy = arrayOfNulls<CharArray>(input.size)
        for (i in input.indices) {
            copy[i] = input[i].clone()
        }
        val len = input.size
        val startingPos = NavigationPoint(6, 6)
        startingPos.setG(0);
        val goalPos = NavigationPoint(len - 3, 2)

        val closedSet = HashSet<NavigationPoint>()
        val openSet = PriorityQueue(NavigationPointComparator())

        if (len > 3) {
            copy[startingPos.position.y]!![startingPos.position.x] = 'C'
            copy[goalPos.position.y]!![goalPos.position.x] = 'o'
            for (i in 0 until len / 2 + 4) {
                copy[i]!![len / 2 - 1] = 'X'
                copy[i]!![len / 2] = 'X'
            }
        }
        display(copy)
        openSet.add(startingPos)
        while (!openSet.isEmpty() && startingPos != goalPos) {
            val q = openSet.poll()
            val neighbours = getSurroundingNodes(q, goalPos, input)
            for(np in neighbours) {
                println("" + np.position + " - " + np.f)
            }
        }
    }

    private fun add(p1: Point, p2: Point): Point {
        val sum = Point(p1.x + p2.x, p1.y + p2.y)
        return sum
    }

    private fun getSurroundingNodes(centre: NavigationPoint, goal: NavigationPoint, input: Array<CharArray>): List<NavigationPoint> {
        val toReturn = ArrayList<NavigationPoint>()
        for(p in surrounding) {
            val point = Point(add(centre.position, p))
            if(isValid(point, input)) {
                val toAdd = NavigationPoint(centre, point)
                toAdd.setH(diagonalDistance(toAdd.position, goal.position))
                toReturn.add(toAdd)
            }
        }
        return toReturn
    }

    private fun isValid(check: Point, input: Array<CharArray>): Boolean {
        return check.x >= 0 && check.x < input.size
                && check.y >= 0 && check.y < input.size
                && input[check.y][check.x] != 'X' // TODO: funker ikke
    }

    private fun diagonalDistance(current: Point, goal: Point): Int {
        return max(abs(current.x - goal.x),
                abs(current.y - goal.y))
    }

    private fun direction(origin: Point, lookingAt: Point): Direction? {
        var direction: Direction? = null
        if(origin.equals(lookingAt)) {
            return direction
        }
        if(origin.y > lookingAt.y) { // Top
            if(origin.x > lookingAt.x) {
                direction = Direction.NORTHWEST
            } else if(origin.x == lookingAt.x) {
                direction =  Direction.NORTH
            } else {
                direction =  Direction.NORTHEAST
            }
        } else if(origin.y == lookingAt.y) { // Middle
            if(origin.x > lookingAt.x) {
                direction = Direction.WEST
            } else {
                direction = Direction.EAST
            }
        } else { // Bottom
            if(origin.x > lookingAt.x) {
                direction = Direction.SOUTHWEST
            } else if(origin.x == lookingAt.x) {
                direction = Direction.SOUTH
            } else {
                direction = Direction.SOUTHEAST
            }
        }


        return direction
    }

    fun display(grid: Array<CharArray?>) {
        for (ca in grid) {
            val builder = StringBuilder()
            for (c in ca!!) {
                builder.append(if (c == 'X') "X" else if (c == 'C') "C" else c.toString()).append(" ")
            }
            println(builder.toString())
        }
    }

    internal inner class NavigationPointComparator : Comparator<NavigationPoint> {
        override fun compare(o1: NavigationPoint, o2: NavigationPoint): Int {
            return if (o1.f - o2.f < 0) -1 else if (o1.f == o2.f) 0 else 1
        }
    }

    companion object {
        // 3x3 area to check the surrounding area of a NavigationPoint
        @JvmStatic val surrounding: Array<Point> = arrayOf(
                Point(-1, -1), // Top
                Point(0, -1),
                Point(1, -1),
                Point(-1, 0), // Middle
                Point(1, 0),
                Point(-1, 1), // Bottom
                Point(0, 1),
                Point(1, 1)
        )

        @JvmStatic fun main(args: Array<String>) {
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