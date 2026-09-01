package io.github.runkang10.atomicfreeze.commands

import com.mojang.brigadier.tree.LiteralCommandNode
import io.github.runkang10.atomicfreeze.configurations.DefaultSettings
import io.github.runkang10.atomicfreeze.configurations.DefaultTranslations
import io.github.runkang10.atomicfreeze.services.Coroutine
import io.github.runkang10.atomicfreeze.services.Permissions
import io.github.runkang10.atomicfreeze.services.PrefixedSender.send
import io.github.runkang10.compactmono.commands.*
import io.github.runkang10.compactmono.configuration.IConfiguration
import io.github.runkang10.compactmono.configuration.LoggedConfiguration
import io.papermc.paper.command.brigadier.CommandSourceStack

class AtomicFreezeCommand(
    private val settings: LoggedConfiguration<DefaultSettings>,
    private val translations: LoggedConfiguration<DefaultTranslations>
) : BrigadierCommand {
    override fun meta() = BrigadierCommandMeta("AtomicFreeze command.", listOf("af"))

    override fun execute(): LiteralCommandNode<CommandSourceStack> = command("atomicfreeze") {
        permission(Permissions.Core.COMMAND.permission)
        subcommand("reload") {
            permission(Permissions.Core.RELOAD.permission)
            execute { context ->
                val sender = context.source.executor ?: context.source.sender
                val reloadMessage = translations.get().reload

                sender.send(reloadMessage.reloading)
                Coroutine.launch {
                    val settingsLoad = settings.load()
                    val translationsLoad = translations.load()

                    if (settingsLoad is IConfiguration.Result.Failure || translationsLoad is IConfiguration.Result.Failure) {
                        sender.send(reloadMessage.reloadFailure)
                        return@launch
                    }

                    sender.send(reloadMessage.reloaded)
                }
            }
        }
    }.build()
}