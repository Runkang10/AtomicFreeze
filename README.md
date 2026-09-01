<div align="center">

# AtomicFreeze
If [AtomicCrash](https://modrinth.com/plugin/atomiccrash) is not enough, this one will definitely piss players off :p

</div>

## How it works
This plugin will intercept all the packets sent by the player and cancel them, making that like if they have never sent
those packets and other players will see the player not moving at all.

The best part is while you, as the victim, can see what they're doing, other players can't see you at all. Just like
being a ghost that can't do anything other than watching others ignoring you.

## Usage
The frozen state of the player is only stored in memory. By default, players are automatically unfrozen when they leave
the server. You can also make them stay frozen until you manually unfreeze them or restart the server.\
For freezing players, use `/freeze PLAYER_NAME`; for unfreezing players, use `/unfreeze PLAYER_NAME`.

## Requirements
- Java 25
- Install [PacketEvents](https://modrinth.com/plugin/packetevents)

## Commands
See the **Permissions** section to find the required permission for each command.

| Command                | Aliases      | Description                     |
|------------------------|--------------|---------------------------------|
| `/freeze <target>`     | `/afreeze`   | Freezes a player.               | 
| `/unfreeze <target>`   | `/aunfreeze` | Unfreezes a player.             | 
| `/atomicfreeze`        | `/af`        |                                 |
| `/atomicfreeze reload` |              | Reload plugin's configurations. |

## Permissions
| Permission                         | Description                                                                                                                                                           | Default |
|------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------|
| `atomicfreeze.command.freeze`      | Use `/freeze` command.                                                                                                                                                | OP      |
| `atomicfreeze.command.unfreeze`    | Use `/unfreeze` command.                                                                                                                                              | OP      |
| `atomicfreeze.command.core`        | Use `/atomicfreeze` command.                                                                                                                                          | OP      |
| `atomicfreeze.command.core.reload` | Use `/atomicfreeze reload` command.                                                                                                                                   | OP      |
| `atomicfreeze.exempt.<number>`     | Protects the player from being frozen. A player can only be frozen by someone with a higher exemption number than their own or if the command is executed by console. | FALSE   |

## Configurations
`settings.conf`
```hocon
# DO NOT TOUCH THIS!
version = 1
# Toggle what should the plugin prevent.
# DO NOT TOGGLE FALSE ON BOTH OR THE PLUGIN WILL DO NOTHING!
prevent {
  # Stop SENDING packets TO the players.
  sending = true
  # Stop RECEIVING packets FROM the players.
  receiving = true
}
# Automatically unfreeze players when they leave the server.
unfreeze-on-quit = true
```
`translations.conf`
```hocon
# DO NOT CHANGE THIS!
version = 1
prefix = "<dark_aqua><b>[AtomicFreeze]</b></dark_aqua> <dark_gray>» </dark_gray>"
freeze {
  already-frozen = "<aqua><target><red> is already frozen!"
  insufficient-permission = "<red>You don't have permission to <u>freeze</u> <aqua><target></aqua>!"
  frozen = "<aqua><target><green> has been <u>frozen</u>."
}
unfreeze {
  not-frozen = "<aqua><target><red> is <u>not frozen</u>!"
  unfrozen = "<aqua><target><green> has been <u>unfrozen</u>."
}
reload {
  reloading = "<yellow>Reloading configurations..."
  reloaded = "<green>All configurations have been reloaded."
  reload-failure = "<red>Failed to reload configurations! Check console for details."
}
```

<br />

## NOTE
This project uses [PacketEvents](https://github.com/retrooper/packetevents) by **retrooper**, which is licensed under
the **GNU General Public License v3.0**; therefore, this project is under the same license the library uses.
See [the LICENSE](LICENSE) file for more information.