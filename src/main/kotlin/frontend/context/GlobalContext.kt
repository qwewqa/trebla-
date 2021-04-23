package xyz.qwewqa.trebla.frontend.context

import xyz.qwewqa.trebla.backend.compile.Archetype
import xyz.qwewqa.trebla.frontend.*
import xyz.qwewqa.trebla.frontend.expression.Expression
import xyz.qwewqa.trebla.frontend.expression.LambdaType
import xyz.qwewqa.trebla.frontend.expression.UnitValue
import xyz.qwewqa.trebla.frontend.expression.Value
import xyz.qwewqa.trebla.frontend.value.*
import xyz.qwewqa.trebla.frontend.value.intrinsics.*
import xyz.qwewqa.trebla.grammar.trebla.TreblaFileNode

class GlobalContext(val configuration: CompilerConfiguration) : Context(null) {
    override val scope = Scope(null)
    override val levelAllocator = StandardAllocator(LEVEL_MEMORY_BLOCK, 256)
    override val leveldataAllocator = StandardAllocator(LEVEL_DATA_BLOCK, 256, 6)
    override val tempAllocator = StandardAllocator(TEMPORARY_MEMORY_BLOCK, 16)
    override val globalContext: GlobalContext = this

    private val files = mutableListOf<TreblaFile>()

    private val packages: MutableMap<List<String>, Package> = mutableMapOf()

    fun process(): CompileData {
        with(files) {
            forEach(TreblaFile::loadInitial)
            forEach(TreblaFile::loadNormal)
            forEach(TreblaFile::loadFinal)
        }
        val scripts = files
            .flatMap { it.scripts }
            .also { scripts ->
                val names = mutableSetOf<String>()
                scripts.forEach {
                    if (it.identifier in names) {
                        compileError("Duplicate script name: ${it.identifier}.", it.node)
                    }
                    names += it.identifier
                }
            }
            .sortedBy { it.identifier }
            .also { scripts ->
                scripts.forEachIndexed { index, script ->
                    script.index = index
                }
            }
        val archetypes = files
            .flatMap { it.archetypes }
            .sortedBy { it.identifier }
        return CompileData(
            scripts.map { it.process() },
            archetypes.map { it.process() },
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
        intrinsicObjects.forEach { (pkg, declaration) -> declaration.applyTo(getOrCreatePackage(pkg).context) }
        intrinsics.forEach { (pkg, declaration) ->
            val target = getOrCreatePackage(pkg)
            declaration(target.context).applyTo(target.context)
        }
    }
}

data class CompileData(val scripts: List<ScriptData>, val archetypes: List<Archetype>)

// completely stateless intrinsics
val intrinsicObjects: List<Pair<List<String>, Declaration>> = listOf(
    listOf("std") to PrimitiveBaseType,
    listOf("std") to NumberType,
    listOf("std") to BooleanType,
    listOf("std") to UnknownPrimitiveType,
    listOf("std") to Builtins,
    listOf("std") to TypeType,
    listOf("std") to AnyType,
    listOf("std") to NothingType,
    listOf("std") to UnitValue,
    listOf("std") to StructType,
    listOf("std") to ScriptType,
    listOf("std") to CallableType,
    listOf("std") to PackageType,
    listOf("std") to ContextType,
    listOf("std") to LambdaType,
    listOf("std") to StringType,
    listOf("std") to EnumType,
    listOf("std") to NamespaceType,
    listOf("std") to MapProxyType,
)

// constructors for intrinsics that require the context
val intrinsics: List<Pair<List<String>, (Context) -> Expression>> = listOf(
    listOf("std") to ::OptionsAccessor,
    listOf("std") to ::EntityPointerCallableType,
    listOf("std") to ::ListBaseType,
    listOf("std") to ::LocalContextCallable,
    listOf("std") to ::WithContextCallable,
    listOf("std") to ::DereferenceCallable,
    listOf("std") to ::ListOfCallable,
    listOf("std") to ::SizeOfCallable,
    listOf("std") to ::TypeOfCallable,
    listOf("std") to ::TypeAcceptsCallable,
    listOf("std") to ::RangeCallableType,
    listOf("std") to ::CompileErrorCallable,
    listOf("std") to ::AllocationDataOf,
    listOf("std") to ::EnumFromOrdinalCallable,
    listOf("std") to ::NamespaceCallable
)
