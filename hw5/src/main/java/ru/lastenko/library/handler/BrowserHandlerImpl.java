package ru.lastenko.library.handler;

import org.springframework.stereotype.Service;
import ru.lastenko.library.exceptions.BrowserException;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class BrowserHandlerImpl implements BrowserHandler {
    @Override
    public void browse(String uri) throws BrowserException {
        // https://stackoverflow.com/questions/27378292/launch-browser-automatically-after-spring-boot-webapp-is-ready
        if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(uri));
            } catch (IOException | URISyntaxException e) {
                throw new BrowserException(e);
            }
        }else{
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("rundll32 url.dll,FileProtocolHandler " + uri);
            } catch (IOException e) {
                throw new BrowserException(e);
            }
        }
    }
}