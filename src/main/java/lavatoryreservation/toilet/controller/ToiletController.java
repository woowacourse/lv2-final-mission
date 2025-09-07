package lavatoryreservation.toilet.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lavatoryreservation.toilet.domain.Toilet;
import lavatoryreservation.toilet.dto.AddToiletDto;
import lavatoryreservation.toilet.dto.ToiletDto;
import lavatoryreservation.toilet.service.ToiletService;

@RestController
@RequestMapping("/api/toilet/")
@Tag(name = "화장실 관리", description = "화장실 등록 및 조회 관련 API")
public class ToiletController {
    
    private static final Logger logger = LoggerFactory.getLogger(ToiletController.class);
    private final ToiletService toiletService;

    public ToiletController(ToiletService toiletService) {
        this.toiletService = toiletService;
    }

    @Operation(summary = "화장실 등록", description = "새로운 화장실을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "화장실 등록 성공",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 화장실실 ID")
    })
    @PostMapping
    public ResponseEntity<Long> addToilet(
            @Parameter(description = "화장실 등록 정보", required = true)
            @RequestBody AddToiletDto addToiletDto) {
        
        logger.info("화장실 등록 요청 - 설명: {}", addToiletDto.description());
        
        try {
            Long id = toiletService.addToilet(addToiletDto);
            logger.info("화장실 등록 성공 - 화장실ID: {}", id);
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            logger.error("화장실 등록 실패 - 설명: {}, 에러: {}", 
                    addToiletDto.description(), e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "화장실 목록 조회", description = "등록된 모든 화장실 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "화장실 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = ToiletDto.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @GetMapping
    public ResponseEntity<List<ToiletDto>> getToilets(){
        logger.info("화장실 목록 조회 요청");
        
        try {
            List<Toilet> toilets = toiletService.getToilets();
            List<ToiletDto> toiletDtos = toilets.stream()
                    .map(toilet -> new ToiletDto(toilet.getDescription(),toilet.getLavatory().getDescription(),toilet.getLavatory().getSex().getDescription()))
                    .toList();
            
            logger.info("화장실 목록 조회 성공 - 조회된 화장실 수: {}", toiletDtos.size());
            return ResponseEntity.ok(toiletDtos);
        } catch (Exception e) {
            logger.error("화장실 목록 조회 실패 - 에러: {}", e.getMessage());
            throw e;
        }
    }
}
