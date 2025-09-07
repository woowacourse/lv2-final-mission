package lavatoryreservation.toilet.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "화장실 추가 요청 DTO")
public record AddToiletDto(
        @Schema(description = "화장실 설명", example = "1층 남자 화장실", required = true)
        String description,
        
        @Schema(description = "비데 여부", example = "true", required = true)
        boolean isBidet,
        
        @Schema(description = "화장실실 ID", example = "1", required = true)
        Long lavatoryId
) {
}
