package joljak.homecoming.service;

import joljak.homecoming.entity.Board;
import joljak.homecoming.entity.SightingBoard;
import joljak.homecoming.repository.SightingBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class SightingBoardService {

    @Autowired
    private SightingBoardRepository sightingBoardRepository;

    @Autowired
    private SightingBoardRepository sightingReportRepository;

    @Autowired
    S3Service s3Service;


    public Long countPostsToday() {
        return sightingBoardRepository.countPostsToday();
    }


    public SightingBoard insertSightingReport(SightingBoard sightingBoard, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            // 이미지 파일의 key를 생성합니다. 이 예제에서는 "images/{사용자ID}/{파일명}" 형식을 사용합니다.
            // SightingBoard에 사용자 정보가 어떻게 연결되어 있는지에 따라 'getProviderId()' 메소드는 적절히 변경해야 할 수 있습니다.
            String key = "Sightingimages/" + sightingBoard.getUser().getProviderId() + "/" + imageFile.getOriginalFilename();

            // s3Service를 사용하여 이미지 파일을 업로드하고, 결과로 얻은 이미지 URL을 변수에 저장합니다.
            String imageUrl = s3Service.uploadFile(key, imageFile.getBytes());

            // 업로드된 이미지의 URL을 SightingBoard 객체의 imageUrl 필드(또는 해당하는 필드)에 설정합니다.
            sightingBoard.setWImageUrl(imageUrl);
        }

        // 이미지 URL이 설정된 SightingBoard 객체를 데이터베이스에 저장합니다.
        return sightingReportRepository.save(sightingBoard);
    }

    public List<SightingBoard> getAllSightingReports() {
        return sightingReportRepository.findAll();
    }

    public SightingBoard getSightingBoardById(Long sightingId) {
        return  sightingBoardRepository.findById(sightingId).orElse(null);
    }

//    public SightingBoard getSightingReportByUserId(String userId) {
//        Optional<SightingBoard> sightingReportOptional = sightingReportRepository.findByUserId(userId);
//        if (sightingReportOptional.isEmpty()) {
//            throw new IllegalArgumentException("Sighting report with User ID " + userId + " not found");
//        }
//        return sightingReportOptional.get();
//    }
//
//    public void updateSightingReport(String userId, String breed, String size, String furColor, String petCharacter, String location, String sightingTime, String contact) {
//        Optional<SightingBoard> existingReportOptional = sightingReportRepository.findByUserId(userId);
//        if (existingReportOptional.isPresent()) {
//            SightingBoard existingReport = existingReportOptional.get();
//            existingReport.setBreed(breed);
//            existingReport.setSize(size);
//            existingReport.setFurColor(furColor);
//            existingReport.setPetCharacter(petCharacter);
//            existingReport.setLocation(location);
//            existingReport.setSightingTime(sightingTime);
//            existingReport.setContact(contact);
//            sightingReportRepository.save(existingReport);
//        } else {
//            throw new IllegalArgumentException("Sighting report with User ID " + userId + " not found");
//        }
//    }

//    public void deleteSightingReport(String userId, Long sightingId) {
//        Optional<SightingBoard> existingReportOptional = sightingReportRepository.findByIdAndUserId(sightingId, userId);
//        if (existingReportOptional.isPresent()) {
//            sightingReportRepository.deleteById(sightingId);
//        } else {
//            throw new IllegalArgumentException("Sighting report with ID " + sightingId + " and User ID " + userId + " not found");
//        }
//    }
//
//    public void updateSightingReport(String userId, Long sightingBoardId, String breed, String size, String furColor, String petCharacter, String location, String sightingTime, String contact) {
//        Optional<SightingBoard> existingReportOptional = sightingReportRepository.findByIdAndUserId(sightingBoardId, userId);
//        if (existingReportOptional.isPresent()) {
//            SightingBoard existingReport = existingReportOptional.get();
//            existingReport.setBreed(breed);
//            existingReport.setSize(size);
//            existingReport.setFurColor(furColor);
//            existingReport.setPetCharacter(petCharacter);
//            existingReport.setLocation(location);
//            existingReport.setSightingTime(sightingTime);
//            existingReport.setContact(contact);
//            sightingReportRepository.save(existingReport);
//        } else {
//            throw new IllegalArgumentException("Sighting report with ID " + sightingBoardId + " and User ID " + userId + " not found");
//        }
//    }



}
