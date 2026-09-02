package io.github.runkang10.atomicfreeze

import io.github.runkang10.atomicfreeze.configurations.DefaultSettings
import io.github.runkang10.atomicfreeze.configurations.DefaultTranslations
import io.github.runkang10.atomicfreeze.services.PrefixedSender
import io.github.runkang10.compactmono.configuration.LoggedConfiguration
import io.github.runkang10.compactmono.services.ColoredLogger
import io.github.runkang10.compactmono.utilities.strings
import io.papermc.paper.plugin.bootstrap.BootstrapContext
import io.papermc.paper.plugin.bootstrap.PluginBootstrap
import io.papermc.paper.plugin.bootstrap.PluginProviderContext
import org.spongepowered.configurate.ConfigurationOptions

@Suppress("unused", "UnstableApiUsage")
internal class AtomicFreezeBootstrap : PluginBootstrap {
    private lateinit var settings: LoggedConfiguration<DefaultSettings>
    private lateinit var translations: LoggedConfiguration<DefaultTranslations>


    override fun bootstrap(context: BootstrapContext) {
        val logger = ColoredLogger(context.logger)

        val pluginFolder = context.dataDirectory.toFile()
        settings = LoggedConfiguration(
            file = pluginFolder.resolve("settings.conf"),
            type = DefaultSettings::class,
            default = DefaultSettings(),
            options = ConfigurationOptions.defaults(),
            migrations = null,
            logger = logger
        )
        translations = LoggedConfiguration(
            file = pluginFolder.resolve("translations.conf"),
            type = DefaultTranslations::class,
            default = DefaultTranslations(),
            options = ConfigurationOptions.defaults()
                .header(
                    strings(
                        "\n",
                        "Welcome to AtomicFreeze translations file. Here you can change the message output for certain actions and commands.",
                        "NOTE: PlaceholderAPI is NOT supported and some internal placeholders like <target> can only work in certain messages."
                    )
                ),
            migrations = null,
            logger = logger
        )
        settings.load()
        translations.load()

        PrefixedSender.prefix = translations.get().prefix
    }

    override fun createPlugin(context: PluginProviderContext) = AtomicFreeze(settings, translations)
}