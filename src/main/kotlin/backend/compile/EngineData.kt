package xyz.qwewqa.trebla.backend.compile

import com.google.gson.Gson
import xyz.qwewqa.trebla.backend.ir.IRNode
import xyz.qwewqa.trebla.frontend.context.*
import java.io.Reader

@Suppress("UNCHECKED_CAST")
class EngineData(
    val scripts: List<CompiledScript>,
    val archetypes: List<Archetype>,
    val nodes: List<Map<String, Any>>,
    val options: List<Map<String, Any>>,
    val buckets: List<Map<String, Any>>,
) {
    init {
        if (archetypes.toSet().size != archetypes.size) backendError("Archetypes have duplicate names.")
    }

    fun compileLevel(entityData: List<EntityData>): CompiledLevel {
        val archetypesByName = archetypes.associateBy { it.name }
        val archetypeIndexes = archetypes.mapIndexed { i, v -> v to i }.toMap()
        val defaultsByArchetype = archetypes.associateWith { arc ->
            val script = scripts[arc.script]
            val data = MutableList(64) { 0.0 }
            arc.defaults.forEach { (name, value) ->
                data[script.arguments.getValue(name)] = value
            }
            data.toList()
        }
        val entities = entityData.map { entity ->
            val archetype = archetypesByName[entity.archetype] ?: backendError("Unknown archetype ${entity.archetype}")
            val script = scripts[archetype.script]
            val data = MutableList(64) { 0.0 }
            entity.arguments.forEach { (name, value) ->
                val index =
                    script.arguments[name] ?: backendError("Unknown argument $name to archetype ${entity.archetype}")
                data[index] = value
            }
            if (data.all { it == 0.0 }) {
                mapOf(
                    "archetype" to archetypeIndexes.getValue(archetype),
                )
            } else {
                mapOf(
                    "archetype" to archetypeIndexes.getValue(archetype),
                    "data" to mapOf(
                        "index" to data.takeWhile { it == 0.0 }.size,
                        "values" to data.dropWhile { it == 0.0 }.dropLastWhile { it == 0.0 }
                    ),
                )
            }
        }
        val levelFile = mapOf(
            "scripts" to scripts.map { sc ->
                sc.callbacks.associate {
                    it.name to mapOf(
                        "index" to it.entryNode,
                        "order" to it.order,
                    )
                }
            },
            "archetypes" to archetypes.map { arc ->
                val defaults = defaultsByArchetype.getValue(arc)
                if (defaults.all { it == 0.0 }) {
                    mapOf(
                        "script" to arc.script,
                        "input" to arc.input,
                    )
                } else {
                    mapOf(
                        "script" to arc.script,
                        "data" to mapOf(
                            "index" to defaults.takeWhile { it == 0.0 }.size,
                            "values" to defaults.dropWhile { it == 0.0 }
                                .dropLastWhile { it == 0.0 },
                        ),
                        "input" to arc.input,
                    )
                }
            },
            "entities" to entities,
            "nodes" to nodes,
            "buckets" to buckets
        )
        val gson = Gson()
        return CompiledLevel(gson.toJson(levelFile), gson.toJson(options))
    }

    // will note that Gson won't necessarily give you back the exact same types you gave it originally
    // when deserializing, so equality may not hold.
    fun toJson(): String = Gson().toJson(toMap())
    fun fromJson(reader: Reader) = fromMap(Gson().fromJson(reader, Map::class.java) as Map<String, Any>)

    fun toMap(): Map<String, Any> = mapOf(
        "scripts" to scripts.map { sc ->
            mapOf(
                "callbacks" to sc.callbacks.map {
                    mapOf(
                        "name" to it.name,
                        "order" to it.order,
                        "entryNode" to it.entryNode
                    )
                },
                "arguments" to sc.arguments
            )
        },
        "archetypes" to archetypes.map {
            mapOf(
                "name" to it.name,
                "script" to it.script,
                "input" to it.input,
                "defaults" to it.defaults
            )
        },
        "nodes" to nodes,
        "options" to options,
        "buckets" to buckets
    )

    override fun toString() = toMap().toString()

    companion object {
        fun fromMap(map: Map<String, Any>) = EngineData(
            (map["scripts"] as List<Map<String, Any>>).map { sc ->
                CompiledScript(
                    (sc["callbacks"] as List<Map<String, Any>>).map { cb ->
                        CompiledCallback(cb["name"] as String, cb["order"].castInt(), cb["entryNode"].castInt())
                    },
                    (sc["arguments"] as Map<String, Any>).mapValues { (_, v) -> v.castInt() }
                )
            },
            (map["archetypes"] as List<Map<String, Any>>).map { arc ->
                Archetype(
                    arc["name"] as String,
                    arc["script"].castInt(),
                    arc["input"] as Boolean,
                    arc["defaults"] as Map<String, Double>
                )
            },
            map["nodes"] as List<Map<String, Any>>,
            map["options"] as List<Map<String, Any>>,
            map["buckets"] as List<Map<String, Any>>
        )

        // just to deal with json numbers
        private fun Any?.castInt() = if (this is Double) this.toInt() else this as Int
    }
}

