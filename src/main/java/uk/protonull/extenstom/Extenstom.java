package uk.protonull.extenstom;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import net.minestom.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;

public final class Extenstom {
    public static void main(
        final @NotNull String @NotNull [] args
    ) {
        // Enable extensions
        System.setProperty("minestom.extension.enabled", "true");

        final InetSocketAddress serverAddress;
        try {
            MinecraftServer.init().start(
                serverAddress = new InetSocketAddress(getHost(), getPort())
            );
        }
        catch (final Throwable thrown) {
            MinecraftServer.LOGGER.error("An error occurred while trying to start the server.", thrown);
            System.exit(1);
            return;
        }
        MinecraftServer.LOGGER.info(
            "Server started on {}:{} ({}:{})",
            serverAddress.getHostString(),
            serverAddress.getPort(),
            MinecraftServer.VERSION_NAME,
            MinecraftServer.PROTOCOL_VERSION
        );
    }

    private static @NotNull InetAddress getHost() {
        return switch (System.getProperty("extenstom.host", null)) {
            case final String host:
                try {
                    yield InetAddress.getByName(host.trim());
                }
                catch (final UnknownHostException ignored) {
                    MinecraftServer.LOGGER.warn("INVALID HOST [{}], USING DEFAULT!", host);
                    // FALLTHROUGH
                }
            case null:
                yield InetAddress.getLoopbackAddress();
        };
    }

    private static int getPort() {
        return switch (System.getProperty("extenstom.port", null)) {
            case final String port:
                try {
                    yield Integer.parseInt(port.trim());
                }
                catch (final NumberFormatException ignored) {
                    MinecraftServer.LOGGER.warn("INVALID PORT [{}], USING DEFAULT!", port);
                    // FALLTHROUGH
                }
            case null:
                yield 25565;
        };
    }
}
