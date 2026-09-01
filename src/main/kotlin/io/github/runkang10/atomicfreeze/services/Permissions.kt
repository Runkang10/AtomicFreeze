package io.github.runkang10.atomicfreeze.services

import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.command.RemoteConsoleCommandSender
import org.bukkit.entity.Player
import org.bukkit.permissions.Permission
import org.bukkit.permissions.PermissionDefault
import org.bukkit.util.permissions.DefaultPermissions

object Permissions {
    data class PermissionNode(
        val permission: String,
        val default: PermissionDefault
    )

    private const val PREFIX = "atomicfreeze"
    private const val COMMAND_PREFIX = "$PREFIX.command"
    private const val EXEMPT_PREFIX = "$PREFIX.exempt"


    val FREEZE = PermissionNode("$COMMAND_PREFIX.freeze", PermissionDefault.OP)
    val UNFREEZE = PermissionNode("$COMMAND_PREFIX.unfreeze", PermissionDefault.OP)

    object Core {
        private const val CORE_COMMAND_PREFIX = "$COMMAND_PREFIX.core"


        val COMMAND = PermissionNode(CORE_COMMAND_PREFIX, PermissionDefault.OP)
        val RELOAD = PermissionNode("$CORE_COMMAND_PREFIX.reload", PermissionDefault.OP)
    }

    fun register() {
        listOf(FREEZE, UNFREEZE, Core.COMMAND, Core.RELOAD).forEach { (permission, default) ->
            DefaultPermissions.registerPermission(Permission(permission, default))
        }

        repeat(10) { i ->
            DefaultPermissions.registerPermission(Permission("${EXEMPT_PREFIX}.${i + 1}", PermissionDefault.FALSE))
        }
    }

    fun CommandSender.canFreeze(target: Player) = when (this) {
        is ConsoleCommandSender -> true
        is RemoteConsoleCommandSender -> true
        is Player -> this == target || exemptWeight() > target.exemptWeight()
        else -> false
    }


    private fun Player.exemptWeight(): Int {
        val weight = effectivePermissions
            .filter { it.value && it.permission.startsWith("$EXEMPT_PREFIX.") }
            .mapNotNull { it.permission.split(".").lastOrNull()?.toIntOrNull() }
            .maxOrNull() ?: 0
        val additionalWeight = if (isOp) 1 else 0
        return weight.coerceAtMost(Int.MAX_VALUE - 2) + additionalWeight
    }
}