data class CompiledLevel(val level: String, val options: String)

data class EntityData(val archetype: String, val arguments: Map<String, Double>)

data class IRScript(
    val index: Int,
    val freeIndexes: List<Int>,
    val callbacks: List<IRCallback>,
    val arguments: Map<String, Int>,
)

data class IRCallback(val name: CallbackName, val order: Int, val node: IRNode)

enum class CallbackName(val compiledName: String, val allowedReadBlocks: Set<Int>, val allowedWriteBlocks: Set<Int>) {
    ShouldSpawn(
        "shouldSpawn",
        setOf(
            LEVEL_MEMORY_BLOCK,
            LEVEL_DATA_BLOCK,
            LEVEL_OPTION_BLOCK,
            LEVEL_TRANSFORM_BLOCK,
            LEVEL_BACKGROUND_BLOCK,
            LEVEL_UI_BLOCK,
            LEVEL_BUCKET_BLOCK,
            LEVEL_SCORE_BLOCK,
            LEVEL_LIFE_BLOCK,
            ENTITY_INFO_ARRAY_BLOCK,
            ENTITY_DATA_ARRAY_BLOCK,
            ENTITY_SHARED_MEMORY_ARRAY_BLOCK,
            ENTITY_INFO_BLOCK,
            ENTITY_DATA_BLOCK,
            ENTITY_SHARED_MEMORY_BLOCK,
            ARCHETYPE_LIFE_BLOCK,
        ),
        setOf(),
    ),
    Initialize(
        "initialize",
        setOf(
            LEVEL_MEMORY_BLOCK,
            LEVEL_DATA_BLOCK,
            LEVEL_OPTION_BLOCK,
            LEVEL_TRANSFORM_BLOCK,
            LEVEL_BACKGROUND_BLOCK,
            LEVEL_UI_BLOCK,
            LEVEL_BUCKET_BLOCK,
            LEVEL_SCORE_BLOCK,
            LEVEL_LIFE_BLOCK,
            ENTITY_INFO_ARRAY_BLOCK,
            ENTITY_DATA_ARRAY_BLOCK,
            ENTITY_SHARED_MEMORY_ARRAY_BLOCK,
            ENTITY_INFO_BLOCK,
            ENTITY_MEMORY_BLOCK,
            ENTITY_DATA_BLOCK,
            ENTITY_INPUT_BLOCK,
            ENTITY_SHARED_MEMORY_BLOCK,
            ARCHETYPE_LIFE_BLOCK,
        ),
        setOf(
            ENTITY_MEMORY_BLOCK,
            ENTITY_INPUT_BLOCK,
        ),
    ),
    UpdateSequential(
        "updateSequential",
        setOf(
            LEVEL_MEMORY_BLOCK,
            LEVEL_DATA_BLOCK,
            LEVEL_OPTION_BLOCK,
            LEVEL_TRANSFORM_BLOCK,
            LEVEL_BACKGROUND_BLOCK,
            LEVEL_UI_BLOCK,
            LEVEL_BUCKET_BLOCK,
            LEVEL_SCORE_BLOCK,
            LEVEL_LIFE_BLOCK,
            ENTITY_INFO_ARRAY_BLOCK,
            ENTITY_DATA_ARRAY_BLOCK,
            ENTITY_SHARED_MEMORY_ARRAY_BLOCK,
            ENTITY_INFO_BLOCK,
            ENTITY_MEMORY_BLOCK,
            ENTITY_DATA_BLOCK,
            ENTITY_INPUT_BLOCK,
            ENTITY_SHARED_MEMORY_BLOCK,
            ARCHETYPE_LIFE_BLOCK,
        ),
        setOf(
            LEVEL_MEMORY_BLOCK,
            LEVEL_TRANSFORM_BLOCK,
            LEVEL_BACKGROUND_BLOCK,
            ENTITY_SHARED_MEMORY_ARRAY_BLOCK,
            ENTITY_MEMORY_BLOCK,
            ENTITY_INPUT_BLOCK,
            ENTITY_SHARED_MEMORY_BLOCK,
        ),
    ),
    Touch(
        "touch",
        setOf(
            LEVEL_MEMORY_BLOCK,
            LEVEL_DATA_BLOCK,
            LEVEL_OPTION_BLOCK,
            LEVEL_TRANSFORM_BLOCK,
            LEVEL_BACKGROUND_BLOCK,
            LEVEL_UI_BLOCK,
            LEVEL_BUCKET_BLOCK,
            LEVEL_SCORE_BLOCK,
            LEVEL_LIFE_BLOCK,
            ENTITY_INFO_ARRAY_BLOCK,
            ENTITY_DATA_ARRAY_BLOCK,
            ENTITY_SHARED_MEMORY_ARRAY_BLOCK,
            ENTITY_INFO_BLOCK,
            ENTITY_MEMORY_BLOCK,
            ENTITY_DATA_BLOCK,
            ENTITY_INPUT_BLOCK,
            ENTITY_SHARED_MEMORY_BLOCK,
            ARCHETYPE_LIFE_BLOCK,
            TEMPORARY_MEMORY_BLOCK,
            TEMPORARY_DATA_BLOCK,
        ),
        setOf(
            LEVEL_MEMORY_BLOCK,
            LEVEL_TRANSFORM_BLOCK,
            LEVEL_BACKGROUND_BLOCK,
            ENTITY_SHARED_MEMORY_ARRAY_BLOCK,
            ENTITY_MEMORY_BLOCK,
            ENTITY_INPUT_BLOCK,
            ENTITY_SHARED_MEMORY_BLOCK,
            TEMPORARY_MEMORY_BLOCK,
        ),
    ),
    UpdateParallel(
        "updateParallel",
        setOf(
            LEVEL_MEMORY_BLOCK,
            LEVEL_DATA_BLOCK,
            LEVEL_OPTION_BLOCK,
            LEVEL_TRANSFORM_BLOCK,
            LEVEL_BACKGROUND_BLOCK,
            LEVEL_UI_BLOCK,
            LEVEL_BUCKET_BLOCK,
            LEVEL_SCORE_BLOCK,
            LEVEL_LIFE_BLOCK,
            ENTITY_INFO_ARRAY_BLOCK,
            ENTITY_DATA_ARRAY_BLOCK,
            ENTITY_SHARED_MEMORY_ARRAY_BLOCK,
            ENTITY_INFO_BLOCK,
            ENTITY_MEMORY_BLOCK,
            ENTITY_DATA_BLOCK,
            ENTITY_INPUT_BLOCK,
            ENTITY_SHARED_MEMORY_BLOCK,
            ARCHETYPE_LIFE_BLOCK,
        ),
        setOf(
            ENTITY_MEMORY_BLOCK,
            ENTITY_INPUT_BLOCK,
        ),
    ),
    Preprocess(
        "preprocess",
        setOf(
            LEVEL_MEMORY_BLOCK,
            LEVEL_DATA_BLOCK,
            LEVEL_OPTION_BLOCK,
            LEVEL_TRANSFORM_BLOCK,
            LEVEL_BACKGROUND_BLOCK,
            LEVEL_UI_BLOCK,
            LEVEL_BUCKET_BLOCK,
            LEVEL_SCORE_BLOCK,
            LEVEL_LIFE_BLOCK,
            ENTITY_INFO_ARRAY_BLOCK,
            ENTITY_DATA_ARRAY_BLOCK,
            ENTITY_SHARED_MEMORY_ARRAY_BLOCK,
            ENTITY_INFO_BLOCK,
            ENTITY_DATA_BLOCK,
            ENTITY_SHARED_MEMORY_BLOCK,
            ARCHETYPE_LIFE_BLOCK,
        ),
        setOf(
            LEVEL_MEMORY_BLOCK,
            LEVEL_DATA_BLOCK,
            LEVEL_OPTION_BLOCK,
            LEVEL_TRANSFORM_BLOCK,
            LEVEL_BACKGROUND_BLOCK,
            LEVEL_UI_BLOCK,
            LEVEL_BUCKET_BLOCK,
            LEVEL_SCORE_BLOCK,
            LEVEL_LIFE_BLOCK,
            ENTITY_DATA_ARRAY_BLOCK,
            ENTITY_SHARED_MEMORY_ARRAY_BLOCK,
            ENTITY_DATA_BLOCK,
            ENTITY_SHARED_MEMORY_BLOCK,
            ARCHETYPE_LIFE_BLOCK,
        ),
    ),
    SpawnOrder(
        "spawnOrder",
        setOf(
            LEVEL_MEMORY_BLOCK,
            LEVEL_DATA_BLOCK,
            LEVEL_OPTION_BLOCK,
            LEVEL_TRANSFORM_BLOCK,
            LEVEL_BACKGROUND_BLOCK,
            LEVEL_UI_BLOCK,
            LEVEL_BUCKET_BLOCK,
            LEVEL_SCORE_BLOCK,
            LEVEL_LIFE_BLOCK,
            ENTITY_INFO_ARRAY_BLOCK,
            ENTITY_DATA_ARRAY_BLOCK,
            ENTITY_SHARED_MEMORY_ARRAY_BLOCK,
            ENTITY_INFO_BLOCK,
            ENTITY_DATA_BLOCK,
            ENTITY_SHARED_MEMORY_BLOCK,
            ARCHETYPE_LIFE_BLOCK,
        ),
        setOf(
            LEVEL_MEMORY_BLOCK,
            LEVEL_DATA_BLOCK,
            LEVEL_OPTION_BLOCK,
            LEVEL_TRANSFORM_BLOCK,
            LEVEL_BACKGROUND_BLOCK,
            LEVEL_UI_BLOCK,
            LEVEL_BUCKET_BLOCK,
            LEVEL_SCORE_BLOCK,
            LEVEL_LIFE_BLOCK,
            ENTITY_DATA_ARRAY_BLOCK,
            ENTITY_SHARED_MEMORY_ARRAY_BLOCK,
            ENTITY_DATA_BLOCK,
            ENTITY_SHARED_MEMORY_BLOCK,
            ARCHETYPE_LIFE_BLOCK,
        ),
    ),
}

data class CompiledScript(
    val callbacks: List<CompiledCallback>,
    val arguments: Map<String, Int>,
)

data class CompiledCallback(val name: String, val order: Int, val entryNode: Int)

data class Archetype(val name: String, val script: Int, val input: Boolean, val defaults: Map<String, Double>)
