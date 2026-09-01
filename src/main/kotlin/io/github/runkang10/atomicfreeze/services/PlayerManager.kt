package io.github.runkang10.atomicfreeze.services

import org.bukkit.Location
import java.util.*

object PlayerManager {
    private val nameCache = mutableMapOf<String, UUID>()
    private val cache = mutableMapOf<UUID, Location>()


    fun add(
        uuid: UUID,
        location: Location
    ) {
        cache[uuid] = location.clone()
    }

    fun remove(uuid: UUID) = cache.remove(uuid)

    fun has(uuid: UUID) = cache.contains(uuid)

    fun clear() = cache.clear()


    fun addUuidByName(
        name: String,
        uuid: UUID
    ) {
        nameCache[name.lowercase()] = uuid
    }

    fun removeUuidByName(name: String) = nameCache.remove(name.lowercase())

    fun getUuidByName(name: String) = nameCache[name.lowercase()]

    fun clearUuid() = nameCache.clear()

    fun getAllNames() = nameCache.keys.toSet()
}