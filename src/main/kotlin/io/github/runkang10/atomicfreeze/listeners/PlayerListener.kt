package io.github.runkang10.atomicfreeze.listeners

import com.github.retrooper.packetevents.event.PacketListenerAbstract
import com.github.retrooper.packetevents.event.PacketListenerPriority
import com.github.retrooper.packetevents.event.PacketReceiveEvent
import com.github.retrooper.packetevents.protocol.packettype.PacketType
import io.github.runkang10.atomicfreeze.configurations.DefaultSettings
import io.github.runkang10.atomicfreeze.services.PlayerManager
import io.github.runkang10.compactmono.configuration.LoggedConfiguration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerListener(
    private val settings: LoggedConfiguration<DefaultSettings>
) : PacketListenerAbstract(PacketListenerPriority.LOWEST), Listener {
    private val allowedPackets = setOf(
        PacketType.Play.Client.CONFIGURATION_ACK,
        PacketType.Play.Client.KEEP_ALIVE,
        PacketType.Play.Client.PONG,
        PacketType.Play.Client.CLICK_WINDOW,
        PacketType.Play.Client.CLICK_WINDOW_BUTTON,
        PacketType.Play.Client.WINDOW_CONFIRMATION,
        PacketType.Play.Client.CLIENT_SETTINGS,
        PacketType.Play.Client.CLIENT_STATUS,
        PacketType.Play.Client.CLIENT_TICK_END
    )


    override fun onPacketReceive(event: PacketReceiveEvent?) {
        val event = event ?: return
        val packetType = event.packetType
        if (packetType !is PacketType.Play.Client || allowedPackets.contains(packetType)) return
        if (!PlayerManager.has(event.user.uuid)) return

        event.isCancelled = true
    }


    @EventHandler
    private fun onPlayerQuit(event: PlayerQuitEvent) {
        if (!settings.get().unfreezeOnQuit) return

        val player = event.player
        PlayerManager.removeUuidByName(player.name)
        PlayerManager.remove(player.uniqueId)
    }
}