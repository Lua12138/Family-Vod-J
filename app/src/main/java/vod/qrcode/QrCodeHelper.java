package vod.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import vod.util.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

/**
 * Created by forDream on 2015-12-27.
 */
public class QrCodeHelper {
    public static File build(String text) {
        int width = 300;
        int height = 300;
        //二维码的图片格式
        String format = "png";
        Hashtable hints = new Hashtable();
        //内容所使用编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(text,
                    BarcodeFormat.QR_CODE, width, height, hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        //生成二维码
        File outputFile = new File(Configuration.getTempFolder() + File.separator + "qrcode.png");
        try {
            MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputFile;
    }
}
