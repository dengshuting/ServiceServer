package com.getyourlocation.app.serviceserver.util;

import java.io.*;
import java.net.Socket;
import com.getyourlocation.app.serviceserver.business.entity.Point;
import com.getyourlocation.app.serviceserver.web.controller.APIController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GetShopLocation {

    private static final Logger Log = LoggerFactory.getLogger(APIController.class);

    public static Point GetShopLocation(byte[] imgbyte) throws IOException {
        Socket client;
        client = new Socket("localhost", 8000);

        Log.info("servive server on");

        BufferedOutputStream out = new BufferedOutputStream(client.getOutputStream(), 8*1024*1024);
        BufferedInputStream buf = new BufferedInputStream(client.getInputStream());

        // push img to ShopPositioningServer
        out.write(imgbyte);
        out.flush();
        Log.info("Pushing image......");

        // get shop position from ShopPositioningServer
        byte[] pointsInfo = new byte[8];
        buf.read(pointsInfo);
        // process 64bit float info
        Float y = ByteToFloat(pointsInfo, 0);
        Float x = ByteToFloat(pointsInfo, 4);
        Log.info(new String(pointsInfo));
        return new Point(x, y);
    }

    public static Float ByteToFloat(byte[] info, int index) {
        int intbits = 0;
        intbits = intbits| (info[index+3] & 0xff) << 0;
        intbits = intbits| (info[index+2] & 0xff) << 8;
        intbits = intbits| (info[index+1] & 0xff) << 16;
        intbits = intbits| (info[index+0] & 0xff) << 24;
        return Float.intBitsToFloat(intbits);
    }
}
