package io.github.runkang10.atomicfreeze.configurations

import org.spongepowered.configurate.objectmapping.ConfigSerializable
import org.spongepowered.configurate.objectmapping.meta.Comment

@ConfigSerializable
data class DefaultSettings(
    @Comment("DO NOT TOUCH THIS!")
    val version: Int = VERSION,
    @Comment("Toggle what should the plugin prevent.\nDO NOT TOGGLE FALSE ON BOTH OR THE PLUGIN WILL DO NOTHING!")
    val prevent: PreventSettings = PreventSettings(),
    @Comment("Automatically unfreeze players when they leave the server.")
    val unfreezeOnQuit: Boolean = true
) {
    companion object {
        const val VERSION = 1
    }
}

@ConfigSerializable
data class PreventSettings(
    @Comment("Stop SENDING packets TO the players.")
    val sending: Boolean = true,
    @Comment("Stop RECEIVING packets FROM the players.")
    val receiving: Boolean = true
)
