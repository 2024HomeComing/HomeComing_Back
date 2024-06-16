package joljak.homecoming.dto;

import joljak.homecoming.entity.SightingBoard;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchResultDto {
    private SightingBoard bestSighting;
    private double maxSimilarity;
}
