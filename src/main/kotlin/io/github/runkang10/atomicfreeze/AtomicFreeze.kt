package io.github.runkang10.atomicfreeze

import com.github.retrooper.packetevents.PacketEvents
import io.github.runkang10.atomicfreeze.configurations.DefaultSettings
import io.github.runkang10.atomicfreeze.configurations.DefaultTranslations
import io.github.runkang10.atomicfreeze.listeners.PlayerListener
import io.github.runkang10.atomicfreeze.services.Commands
import io.github.runkang10.atomicfreeze.services.Permissions
import io.github.runkang10.atomicfreeze.services.PlayerManager
import io.github.runkang10.atomicfreeze.utilities.isPacketEventsPresent
import io.github.runkang10.compactmono.configuration.LoggedConfiguration
import io.github.runkang10.compactmono.services.ColoredLogger
import org.bukkit.plugin.java.JavaPlugin

class AtomicFreeze(
    private val logger: ColoredLogger,
    private val settings: LoggedConfiguration<DefaultSettings>,
    private val translations: LoggedConfiguration<DefaultTranslations>
) : JavaPlugin() {
    private val listener by lazy { PlayerListener(settings) }


    override fun onLoad() {
        Permissions.register()

        if (!isPacketEventsPresent()) {
            logger.error("PacketEvents is not installed!")
            logger.error("Please install PacketEvents in order to use AtomicFreeze.")
            return
        }

        Commands(lifecycleManager, pluginMeta, settings, translations).load()
        PacketEvents.getAPI().eventManager.registerListener(listener)
    }

    override fun onEnable() {
        server.pluginManager.registerEvents(listener, this)
    }

    override fun onDisable() {
        if (isPacketEventsPresent()) PacketEvents.getAPI().eventManager.unregisterListener(listener)

        PlayerManager.clearUuid()
        PlayerManager.clear()
    }
}