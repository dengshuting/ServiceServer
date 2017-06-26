package com.getyourlocation.app.serviceserver.util;

import java.io.*;
import java.net.Socket;
import com.getyourlocation.app.serviceserver.business.entity.Point;

public class GetShopLocation {
    public static Point GetShopLocation(byte[] imgbyte) throws IOException {
        Socket client;
        client = new Socket("127.0.0.1", 8000);
        BufferedOutputStream out = new BufferedOutputStream(client.getOutputStream(), 8*1024*1024);
        BufferedInputStream buf = new BufferedInputStream(client.getInputStream());
        // push img to ShopPositioningServer
        out.write(imgbyte);
        out.flush();
        // get shop position from ShopPositioningServer
        byte[] pointsInfo = new byte[1024];
        buf.read(pointsInfo);
        // TODO : process the bytes

        //String str = new String(pointsInfo);
        //System.out.println(str);
        return new Point(1, 1);
    }
}
