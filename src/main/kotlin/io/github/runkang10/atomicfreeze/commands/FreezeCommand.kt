package io.github.runkang10.atomicfreeze.commands

import com.mojang.brigadier.tree.LiteralCommandNode
import io.github.runkang10.atomicfreeze.configurations.DefaultTranslations
import io.github.runkang10.atomicfreeze.services.Permissions
import io.github.runkang10.atomicfreeze.services.Permissions.canFreeze
import io.github.runkang10.atomicfreeze.services.PlayerManager
import io.github.runkang10.atomicfreeze.services.PrefixedSender.send
import io.github.runkang10.atomicfreeze.services.Tags
import io.github.runkang10.compactmono.commands.*
import io.github.runkang10.compactmono.configuration.LoggedConfiguration
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.argument.ArgumentTypes
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver

class FreezeCommand(private val translations: LoggedConfiguration<DefaultTranslations>) : BrigadierCommand {
    override fun meta() = BrigadierCommandMeta("Freeze players.", listOf("afreeze"))

    override fun execute(): LiteralCommandNode<CommandSourceStack> = command("freeze") {
        permission(Permissions.FREEZE.permission)
        argument("target", ArgumentTypes.player()) {
            execute { context ->
                val source = context.source
                val sender = source.executor ?: source.sender
                val target = context.getArgument("target", PlayerSelectorArgumentResolver::class.java)
                    .resolve(source)
                    .first()

                val translations = translations.get().freeze
                val tags = Tags.default(target.name)

                if (!sender.canFreeze(target)) {
                    sender.send(translations.insufficientPermission, tags)
                    return@execute
                }

                val uuid = target.uniqueId
                if (PlayerManager.has(uuid)) {
                    sender.send(translations.alreadyFrozen, tags)
                    return@execute
                }

                PlayerManager.add(uuid, target.location)
                PlayerManager.addUuidByName(target.name, uuid)

                sender.send(translations.frozen, tags)
            }
        }
    }.build()
}