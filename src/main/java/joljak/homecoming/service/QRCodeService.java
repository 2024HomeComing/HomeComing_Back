package joljak.homecoming.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class QRCodeService {

    public byte[] generateQRCode(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); // 높은 오류 수정 수준
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        // QR 코드 이미지를 BufferedImage로 변환
        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        // QR 코드 중앙에 구역을 비우고 텍스트 추가
        BufferedImage finalImage = createQRCodeWithTextHole(qrImage, "Homecoming");

        // 최종 이미지를 byte[]로 변환
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        ImageIO.write(finalImage, "PNG", pngOutputStream);
        return pngOutputStream.toByteArray();
    }

    private BufferedImage createQRCodeWithTextHole(BufferedImage qrImage, String text) {
        int width = qrImage.getWidth();
        int height = qrImage.getHeight();
        BufferedImage finalImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = finalImage.createGraphics();

        // QR 코드 그리기
        g.drawImage(qrImage, 0, 0, null);

        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        // 텍스트 설정
        g.setFont(new Font("Verdana", Font.BOLD, 20)); // 폰트 크기 조정
        FontMetrics fontMetrics = g.getFontMetrics();
        int textWidth = fontMetrics.stringWidth(text);
        int textHeight = fontMetrics.getHeight();

        // 중앙 구역 비우기
        int holeX = (width - textWidth) / 2; // 구멍 시작 X 위치
        int holeY = (height / 2) - (textHeight / 2); // 구멍 시작 Y 위치
        int holeHeight = textHeight + 5; // 텍스트 높이 + 여백

        // 중앙 구역을 흰색으로 비우기
        g.setColor(Color.WHITE);
        g.fillRect(holeX, holeY, textWidth, holeHeight); // 구멍 만들기

        // 텍스트 색상 설정 (오렌지색)
        g.setColor(new Color(255, 165, 0)); // 오렌지색

        // 텍스트 그리기
        int textX = (width - textWidth) / 2; // 중앙 정렬
        int textY = holeY + textHeight; // 구멍 아래에 위치
        g.drawString(text, textX, textY);
        g.dispose();

        return finalImage;
    }
}


