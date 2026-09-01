package io.github.runkang10.atomicfreeze.listeners

import com.github.retrooper.packetevents.event.PacketListenerAbstract
import com.github.retrooper.packetevents.event.PacketListenerPriority
import com.github.retrooper.packetevents.event.PacketReceiveEvent
import com.github.retrooper.packetevents.event.PacketSendEvent
import com.github.retrooper.packetevents.protocol.packettype.PacketType
import io.github.runkang10.atomicfreeze.configurations.DefaultSettings
import io.github.runkang10.atomicfreeze.services.PlayerManager
import io.github.runkang10.compactmono.configuration.LoggedConfiguration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerListener(
    private val settings: LoggedConfiguration<DefaultSettings>
) : PacketListenerAbstract(PacketListenerPriority.NORMAL), Listener {
    private val preventSettings get() = settings.get().prevent


    override fun onPacketSend(event: PacketSendEvent?) {
        if (!preventSettings.sending) return

        val event = event ?: return
        if (event.packetType == PacketType.Play.Server.KEEP_ALIVE ||
            !PlayerManager.has(event.user.uuid ?: return)
        ) return

        event.isCancelled = true
    }

    override fun onPacketReceive(event: PacketReceiveEvent?) {
        if (!preventSettings.receiving) return

        val event = event ?: return
        if (event.packetType == PacketType.Play.Client.KEEP_ALIVE ||
            !PlayerManager.has(event.user.uuid ?: return)
        ) return

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