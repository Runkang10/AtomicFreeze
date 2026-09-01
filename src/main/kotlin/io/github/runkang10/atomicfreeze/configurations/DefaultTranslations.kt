package io.github.runkang10.atomicfreeze.configurations

import org.spongepowered.configurate.objectmapping.ConfigSerializable
import org.spongepowered.configurate.objectmapping.meta.Comment

@ConfigSerializable
data class DefaultTranslations(
    @Comment("DO NOT CHANGE THIS!")
    val version: Int = DefaultSettings.VERSION,
    val prefix: String = "<dark_aqua><b>[AtomicFreeze]</b></dark_aqua> <dark_gray>» </dark_gray>",
    val freeze: FreezeTranslations = FreezeTranslations(),
    val unfreeze: UnfreezeTranslations = UnfreezeTranslations(),
    val reload: ReloadTranslations = ReloadTranslations()
)

@ConfigSerializable
data class FreezeTranslations(
    val alreadyFrozen: String = "<aqua><target><red> is <u>already frozen</u>!",
    val insufficientPermission: String = "<red>You don't have permission to <u>freeze</u> <aqua><target></aqua>!",
    val frozen: String = "<aqua><target><green> has been <u>frozen</u>."
)

@ConfigSerializable
data class UnfreezeTranslations(
    val notFrozen: String = "<aqua><target><red> is <u>not frozen</u>!",
    val unfrozen: String = "<aqua><target><green> has been <u>unfrozen</u>."
)

@ConfigSerializable
data class ReloadTranslations(
    val reloading: String = "<yellow>Reloading configurations...",
    val reloaded: String = "<green>All configurations have been reloaded.",
    val reloadFailure: String = "<red>Failed to reload configurations! Check console for details."
)