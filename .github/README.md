# Extenstom

[![GitHub](https://img.shields.io/github/license/Protonull/Extenstom?style=flat-square&color=b2204c)](https://github.com/Protonull/Extenstom/blob/master/LICENCE.txt)
[![GitHub Repo stars](https://img.shields.io/github/stars/Protonull/Extenstom?style=flat-square)](https://github.com/Protonull/Extenstom/stargazers)

Extenstom is an ***EXTREMELY*** barebones implementation of [Microtus](https://github.com/OneLiteFeatherNET/Microtus)
that defers any and all custom behaviour to extensions.

## Install

You can either use the latest build [here](https://github.com/Protonull/Extenstom/releases/tag/latest) or you can
compile it yourself with JDK 21 by doing:
```shell
git clone https://github.com/Protonull/Extenstom.git
cd Extenstom
./gradlew build
```
The resulting jar will be located at: `build/libs/Extenstom-<VERSION>.jar`

## Usage

You'll need Java 21 or above to run Extenstom. You need only execute it like so:
```shell
java -jar Extenstom-<VERSION>.jar
```

You can also set the hostname and port values like so (otherwise they'll default to `localhost` and `25565` respectively):
```shell
java -jar -Dextenstom.host="localhost" -Dextenstom.port=25565 Extenstom-<VERSION>.jar
```

## Extending

Any and all custom behaviour MUST be handled by [extensions](https://wiki.microtus.dev/extension-system/extensions).

```java
package example.extension;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.extensions.Extension;
import net.minestom.server.extras.bungee.BungeeCordProxy;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.Block;

public class ExampleExtension extends Extension {
    // This method should be used for things that ought to be done as soon as possible.
    @Override
    public void preInitialize() {
        MinecraftServer.setBrandName("BestServerEverMC");
    }

    // This method is for other things that occur prior to server start.
    @Override
    public void initialize() {
        BungeeCordProxy.enable();
    }

    // This method is for anything you want to do after the server has started.
    // This is based on the "Your first server" wiki page: https://wiki.microtus.dev/getting-started/your-first-server
    @Override
    public void postInitialize() {
        // Create a new instance
        final InstanceContainer instanceContainer = MinecraftServer.getInstanceManager().createInstanceContainer();

        // Set the ChunkGenerator
        instanceContainer.setGenerator((unit) -> {
            unit.modifier().fillHeight(0, 0, Block.BEDROCK);
            unit.modifier().fillHeight(1, 39, Block.DIRT);
            unit.modifier().fillHeight(40, 40, Block.GRASS_BLOCK);
        });

        // Listen for player logins and put them in the instance
        MinecraftServer.getGlobalEventHandler().addListener(AsyncPlayerConfigurationEvent.class, (event) -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(instanceContainer);
            player.setRespawnPoint(new Pos(0, 42, 0));
        });
    }

    // This method is called when your extension is disabled, like during shutdown
    @Override
    public void terminate() {

    }
}
```
