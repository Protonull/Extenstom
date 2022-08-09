package uk.protonull.minestom;

import net.minestom.server.MinecraftServer;

public final class Server {

    public static void main(final String[] args) {
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
        MinecraftServer.LOGGER.info("Server: " + host + ":" + port);
    }

    private static final String DEFAULT_HOST = "0.0.0.0";
    private static String getHost() {
        return System.getProperty("host", DEFAULT_HOST);
    }

    private static final int DEFAULT_PORT = 25565;
    private static int getPort() {
        final String property = System.getProperty("port", Integer.toString(DEFAULT_PORT));
        try {
            return Integer.parseInt(property);
        }
        catch (final NullPointerException | NumberFormatException ignored) {
            MinecraftServer.LOGGER.warn("Could not parse [" + property + "] as a valid port, defaulting to: " + DEFAULT_PORT);
            return DEFAULT_PORT;
        }
    }

}