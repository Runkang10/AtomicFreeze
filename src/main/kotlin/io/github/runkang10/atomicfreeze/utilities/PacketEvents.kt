package io.github.runkang10.atomicfreeze.utilities

import org.bukkit.Bukkit

private const val PACKETEVENTS = "packetevents"


fun isPacketEventsPresent(): Boolean {
    val pluginManager = Bukkit.getPluginManager()
    return pluginManager.getPlugin(PACKETEVENTS) != null ||
            pluginManager.isPluginEnabled(PACKETEVENTS)
}