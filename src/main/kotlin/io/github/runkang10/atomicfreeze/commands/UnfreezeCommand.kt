package io.github.runkang10.atomicfreeze.commands

import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.tree.LiteralCommandNode
import io.github.runkang10.atomicfreeze.configurations.DefaultTranslations
import io.github.runkang10.atomicfreeze.services.Permissions
import io.github.runkang10.atomicfreeze.services.PlayerManager
import io.github.runkang10.atomicfreeze.services.PrefixedSender.send
import io.github.runkang10.atomicfreeze.services.Tags
import io.github.runkang10.compactmono.commands.*
import io.github.runkang10.compactmono.configuration.LoggedConfiguration
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.Bukkit

class UnfreezeCommand(private val translations: LoggedConfiguration<DefaultTranslations>) : BrigadierCommand {
    override fun meta() = BrigadierCommandMeta("Unfreeze players.", listOf("aunfreeze"))

    override fun execute(): LiteralCommandNode<CommandSourceStack> = command("unfreeze") {
        permission(Permissions.UNFREEZE.permission)
        argument("target", StringArgumentType.string()) {
            suggests { _, builder ->
                PlayerManager.getAllNames()
                    .filter { name -> name.startsWith(builder.remainingLowerCase) }
                    .forEach(builder::suggest)
                builder.buildFuture()
            }
            execute { context ->
                val source = context.source
                val sender = source.executor ?: source.sender
                val name = context.getArgument<String>("target") ?: return@execute

                val translations = translations.get().unfreeze
                val tags = Tags.default(name)

                val uuid = PlayerManager.getUuidByName(name)
                if (uuid == null) {
                    sender.send(translations.notFrozen, tags)
                    return@execute
                }

                val location = PlayerManager.remove(uuid)
                if (location == null) {
                    sender.send(translations.notFrozen, tags)
                    return@execute
                }

                PlayerManager.removeUuidByName(name)

                val player = Bukkit.getPlayer(uuid)
                player?.teleportAsync(location)?.thenRun { player.updateInventory() }

                sender.send(translations.unfrozen, tags)
            }
        }
    }.build()
}