package io.github.runkang10.atomicfreeze.services

import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.command.CommandSender

object PrefixedSender {
    @Volatile
    var prefix: String = ""

    fun CommandSender.send(
        message: String,
        tags: TagResolver = Tags.EMPTY
    ) = sendRichMessage("$prefix$message", tags)
}