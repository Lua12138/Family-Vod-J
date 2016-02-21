import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import vod.qrcode.MatrixToImageWriter;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

/**
 * Created by forDream on 2015-12-27.
 */
public class QrCodeTest {
    public void buildUrl() {
        String text = "http://www.baidu.com";
        int width = 300;
        int height = 300;
        //二维码的图片格式
        String format = "gif";
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
        File outputFile = new File("d:" + File.separator + "new.gif");
        try {
            MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
