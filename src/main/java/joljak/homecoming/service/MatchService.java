package joljak.homecoming.service;

import joljak.homecoming.dto.MatchResultDto;
import joljak.homecoming.entity.Board;
import joljak.homecoming.entity.SightingBoard;
import joljak.homecoming.repository.BoardRepository;
import joljak.homecoming.repository.SightingBoardRepository;
import joljak.homecoming.util.TfIdfCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MatchService {

    private static final Logger logger =  LoggerFactory.getLogger(MatchService.class);

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private SightingBoardRepository sightingBoardRepository;
    private final Set<String> stopWords;
    private final TfIdfCalculator tfIdfCalculator;

    @Autowired
    public MatchService(BoardRepository boardRepository, SightingBoardRepository sightingBoardRepository, ResourceLoader resourceLoader) throws IOException {
        this.boardRepository = boardRepository;
        this.sightingBoardRepository = sightingBoardRepository;

        Resource resource = resourceLoader.getResource("classpath:stopword.txt");
        this.stopWords = Files.lines(Paths.get(resource.getURI()))
                .map(String::trim)
                .collect(Collectors.toSet());
        this.tfIdfCalculator = new TfIdfCalculator(new HashSet<>()); // 빈 Set 전달
    }

    public MatchResultDto findBestMatch(Long boardId) {
        // 해당 ID의 신고글을 가져옵니다.
        Board board = boardRepository.findById(boardId).orElse(null);
        if (board == null) {
            return null;
        }

        logger.info("신고글: {}", board);

        // 데이터베이스에서 모든 목격글을 가져옵니다.
        List<SightingBoard> sightings = sightingBoardRepository.findAll();
        logger.info("목격글 목록: {}", sightings);

        // 신고글의 breed와 동일한 breed의 목격글들만 필터링합니다.
        List<SightingBoard> filteredSightings = sightings.stream()
                .filter(sighting -> sighting.getWbreed().equals(board.getBreed()))
                .collect(Collectors.toList());

        logger.info("필터링된 목격글 목록: {}", filteredSightings);

        if (filteredSightings.isEmpty()) {
            return null;
        }

        // 신고글의 텍스트를 결합하여 TF-IDF 벡터를 계산합니다.
        String boardText = concatenateFields(board);
        logger.info("신고글 텍스트: {}", boardText);

        List<String> sightingTexts = filteredSightings.stream()
                .map(this::concatenateFields)
                .collect(Collectors.toList());
        logger.info("목격글 텍스트 목록: {}", sightingTexts);

        Map<String, Double> idfMap = tfIdfCalculator.computeIdf(sightingTexts);
        logger.info("IDF Map: {}", idfMap);

        Map<String, Double> boardTfIdfVector = tfIdfCalculator.computeTfIdf(boardText, idfMap);
        logger.info("Board TF-IDF Vector: {}", boardTfIdfVector);

        // 가장 잘 매칭된 목격글을 저장할 변수를 초기화합니다.
        SightingBoard bestSighting = null;
        double maxSimilarity = 0.0;

        // 신고글과 필터링된 목격글들을 비교하여 가장 유사한 목격글을 찾습니다.
        for (SightingBoard sighting : filteredSightings) {
            String sightingText = concatenateFields(sighting);
            logger.info("목격글 텍스트: {}", sightingText);

            Map<String, Double> sightingTfIdfVector = tfIdfCalculator.computeTfIdf(sightingText, idfMap);
            logger.info("Sighting TF-IDF Vector: {}", sightingTfIdfVector);

            double similarity = tfIdfCalculator.cosineSimilarity(boardTfIdfVector, sightingTfIdfVector);
            logger.info("Similarity with Sighting [{}]: {}", sighting.getId(), similarity);

            if (similarity > maxSimilarity) {
                maxSimilarity = similarity;
                bestSighting = sighting;
            }
        }
        MatchResultDto result = new MatchResultDto();
        result.setBestSighting(bestSighting);
        result.setMaxSimilarity(maxSimilarity);

        return result;
    }

    private String concatenateFields(Board board) {
        return String.join(" ",
                board.getTitle(),
                board.getBreed(),
                board.getSize(),
                board.getColor(),
                board.getCharacteristics(),
                board.getLastSeenLocation());
    }

    private String concatenateFields(SightingBoard sightingBoard) {
        return String.join(" ",
                sightingBoard.getWtitle(),
                sightingBoard.getWbreed(),
                sightingBoard.getWsize(),
                sightingBoard.getWcolor(),
                sightingBoard.getWcharacteristics(),
                sightingBoard.getWlastSeenLocation());
    }
}
