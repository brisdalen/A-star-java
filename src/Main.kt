import java.awt.Point
import java.lang.Exception
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
    private val gCostHandler: GCostHandler = GCostHandler()

    private fun pathAStar(input: Array<Array<NavigationPoint?>>): ArrayList<NavigationPoint>? {
        val startTime = System.currentTimeMillis()
//        val copy = arrayOfNulls<CharArray>(input.size)
        val copy = input.copyOf();
        for(i in input.indices) {
            copy[i] = input[i].clone()
        }
        val len = input.size
        val startingPos = NavigationPoint(2, 2)
        val goalPos = NavigationPoint(len - 3, 2)

        val openSet = PriorityQueue(EdgeComparator()) // 1 Initialize the open list
        val closedSet = HashSet<Edge>() // 2 Initialize the closed list

        if(len > 3) {
            //for (i in 0 until len / 2 + 4) {
            for(i in 1 until len / 2 + 4) {
                //    copy[i]!![len / 2 - 1] = 'X'
                //    copy[i]!![len / 2] = 'X'
                copy[i][len / 2]!!.variation = Variation.E
            }
        }

        val edges = getSurroundingEdges(startingPos, goalPos, copy)
        startingPos.edges = edges.toTypedArray()
        println(startingPos.edges)
        openSet.addAll(startingPos.edges) // put the starting node's edges on the open list

        while(!openSet.isEmpty()) { // 3.  while the open list is not empty
            val q = openSet.poll() // a) find the node with the least f on the open list, call it "q". b) pop q off the open list
            println(q)
            display(copy, q.start, goalPos)
            if(q.end == goalPos) {
                return returnPath(q.end, copy, startTime)
            }

            val neighbours = getSurroundingEdges(q.end, goalPos, copy) // c) generate q's 8 successors and set their parents to q
            for(ne in neighbours) { // d) for each successor
                if(ne.end == goalPos) { // i) if successor is the goal, stop search
                    return returnPath(ne.end, copy, startTime)
                }
                //                println("" + ne.end.position + " - " + ne.f)
                if(!openSet.contains(ne)) {
                    openSet.add(ne)
                }
            }

            closedSet.add(q) // e) push q on the closed list
//            copy[q.end.position.y]!![q.end.position.x] = 'ø'
        }

        return null
    }

    private fun returnPath(np: NavigationPoint, copy: Array<Array<NavigationPoint?>>, startTime: Long): ArrayList<NavigationPoint> {
        println("path to goal found: $np")
        val path = pathFromNavigationPoint(np)
        println("Execution time: ${System.currentTimeMillis() - startTime}")
        return path
    }

    private fun add(p1: Point, p2: Point): Point {
        return Point(p1.x + p2.x, p1.y + p2.y)
    }

    private fun getSurroundingNodes(centre: NavigationPoint, input: Array<Array<NavigationPoint?>>): List<NavigationPoint> {
        val toReturn = ArrayList<NavigationPoint>()
        for(p in surrounding) {
            val point = Point(add(centre.position, p))
            if(isValid(point, input)) {
                val toAdd = NavigationPoint(centre, point, input[point.x][point.y]!!.variation)
                toReturn.add(toAdd)
            }
        }
        return toReturn
    }

    private fun getSurroundingEdges(centre: NavigationPoint, finalGoal: NavigationPoint, input: Array<Array<NavigationPoint?>>): List<Edge> {
        val toReturn = ArrayList<Edge>()
        val points = getSurroundingNodes(centre, input)
        for(point in points) {
            val edge = Edge(centre, point)
            edge.setH(manhattenDistance(edge.start, finalGoal)) // successor.h = distance from goal to successor
            edge.setG(calculateG(centre, direction(centre, point), point)) // Funker som det skal

            toReturn.add(edge)
        }
        return toReturn
    }

    private fun calculateG(originVariation: NavigationPoint, direction: Direction, goalVariation: NavigationPoint): Int {
        val g = calculateG(originVariation.variation, direction, goalVariation.variation)
        println("${originVariation.variation.name}:${direction.name}:${goalVariation.variation.name}")
        println(g)
        return g
    }

    private fun calculateG(originVariation: Variation, direction: Direction, goalVariation: Variation): Int {

        return when(direction) {
            Direction.NORTHWEST -> {
                gCostHandler.handleNorthWest(originVariation, goalVariation)
            }
            Direction.NORTH -> {
                gCostHandler.handleNorth(originVariation, goalVariation)
            }
            Direction.NORTHEAST -> {
                gCostHandler.handleNorthEast(originVariation, goalVariation)
            }
            Direction.WEST -> {
                gCostHandler.handleWest(originVariation, goalVariation)
            }
            Direction.EAST -> {
                gCostHandler.handleEast(originVariation, goalVariation)
            }
            Direction.SOUTHWEST -> {
                gCostHandler.handleSouthWest(originVariation, goalVariation)
            }
            Direction.SOUTH -> {
                gCostHandler.handleSouth(originVariation, goalVariation)
            }
            else -> gCostHandler.handleSouthEast(originVariation, goalVariation)
        }
    }

    private fun isValid(check: Point, input: Array<Array<NavigationPoint?>>): Boolean {
        return check.x >= 0 && check.x < input.size
                && check.y >= 0 && check.y < input.size
                && input[check.y][check.x]!!.variation != Variation.X
    }

    private fun diagonalDistance(current: NavigationPoint, goal: NavigationPoint): Int {
        return max(abs(current.position.x - goal.position.x),
                abs(current.position.y - goal.position.y))
    }

    private fun diagonalDistance(current: Point, goal: Point): Int {
        return max(abs(current.x - goal.x),
                abs(current.y - goal.y))
    }

    /*
     h = abs (current_cell.x – goal.x) +
     abs (current_cell.y – goal.y)
     */
    private fun manhattenDistance(current: NavigationPoint, goal: NavigationPoint): Int {
        return abs(current.position.x - goal.position.x) +
                abs(current.position.y - goal.position.y)
    }

    private fun direction(origin: NavigationPoint, lookingAt: NavigationPoint): Direction {
        return direction(origin.position, lookingAt.position)
    }

    private fun direction(origin: Point, lookingAt: Point): Direction {
        if(origin == lookingAt) {
            throw Exception("Can't call this method from the same Point that you're looking at.")
        }
        // Top
        if(origin.y > lookingAt.y) {
            when {
                origin.x > lookingAt.x -> {
                    return Direction.NORTHWEST
                }
                origin.x == lookingAt.x -> {
                    return Direction.NORTH
                }
            }
            return Direction.NORTHEAST
        }
        // Middle
        if(origin.y == lookingAt.y) {
            if(origin.x > lookingAt.x) {
                return Direction.WEST
            }
            return Direction.EAST
        }
        // Bottom
        when {
            origin.x > lookingAt.x -> {
                return Direction.SOUTHWEST
            }
            origin.x == lookingAt.x -> {
                return Direction.SOUTH
            }
        }
        return Direction.SOUTHEAST
    }

    private fun pathFromNavigationPoint(point: NavigationPoint): ArrayList<NavigationPoint> {
        return recursivePath(point, ArrayList())
    }

    private fun recursivePath(point: NavigationPoint, list: ArrayList<NavigationPoint>): ArrayList<NavigationPoint> {
        list.add(point)
        return if(point.parent == null) {
            list
        } else {
            recursivePath(point.parent, list)
        }
    }

    private fun display(grid: Array<Array<NavigationPoint?>>) {
        for(npa in grid) {
            val builder = StringBuilder()
            for(np in npa) {
                if(np!!.variation == Variation.O) {
                    builder.append(".")
                } else {
                    builder.append(np.variation.name)
                }
                builder.append(" ")
            }
            println(builder.toString())
        }
    }

    private fun display(grid: Array<Array<NavigationPoint?>>, playerPos: NavigationPoint, goalPos: NavigationPoint) {
        for(npa in grid) {
            val builder = StringBuilder()
            for(np in npa) {
                when {
                    np!!.position == goalPos.position -> {
                        builder.append("o")
                    }
                    np.position == playerPos.position -> {
                        builder.append("C")
                    }
                    np.variation == Variation.O -> {
                        builder.append(".")
                    }
                    else -> {
                        builder.append(np.variation.name)
                    }
                }
                builder.append(" ")
            }
            println(builder.toString())
        }
    }

    internal inner class EdgeComparator : Comparator<Edge> {
        override fun compare(o1: Edge, o2: Edge): Int {
            return if(o1.f - o2.f < 0) -1 else if(o1.f == o2.f) 0 else 1
        }
    }

    companion object {
        // 3x3 area to check the surrounding area of a NavigationPoint
        @JvmStatic
        val surrounding: Array<Point> = arrayOf(
                Point(-1, -1), // Top
                Point(0, -1),
                Point(1, -1),
                Point(-1, 0), // Middle
                Point(1, 0),
                Point(-1, 1), // Bottom
                Point(0, 1),
                Point(1, 1)
        )

        @JvmStatic
        fun main(args: Array<String>) {
            Main()
        }
    }

    init {
        val size = 16
        val grid = Array(size) { arrayOfNulls<NavigationPoint>(size) }
        for((i, outerArray) in grid.withIndex()) {
            for(j in outerArray.indices) {
                grid[i][j] = NavigationPoint(i, j)
            }
        }
        for(i in grid.indices) {
            grid[i][0]!!.variation = Variation.X // 'X' = obstacle
            grid[i][grid.size - 1]!!.variation = Variation.X
            grid[0][i]!!.variation = Variation.X
            grid[grid.size - 1][i]!!.variation = Variation.X
        }

        display(grid)
        val path = pathAStar(grid)
        /*
        println("path size: ${path?.size}")
        path?.reverse()
        if(path != null) {
            var step = 1
            for(p in path) {
                println("${step++}:[${p.position.x}:${p.position.y}]")
            }
        }
        */
    }
}