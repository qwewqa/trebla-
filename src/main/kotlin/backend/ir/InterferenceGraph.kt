package xyz.qwewqa.trebla.backend.ir

fun IFGraph.coalesce(copies: Map<TempLocation, TempLocation>) {
    copies.forEach { (k, v) ->
        if (this[k] !in this[v]) {
            this[k].merge(this[v])
        }
    }
}

fun IRNode.getCopyRelations(): Map<TempLocation, TempLocation> {
    val m = mutableMapOf<TempLocation, MutableSet<TempLocation>>()
    getCopies().forEach { (k, v) ->
        m.getOrPut(k) { mutableSetOf() } += v
    }
    return m.filterValues { it.size == 1 }.mapValues { (_, v) -> v.single() }
}

private fun IRNode.getCopies(): Set<Pair<TempLocation, TempLocation>> = when (this) {
    is IRValue -> emptySet()
    is IRFunctionCall -> arguments.map { it.getCopies() }.fold(emptySet()) { a, v -> a + v }
    is IRTempRead -> emptySet()
    is IRTempAssign -> if (rhs is IRTempRead) setOf(location to rhs.location) else emptySet()
    is IRSeqTempRead -> offset.getCopies()
    is IRSeqTempAssign -> offset.getCopies() + rhs.getCopies()
}

class IFGraph {
    private val nodesByLocation = mutableMapOf<TempLocation, IFNode>()
    private val _nodes = mutableSetOf<IFNode>()

    val nodes: Set<IFNode> get() = _nodes

    operator fun get(location: TempLocation) =
        nodesByLocation.getOrPut(location) { IFNode(location).also { _nodes += it } }

    fun addAll(locations: Set<TempLocation>) {
        locations.forEach { get(it) }
        val loc = locations.toList()
        loc.indices.forEach { l ->
            ((l + 1) until loc.size).forEach { r ->
                this[loc[l]].addEdge(loc[r])
            }
        }
    }

    operator fun plusAssign(locations: Set<TempLocation>) = addAll(locations)

    inner class IFNode(val locations: Set<TempLocation>) {
        constructor(location: TempLocation) : this(setOf(location))

        /**
         * A list containing the indexes occupied by this node (a single number if a single location,
         * or potentially multiple for a sequential location).
         * The "colors" for graph coloring.
         */
        var colors: List<Int> = emptyList()

        val edges: Set<IFNode> get() = _edges
        private val _edges = mutableSetOf<IFNode>()

        var active = true
            private set

        fun addEdge(other: TempLocation) {
            if (other in locations) return
            _edges += this@IFGraph[other].also { it._edges += this }
        }

        fun activate() {
            if (active) return
            _nodes += this
            active = true
            edges.forEach { it._edges += this }
        }

        fun deactivate() {
            if (!active) return
            _nodes -= this
            active = false
            edges.forEach { it._edges -= this }
        }

        fun merge(other: IFNode) {
            this.deactivate()
            other.deactivate()
            val new = IFNode(locations + other.locations)
            _nodes += new
            (locations + other.locations).forEach { nodesByLocation[it] = new }
            (edges + other.edges).flatMap { it.locations }.forEach { new.addEdge(it) }
        }

        operator fun contains(ifNode: IFGraph.IFNode) = (ifNode in edges).also { assert(this in ifNode.edges) }
    }
}
