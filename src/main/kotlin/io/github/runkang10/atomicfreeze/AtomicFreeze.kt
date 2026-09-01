package io.github.runkang10.atomicfreeze

import com.github.retrooper.packetevents.PacketEvents
import io.github.runkang10.atomicfreeze.configurations.DefaultSettings
import io.github.runkang10.atomicfreeze.configurations.DefaultTranslations
import io.github.runkang10.atomicfreeze.listeners.PlayerListener
import io.github.runkang10.atomicfreeze.services.*
import io.github.runkang10.compactmono.configuration.LoggedConfiguration
import org.bukkit.plugin.java.JavaPlugin

class AtomicFreeze(
    private val settings: LoggedConfiguration<DefaultSettings>,
    private val translations: LoggedConfiguration<DefaultTranslations>
) : JavaPlugin() {
    private val listener by lazy { PlayerListener(settings) }


    override fun onLoad() {
        Permissions.register()
        Commands(lifecycleManager, pluginMeta, settings, translations).load()
        PacketEvents.getAPI().eventManager.registerListener(listener)
    }

    override fun onEnable() {
        server.pluginManager.registerEvents(listener, this)
    }

    override fun onDisable() {
        Coroutine.cancel()

        PacketEvents.getAPI().eventManager.unregisterListener(listener)

        PlayerManager.clearUuid()
        PlayerManager.clear()

        PrefixedSender.prefix = ""
    }
}