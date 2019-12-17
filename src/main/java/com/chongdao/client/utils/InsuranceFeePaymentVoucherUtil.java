package com.chongdao.client.utils;

import com.chongdao.client.vo.PaymentVoucherVO;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

/**
 * @Description 医疗费用支付凭证生成方法
 * @Author onlineS
 * @Date 2019/12/16
 * @Version 1.0
 **/
public class InsuranceFeePaymentVoucherUtil {
    private static final String prefix = "/static/images/";

    private static void createImage(String fileLocation, BufferedImage image) {
        try {
            FileOutputStream fos = new FileOutputStream(fileLocation);
            BufferedOutputStream bos = new BufferedOutputStream(fos);


            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
            encoder.encode(image);
            bos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String graphicsGeneration(PaymentVoucherVO paymentVoucherVO) {
        int imageWidth = 350;// 图片的宽度
        int imageHeight = 450;// 图片的高度
        BufferedImage image = new BufferedImage(imageWidth, imageHeight,
                BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, imageWidth, imageHeight);
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("simsun", Font.BOLD, 20));

        int num = 50;
        graphics.drawString("商家    : " + paymentVoucherVO.getShopName(), 50, num);
        num += 50;
        graphics.drawString("类型    : " + paymentVoucherVO.getPayType(), 50, num);
        num += 50;
        graphics.drawString("时间    : " + paymentVoucherVO.getCreateTime(), 50, num);
        num += 50;
        graphics.drawString("交易单号    : " + paymentVoucherVO.getOrderNo(), 50, num);
        num += 50;
        graphics.drawString("费用    : " + paymentVoucherVO.getMoney(), 50, num);
//        num += 50;
//        graphics.drawString("备注    : " + paymentVoucherVO.getRemark(), 50, num);

        createImage(prefix + paymentVoucherVO.getOrderNo() + ".jpg", image);
        return "https://www.chongdaopet.cn/images/" + paymentVoucherVO.getOrderNo() + ".jpg";
    }
}
