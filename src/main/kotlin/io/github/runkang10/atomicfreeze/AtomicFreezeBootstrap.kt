package io.github.runkang10.atomicfreeze

import io.github.runkang10.atomicfreeze.configurations.DefaultSettings
import io.github.runkang10.atomicfreeze.configurations.DefaultTranslations
import io.github.runkang10.atomicfreeze.services.PrefixedSender
import io.github.runkang10.compactmono.configuration.LoggedConfiguration
import io.github.runkang10.compactmono.services.ColoredLogger
import io.papermc.paper.plugin.bootstrap.BootstrapContext
import io.papermc.paper.plugin.bootstrap.PluginBootstrap
import io.papermc.paper.plugin.bootstrap.PluginProviderContext
import org.spongepowered.configurate.ConfigurationOptions

@Suppress("unused", "UnstableApiUsage")
internal class AtomicFreezeBootstrap : PluginBootstrap {
    private lateinit var logger: ColoredLogger
    private lateinit var settings: LoggedConfiguration<DefaultSettings>
    private lateinit var translations: LoggedConfiguration<DefaultTranslations>


    override fun bootstrap(context: BootstrapContext) {
        logger = ColoredLogger(context.logger)

        val pluginFolder = context.dataDirectory.toFile()
        settings = LoggedConfiguration(
            pluginFolder.resolve("settings.conf"),
            DefaultSettings::class,
            DefaultSettings(),
            ConfigurationOptions.defaults(),
            null,
            logger
        )
        translations = LoggedConfiguration(
            pluginFolder.resolve("translations.conf"),
            DefaultTranslations::class,
            DefaultTranslations(),
            ConfigurationOptions.defaults(),
            null,
            logger
        )
        settings.load()
        translations.load()

        PrefixedSender.prefix = translations.get().prefix
    }

    override fun createPlugin(context: PluginProviderContext) = AtomicFreeze(logger, settings, translations)
}