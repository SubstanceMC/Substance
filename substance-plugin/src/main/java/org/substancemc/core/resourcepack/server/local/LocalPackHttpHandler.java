package org.substancemc.core.resourcepack.server.local;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.substancemc.core.util.file.DataFolderFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class LocalPackHttpHandler implements HttpHandler {

    private final DataFolderFile served;

    public LocalPackHttpHandler(DataFolderFile served)
    {
        this.served = served;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if(exchange.getRequestMethod().equals("GET"))
        {
            File resourcePack = served.getFile();
            exchange.getResponseHeaders().set("Content-Type", "application/zip");
            exchange.sendResponseHeaders(200, resourcePack.length());
            try(OutputStream responseBody = exchange.getResponseBody())
            {
                Files.copy(resourcePack.toPath(), responseBody);
                responseBody.flush();
            }
        }
    }
}
