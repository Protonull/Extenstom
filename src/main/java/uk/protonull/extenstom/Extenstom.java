package uk.protonull.extenstom;

import net.minestom.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;

public final class Extenstom {
    public static void main(
        final @NotNull String @NotNull [] args
    ) {
        // Enable extensions
        System.setProperty("minestom.extension.enabled", "true");

        final String host;
        final int port;
        try {
            MinecraftServer.init().start(
                host = getHost(),
                port = getPort()
            );
        }
        catch (final Throwable thrown) {
            MinecraftServer.LOGGER.error("An error occurred while trying to start the server.", thrown);
            System.exit(1);
            return;
        }
        MinecraftServer.LOGGER.info(
            "Server started on {}:{} ({}:{})",
            host,
            port,
            MinecraftServer.VERSION_NAME,
            MinecraftServer.PROTOCOL_VERSION
        );
    }

    private static final String DEFAULT_HOST = "0.0.0.0";
    private static String getHost() {
        return System.getProperty("extenstom.host", null) instanceof final String host ? host : DEFAULT_HOST;
    }

    private static final int DEFAULT_PORT = 25565;
    private static int getPort() {
        if (!(System.getProperty("extenstom.port", null) instanceof final String port)) {
            return DEFAULT_PORT;
        }
        try {
            return Integer.parseInt(port);
        }
        catch (final NumberFormatException ignored) {
            MinecraftServer.LOGGER.warn("Could not parse [{}] as a valid port, defaulting to [{}]", port, DEFAULT_PORT);
            return DEFAULT_PORT;
        }
    }
}
