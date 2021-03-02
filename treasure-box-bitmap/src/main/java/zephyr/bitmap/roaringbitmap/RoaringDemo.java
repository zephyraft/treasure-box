package zephyr.bitmap.roaringbitmap;

import lombok.extern.slf4j.Slf4j;
import org.roaringbitmap.RoaringBitmap;

import java.io.*;

@Slf4j
public class RoaringDemo {

    public static void main(String[] args) {
        log.info("中文test");
        int[] data = new int[50000];
        for (int i = 0; i < 30000; i++) {
            if (i % 9 == 0) {
                data[i] = i + 1;
            }
        }

        RoaringBitmap rr = RoaringBitmap.bitmapOf(data);

        log.info("{}", rr.select(4));
        log.info("{}", rr.select(5));
        log.info("{}", rr.select(6));



        log.info("{}", rr.getSizeInBytes());

        //序列化与反序列化
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            rr.serialize(new DataOutputStream(bos));
            byte[] bout = bos.toByteArray();

            // log.info("{}", Base64.getEncoder().encodeToString(bout));
            // log.info("{}", Base64.getEncoder().encodeToString(bout).length());

            RoaringBitmap dd = RoaringBitmap.bitmapOf();
            dd.deserialize(new DataInputStream(new ByteArrayInputStream(bout)));
            log.info("{}", "bitmap 1 (recovered) : " + dd);


            // rr.add();


        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

}
