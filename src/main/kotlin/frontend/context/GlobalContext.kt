package xyz.qwewqa.trebla.frontend.context

import xyz.qwewqa.trebla.backend.compile.Archetype
import xyz.qwewqa.trebla.frontend.CompilerConfiguration
import xyz.qwewqa.trebla.frontend.TreblaFile
import xyz.qwewqa.trebla.frontend.compileError
import xyz.qwewqa.trebla.frontend.declaration.*
import xyz.qwewqa.trebla.frontend.declaration.intrinsics.*
import xyz.qwewqa.trebla.frontend.expression.Expression
import xyz.qwewqa.trebla.frontend.expression.LambdaType
import xyz.qwewqa.trebla.frontend.expression.UnitValue
import xyz.qwewqa.trebla.frontend.expression.Value
import xyz.qwewqa.trebla.grammar.trebla.TreblaFileNode
import java.util.concurrent.atomic.AtomicInteger

class GlobalContext(val configuration: CompilerConfiguration) : GlobalAllocatorContext, Context {
    override val parentContext: Context? = null
    override val scope = Scope(null)
    override val levelAllocator = StandardAllocator(LEVEL_MEMORY_BLOCK, 256)
    override val leveldataAllocator = StandardAllocator(LEVEL_DATA_BLOCK, 256)
    override val tempAllocator = StandardAllocator(TEMPORARY_MEMORY_BLOCK, 16)
    override val globalContext: GlobalContext = this

    private val files = mutableListOf<TreblaFile>()

    private val packages: MutableMap<List<String>, Package> = mutableMapOf()

    var scriptIndex = 0

    fun process(): CompileData {
        with(files) {
            forEach(TreblaFile::loadInitial)
            forEach(TreblaFile::loadNormal)
            forEach(TreblaFile::loadFinal)
        }
        return CompileData(
            files.flatMap { it.scripts }.map { it.process() },
            files.flatMap { it.archetypes }.map { it.process() }
        )
    }

    fun add(fileNode: TreblaFileNode) {
        files += TreblaFile(fileNode, this)
    }

    /**
     * Gets the package with the given [name], creating a package as necessary.
     */
    fun getOrCreatePackage(name: List<String>): Package {
        if (name.isEmpty()) error("Package name is empty")
        return packages.getOrPut(name) { Package(name, this) }
    }

    fun getPackage(name: List<String>) = packages[name]

    fun getNestedValue(name: List<String>) = getNestedValueOrNull(name)
        ?: compileError("Value ${name.joinToString(".")} does not exist")

    fun getNestedValueOrNull(name: List<String>): Value? {
        if (name.isEmpty()) error("Value ${name.joinToString(".")} is empty")
        val base = scope.get(name[0]) ?: return null
        return name
            .drop(1)
            .fold(base) { a, v -> (a as? Context)?.scope?.get(v) ?: return null }
    }

    init {
        intrinsicObjects.forEach { (pkg, declaration) -> declaration.applyTo(getOrCreatePackage(pkg)) }
        intrinsics.forEach { (pkg, declaration) ->
            val target = getOrCreatePackage(pkg)
            declaration(target).applyTo(target)
        }
    }
}

data class CompileData(val scripts: List<ScriptData>, val archetypes: List<Archetype>)

interface GlobalAllocatorContext : Context {
    val levelAllocator: StandardAllocator
    val leveldataAllocator: StandardAllocator

    // not to be confused with the local allocator for temporary variables
    // this is for the literal temporary memory block
    val tempAllocator: StandardAllocator
}

// completely stateless intrinsics
val intrinsicObjects: List<Pair<List<String>, Declaration>> = listOf(
    listOf("std") to Builtins,
    listOf("std") to TypeType,
    listOf("std") to AnyType,
    listOf("std") to UnitValue,
    listOf("std") to StructType,
    listOf("std") to ScriptType,
    listOf("std") to CallableType,
    listOf("std") to PackageType,
    listOf("std") to ContextType,
    listOf("std") to LambdaType,
    listOf("std") to StringType,
)

// constructors for intrinsics that require the context
val intrinsics: List<Pair<List<String>, (Context) -> Expression>> = listOf(
    listOf("std") to ::OptionsAccessor,
    listOf("std") to ::EntityPointer,
    listOf("std") to ::TreblaList,
    listOf("std") to ::LocalContext,
    listOf("std") to ::WithContext,
    listOf("std") to ::BoxCallable,
    listOf("std") to ::Deref,
    listOf("std") to ::Pointer,
    listOf("std") to ::ListOf,
    listOf("std") to ::Box,
    listOf("std") to ::ResolveBoxPointer,
    listOf("std") to ::ResolveLocalBoxPointer,
    listOf("std") to ::SizeOf,
    listOf("std") to ::TypeOf,
    listOf("std") to ::TypeAccepts,
    listOf("std") to ::Range,
    listOf("std") to ::Error,
)
