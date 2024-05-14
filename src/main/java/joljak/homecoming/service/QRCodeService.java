package joljak.homecoming.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import joljak.homecoming.entity.PetQrInfo;
import joljak.homecoming.repository.PetQrInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class QRCodeService {

    private final PetQrInfoRepository petQrInfoRepository;

    @Autowired
    public QRCodeService(PetQrInfoRepository petQrInfoRepository) {
        this.petQrInfoRepository = petQrInfoRepository;
    }

    public byte[] generateQRCode(String text) throws Exception{

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        return pngOutputStream.toByteArray();
    }
}
