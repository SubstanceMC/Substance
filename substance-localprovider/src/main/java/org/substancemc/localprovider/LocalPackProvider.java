package org.substancemc.localprovider;

import com.sun.net.httpserver.HttpServer;
import org.substancemc.core.SubstancePlugin;
import org.substancemc.core.resourcepack.server.ResourcePackProvider;
import org.substancemc.core.util.file.DataFolderFile;

import java.io.IOException;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class LocalPackProvider implements ResourcePackProvider {

    private transient String url;
    private transient HttpServer server;
    private transient byte[] hash;
    private transient String checksum;
    private int port;

    public LocalPackProvider(int port)
    {
        this.port = port;
    }

    public LocalPackProvider()
    {

    }

    @Override
    public String getURL() {
        return url;
    }

    @Override
    public String getChecksum() {
        return checksum;
    }

    @Override
    public byte[] getHash()
    {
        return hash;
    }

    @Override
    public void upload(DataFolderFile resourcePack) {
        try {
            byte[] data = Files.readAllBytes(Paths.get(resourcePack.getFile().getPath()));
            this.hash = MessageDigest.getInstance("SHA").digest(data);
            this.checksum = new BigInteger(1, hash).toString(16);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            LocalPackHttpHandler devHandler = new LocalPackHttpHandler(resourcePack);
            server = HttpServer.create();
            server.bind(new InetSocketAddress(SubstancePlugin.get().getServer().getIp(), port), 0);
            ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
            server.setExecutor(poolExecutor);
            server.createContext("/" + checksum, devHandler);
            server.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        url = String.format("http://localhost:%s/%s", port, checksum);
        SubstancePlugin.get().getLogger().warning("Resource Pack server started successfully on " + getURL());
    }

    @Override
    public void onUnload() {
        server.stop(0);
    }
}
