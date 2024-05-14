package joljak.homecoming.controller;

import joljak.homecoming.entity.PetQrInfo;
import joljak.homecoming.service.QRCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/qr")
public class QRCodeController {

    @Autowired
    private QRCodeService qrCodeService;

    @PostMapping("/generate")

    public ResponseEntity<byte[]> generateQRCode(@RequestBody PetQrInfo petQrInfo) {
        try {
            String petInfoText = String.format("털색: %s, 좋아하는 것: %s, 싫어하는 것: %s, 사는 지역: %s, 전화번호: %s, 메뉴얼: %s",
                    petQrInfo.getFurColor(), petQrInfo.getLikes(), petQrInfo.getDislikes(),
                    petQrInfo.getRegion(), petQrInfo.getPhoneNumber(), petQrInfo.getManual());

            byte[] qrImage = qrCodeService.generateQRCode(petInfoText);
            System.out.println("QR 생성됨");
            return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.IMAGE_PNG).body(qrImage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
