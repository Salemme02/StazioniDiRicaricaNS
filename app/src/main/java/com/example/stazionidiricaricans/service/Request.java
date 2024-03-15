package com.example.stazionidiricaricans.service;


import android.content.Context;

import org.chromium.net.CronetEngine;
import org.chromium.net.CronetException;
import org.chromium.net.UrlRequest;
import org.chromium.net.UrlResponseInfo;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Request {

    private static volatile Request instance = null;
    private CronetEngine engine;

    private Request(Context context) {
        engine = new CronetEngine.Builder(context)
                .enableHttp2(true)
                .enableQuic(true)
                .enableBrotli(true)
                //imposta la cartella per la cache http e per i cookie
                .setStoragePath(context.getCacheDir().getAbsolutePath())
                .enableHttpCache(CronetEngine.Builder.HTTP_CACHE_DISK, 10 * 1024 * 1024)
                .build();
    }

    public static synchronized Request getInstance(Context context) {

        if(instance == null) {
            synchronized (Request.class) {
                if (instance == null) instance = new Request(context);
            }
        }
        return instance;
    }


    private final Executor executor = Executors.newSingleThreadExecutor();

    //void perchè è asincrono
    public void requestDownload(Request.RequestCallback callback) {
        engine.newUrlRequestBuilder("https://api.openchargemap.org/v3/poi?output=json", callback,executor)
                .build()
                .start();
        ;
    }

    public abstract static class RequestCallback extends UrlRequest.Callback {

        private final int BYTE_BUFFER_CAPACITY = 1024 * 1024;

        //dove mettiamo i dati ricevuti
        private final ByteArrayOutputStream bytesReceived = new ByteArrayOutputStream();

        //ci permette di scrivere nello stream per i dati ricevuti
        private final WritableByteChannel receiveChannel = Channels.newChannel(bytesReceived);

        //come comportarsi se si verifica un redirect
        @Override
        public void onRedirectReceived(UrlRequest request, UrlResponseInfo info, String newLocationUrl) throws Exception {
            request.followRedirect();
        }

        @Override
        public void onResponseStarted(UrlRequest request, UrlResponseInfo info) throws Exception {
            int code = info.getHttpStatusCode();
            if (code == 200) { //codice di successo
                request.read(ByteBuffer.allocateDirect(BYTE_BUFFER_CAPACITY));
            }
        }

        @Override
        public void onReadCompleted(UrlRequest request, UrlResponseInfo info, ByteBuffer byteBuffer) throws Exception {
            byteBuffer.flip(); //blocca la lunghezza del buffer e sposta il cursore all'inizio
            try {
                receiveChannel.write(byteBuffer);
            } catch (Exception e) {
                e.printStackTrace();
            }
            byteBuffer.clear();

            request.read(byteBuffer); //ricorsivo su se stesso finchè non finisce la lettura
        }

        @Override
        public void onSucceeded(UrlRequest request, UrlResponseInfo info) {
            byte[] data = bytesReceived.toByteArray();
            onCompleted(request, info, data, null);

        }

        //come comportarsi se il download fallisce
        @Override
        public void onFailed(UrlRequest request, UrlResponseInfo info, CronetException error) {
            onCompleted(request, info, null, error);
        }

        public abstract void onCompleted(UrlRequest request, UrlResponseInfo info, byte[] data, CronetException error);
    }
}
