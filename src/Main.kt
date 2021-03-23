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
    private fun pathAStar(input: Array<CharArray>): ArrayList<NavigationPoint>? {
        val startTime = System.currentTimeMillis()
        val copy = arrayOfNulls<CharArray>(input.size)
        for(i in input.indices) {
            copy[i] = input[i].clone()
        }
        val len = input.size
        val startingPos = NavigationPoint(2, 2)
        val goalPos = NavigationPoint(len - 3, 2)
//        startingPos.setG(0)
//        startingPos.setH(diagonalDistance(startingPos, goalPos))
//        startingPos.f = 0

        val openSet = PriorityQueue(EdgeComparator()) // 1 Initialize the open list
        val closedSet = HashSet<Edge>() // 2 Initialize the closed list

        if(len > 3) {
            copy[goalPos.position.y]!![goalPos.position.x] = 'o'
            //for (i in 0 until len / 2 + 4) {
            for(i in 1 until len / 2 + 4) {
                //    copy[i]!![len / 2 - 1] = 'X'
                //    copy[i]!![len / 2] = 'X'
                copy[i]!![len / 2] = 'E'
            }
        }

        val edges = getSurroundingEdges(startingPos, copy) // Her er goalPos _ikke_ den riktige variabelen å bruke
        openSet.addAll(edges) // put the starting node's edges on the open list
        var first = true

        while(!openSet.isEmpty()) { // 3.  while the open list is not empty
            val q = openSet.poll() // a) find the node with the least f on the open list, call it "q". b) pop q off the open list
            println(q)
            copy[q.start.position.y]!![q.start.position.x] = 'C'
            display(copy)
            if(q.end == goalPos) {
                return returnPath(q.end, copy, startTime)
            }

            if(!first) {
                val neighbours = getSurroundingEdges(q.start, copy) // c) generate q's 8 successors and set their parents to q
                for(ne in neighbours) { // d) for each successor
                    if(ne.end == goalPos) { // i) if successor is the goal, stop search
                        return returnPath(ne.end, copy, startTime)
                    }
                    //                println("" + ne.end.position + " - " + ne.f)
                    if(!openSet.contains(ne)) {
                        openSet.add(ne)
                    }
                }
            }

            closedSet.add(q) // e) push q on the closed list
            copy[q.end.position.y]!![q.end.position.x] = 'ø'
            first = false
        }

        return null
    }

    private fun returnPath(np: NavigationPoint, copy: Array<CharArray?>, startTime: Long): ArrayList<NavigationPoint> {
        println("path to goal found: $np")
        val path = pathFromNavigationPoint(np)
        for(point in path) {
            copy[point.position.y]!![point.position.x] = '0'
        }
        display(copy)
        println("Execution time: ${System.currentTimeMillis() - startTime}")
        return path
    }

    private fun add(p1: Point, p2: Point): Point {
        return Point(p1.x + p2.x, p1.y + p2.y)
    }

    private fun getSurroundingNodes(centre: NavigationPoint, input: Array<CharArray?>): List<NavigationPoint> {
        val toReturn = ArrayList<NavigationPoint>()
        for(p in surrounding) {
            val point = Point(add(centre.position, p))
            if(isValid(point, input)) {
                val toAdd = NavigationPoint(centre, point)
                toReturn.add(toAdd)
            }
        }
        return toReturn
    }

    private fun getSurroundingEdges(centre: NavigationPoint, input: Array<CharArray?>): List<Edge> {
        val toReturn = ArrayList<Edge>()
        val points = getSurroundingNodes(centre, input)
        for(point in points) {
            val edge = Edge(centre, point)
            edge.setH(manhattenDistance(edge.start, point)) // successor.h = distance from goal to successor
            edge.setG(calculateG(centre, direction(centre, point), point)) // Funker som det skal

            toReturn.add(edge)
        }
        return toReturn
    }

    private fun calculateG(originVariation: NavigationPoint, direction: Direction, goalVariation: NavigationPoint): Int {
        val g = calculateG(originVariation.variation, direction, goalVariation.variation)
        return g
    }

    private fun calculateG(originVariation: Variation, direction: Direction, goalVariation: Variation): Int {

        return when(direction) {
            Direction.NORTHWEST -> {
                handleNorthWest(originVariation, goalVariation)
            }
            Direction.NORTH -> {
                handleNorth(originVariation, goalVariation)
            }
            Direction.NORTHEAST -> {
                handleNorthEast(originVariation, goalVariation)
            }
            Direction.WEST -> {
                handleWest(originVariation, goalVariation)
            }
            Direction.EAST -> {
                handleEast(originVariation, goalVariation)
            }
            Direction.SOUTHWEST -> {
                handleSouthWest(originVariation, goalVariation)
            }
            Direction.SOUTH -> {
                handleSouth(originVariation, goalVariation)
            }
            else -> handleSouthEast(originVariation, goalVariation)
        }
    }

    private fun handleNorthWest(originVariation: Variation, goalVariation: Variation): Int {
        if(checkContainsNW(goalVariation.name, originVariation.name)) {
            return 999
        }
        return 1
    }

    private fun handleNorth(originVariation: Variation, goalVariation: Variation): Int {
        if(checkContainsN(goalVariation.name, originVariation.name)) {
            return 999
        }
        return 1
    }

    private fun handleNorthEast(originVariation: Variation, goalVariation: Variation): Int {
        if(checkContainsNE(goalVariation.name, originVariation.name)) {
            return 999
        }
        return 1
    }

    private fun handleWest(originVariation: Variation, goalVariation: Variation): Int {
        if(checkContainsW(goalVariation.name, originVariation.name)) {
            return 999
        }
        return 1
    }

    private fun handleEast(originVariation: Variation, goalVariation: Variation): Int {
        if(checkContainsE(originVariation.name, goalVariation.name)) {
            return 999
        }
        return 1
    }

    private fun handleSouthWest(originVariation: Variation, goalVariation: Variation): Int {
        if(checkContainsSW(goalVariation.name, originVariation.name)) {
            return 999
        }
        return 1
    }

    private fun handleSouth(originVariation: Variation, goalVariation: Variation): Int {
        if(checkContainsS(goalVariation.name, originVariation.name)) {
            return 999
        }
        return 1
    }

    private fun handleSouthEast(originVariation: Variation, goalVariation: Variation): Int {
        if(checkContainsSE(goalVariation.name, originVariation.name)) {
            return 999
        }
        return 1
    }

    private fun checkContainsNW(goal: String, origin: String): Boolean {
        return goal.contains(Variation.S.name)
                || goal.contains(Variation.E.name)
                || goal.contains(Variation.rs.name)
                || goal.contains(Variation.de.name)
                || origin.contains(Variation.N.name)
                || origin.contains(Variation.W.name)
                || origin.contains(Variation.ln.name)
                || origin.contains(Variation.uw.name)
    }

    // N
    // NE

    private fun checkContainsW(goal: String, origin: String): Boolean {
        return goal.contains(Variation.E.name)
                || goal.contains(Variation.ue.name)
                || goal.contains(Variation.de.name)
                || origin.contains(Variation.W.name)
                || origin.contains(Variation.uw.name)
                || origin.contains(Variation.dw.name)
    }

    private fun checkContainsE(goal: String, origin: String): Boolean {
        return goal.contains(Variation.W.name)
                || goal.contains(Variation.uw.name)
                || goal.contains(Variation.dw.name)
                || origin.contains(Variation.E.name)
                || origin.contains(Variation.ue.name)
                || origin.contains(Variation.de.name)
    }

    // SW
    // S

    private fun checkContainsSE(goal: String, origin: String): Boolean {
        return goal.contains(Variation.N.name)
                || goal.contains(Variation.W.name)
                || goal.contains(Variation.ln.name)
                || goal.contains(Variation.uw.name)
                || origin.contains(Variation.S.name)
                || origin.contains(Variation.E.name)
                || origin.contains(Variation.rs.name)
                || origin.contains(Variation.de.name)
    }

    private fun isValid(check: Point, input: Array<CharArray?>): Boolean {
        return check.x >= 0 && check.x < input.size
                && check.y >= 0 && check.y < input.size
                && input[check.y]!![check.x] != 'X'
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
            throw Exception("Can't call this method from the same Point that you're looking at.");
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

    private fun display(grid: Array<CharArray?>) {
        for(ca in grid) {
            val builder = StringBuilder()
            for(c in ca!!) {
                builder.append(c.toString()).append(" ")
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
        val grid = Array(size) { CharArray(size) }
        for(ca in grid) {
            Arrays.fill(ca, '.')
        }
        for(i in grid.indices) {
            grid[i][0] = 'X' // 'X' = obstacle
            grid[i][grid.size - 1] = 'X'
            grid[0][i] = 'X'
            grid[grid.size - 1][i] = 'X'
        }
        val path = pathAStar(grid)
        println("path size: ${path?.size}")
        path?.reverse()
        if(path != null) {
            var step = 1
            for(p in path) {
                println("${step++}:[${p.position.x}:${p.position.y}]")
            }
        }
    }
}