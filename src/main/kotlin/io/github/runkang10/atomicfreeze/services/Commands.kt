package io.github.runkang10.atomicfreeze.services

import io.github.runkang10.atomicfreeze.commands.AtomicFreezeCommand
import io.github.runkang10.atomicfreeze.commands.FreezeCommand
import io.github.runkang10.atomicfreeze.commands.UnfreezeCommand
import io.github.runkang10.atomicfreeze.configurations.DefaultSettings
import io.github.runkang10.atomicfreeze.configurations.DefaultTranslations
import io.github.runkang10.compactmono.configuration.LoggedConfiguration
import io.github.runkang10.compactmono.services.GenericService
import io.papermc.paper.plugin.configuration.PluginMeta
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import org.bukkit.plugin.Plugin

class Commands(
    private val lifecycleManager: LifecycleEventManager<Plugin>,
    private val pluginMeta: PluginMeta,
    private val settings: LoggedConfiguration<DefaultSettings>,
    private val translations: LoggedConfiguration<DefaultTranslations>,
) : GenericService {
    private val commands by lazy {
        arrayOf(
            AtomicFreezeCommand(settings, translations),
            FreezeCommand(translations),
            UnfreezeCommand(translations)
        )
    }


    override fun load() {
        lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) { event ->
            commands.forEach { command ->
                val (description, aliases) = command.meta()
                event.registrar().register(pluginMeta, command.execute(), description, aliases)
            }
        }
    }

    override fun unload() {}
}