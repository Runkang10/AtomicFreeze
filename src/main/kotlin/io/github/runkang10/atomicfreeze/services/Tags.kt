package io.github.runkang10.atomicfreeze.services

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver

object Tags {
    val EMPTY = TagResolver.empty()

    fun default(target: String) = TagResolver.resolver(
        Placeholder.unparsed("target", target)
    )
}