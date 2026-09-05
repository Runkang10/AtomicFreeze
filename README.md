<div align="center">

# AtomicFreeze
All it takes is one command to make the player think it's lagging.

</div>

## How it works
This plugin will intercept all the packets sent by the player or getting sent to the player and cancel them, making that
like if they have never existed and other players will see the player not moving at all.\
The frozen state of the player is only stored in memory. By default, players are automatically unfrozen when they leave
the server. You can also make them stay frozen until you manually unfreeze them or restart the server.

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

## NOTE
This project uses [PacketEvents](https://github.com/retrooper/packetevents) by **retrooper**, which is licensed under
the **GNU General Public License v3.0**; therefore, this project is under the same license the library uses.
See [the LICENSE](LICENSE) file for more information.