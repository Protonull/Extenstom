# BasicMinestomServer

[![GitHub](https://img.shields.io/github/license/Protonull/BasicMinestomServer?style=flat-square&color=b2204c)](https://github.com/Protonull/BasicMinestomServer/blob/master/LICENSE)
[![GitHub Repo stars](https://img.shields.io/github/stars/Protonull/BasicMinestomServer?style=flat-square)](https://github.com/Protonull/BasicMinestomServer/stargazers)

BasicMinestomServer is an ***EXTREMELY*** basic [Minestom](https://github.com/Minestom/Minestom) server in that it produces a jar that'll run... and that's it.

## Install

You can either use the provided latest build [here](https://github.com/Protonull/BasicMinestomServer/releases/tag/latest) or you can compile it yourself with JDK 21 by doing:
```shell
git clone --recursive https://github.com/Protonull/BasicMinestomServer.git
cd BasicMinestomServer
./gradlew build
```
The resulting jar will be located at: `build/libs/BasicMinestomServer-<VERSION>.jar`

## Usage

You'll need Java 21 or above to run BasicMinestomServer. You need only execute it like so:
```shell
java -jar BasicMinestomServer-<VERSION>.jar
```

You can also set the `host` and `port` values like so (otherwise they'll default to `localhost` and `25565` respectively):
```shell
java -jar -Dhost="localhost" -Dport=25565 BasicMinestomServer-<VERSION>.jar
```

## Extending

Any and all custom behaviour should be handled by [extensions](https://github.com/hollow-cube/minestom-ce-extensions). For example,
[StomCleanly](https://github.com/Protonull/StomCleanly) introduces a `/stop` command.

```java
package example.extension;

import net.minestom.server.MinecraftServer;
import net.minestom.server.extensions.Extension;
import net.minestom.server.extras.bungee.BungeeCordProxy;

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
    @Override
    public void postInitialize() {

    }
}
```
