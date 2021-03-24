class GCostHandler {

    public fun handleNorthWest(originVariation: Variation, goalVariation: Variation): Int {
        if(checkContainsNW(goalVariation.name, originVariation.name)) {
            return 999
        }
        return 1
    }

    public fun handleNorth(originVariation: Variation, goalVariation: Variation): Int {
        if(checkContainsN(goalVariation.name, originVariation.name)) {
            return 999
        }
        return 1
    }

    public fun handleNorthEast(originVariation: Variation, goalVariation: Variation): Int {
        if(checkContainsNE(goalVariation.name, originVariation.name)) {
            return 999
        }
        return 1
    }

    public fun handleWest(originVariation: Variation, goalVariation: Variation): Int {
        if(checkContainsW(goalVariation.name, originVariation.name)) {
            return 999
        }
        return 1
    }

    public fun handleEast(originVariation: Variation, goalVariation: Variation): Int {
        if(checkContainsE(originVariation.name, goalVariation.name)) {
            return 999
        }
        return 1
    }

    public fun handleSouthWest(originVariation: Variation, goalVariation: Variation): Int {
        if(checkContainsSW(goalVariation.name, originVariation.name)) {
            return 999
        }
        return 1
    }

    public fun handleSouth(originVariation: Variation, goalVariation: Variation): Int {
        if(checkContainsS(goalVariation.name, originVariation.name)) {
            return 999
        }
        return 1
    }

    public fun handleSouthEast(originVariation: Variation, goalVariation: Variation): Int {
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

    private fun checkContainsN(goal: String, origin: String): Boolean {
        return goal.contains(Variation.S.name)
                || goal.contains(Variation.ls.name)
                || goal.contains(Variation.rs.name)
                || origin.contains(Variation.N.name)
                || origin.contains(Variation.ln.name)
                || origin.contains(Variation.rn.name)
    }

    private fun checkContainsNE(goal: String, origin: String): Boolean {
        return goal.contains(Variation.S.name)
                || goal.contains(Variation.W.name)
                || goal.contains(Variation.ls.name)
                || goal.contains(Variation.dw.name)
                || origin.contains(Variation.N.name)
                || origin.contains(Variation.E.name)
                || origin.contains(Variation.rn.name)
                || origin.contains(Variation.ue.name)
    }

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

    private fun checkContainsSW(goal: String, origin: String): Boolean {
        return goal.contains(Variation.N.name)
                || goal.contains(Variation.E.name)
                || goal.contains(Variation.rn.name)
                || goal.contains(Variation.ue.name)
                || origin.contains(Variation.S.name)
                || origin.contains(Variation.W.name)
                || origin.contains(Variation.ls.name)
                || origin.contains(Variation.dw.name)
    }

    private fun checkContainsS(goal: String, origin: String): Boolean {
        return goal.contains(Variation.N.name)
                || goal.contains(Variation.ln.name)
                || goal.contains(Variation.rn.name)
                || origin.contains(Variation.S.name)
                || origin.contains(Variation.ls.name)
                || origin.contains(Variation.rs.name)
    }

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
}