package lavatoryreservation.toilet.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "화장실 정보 응답 DTO")
public record ToiletDto(
        @Schema(description = "화장실 설명", example = "1층 남자 화장실")
        String description,
        
        @Schema(description = "화장실실 설명", example = "메인 화장실실")
        String lavatoryDescription,
        
        @Schema(description = "성별 설명", example = "남성")
        String sexDescription
) {

}
