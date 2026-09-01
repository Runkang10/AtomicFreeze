package io.github.runkang10.atomicfreeze.configurations

import org.spongepowered.configurate.objectmapping.ConfigSerializable
import org.spongepowered.configurate.objectmapping.meta.Comment

@ConfigSerializable
data class DefaultSettings(
    @Comment("DO NOT TOUCH THIS!")
    val version: Int = VERSION,
    @Comment("Automatically unfreeze players when they leave the server.")
    val unfreezeOnQuit: Boolean = true
) {
    companion object {
        const val VERSION = 1
    }
}
