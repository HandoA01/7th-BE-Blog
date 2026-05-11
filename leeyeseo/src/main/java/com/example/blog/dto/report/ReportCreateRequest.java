package com.example.blog.dto.report;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "게시글 신고 요청 DTO")
public class ReportCreateRequest {

    @Schema(description = "신고 사유", example = "부적절한 홍보성 게시글입니다.", maxLength = 500)
    @NotBlank(message = "신고 사유는 필수입니다.")
    @Size(max = 500, message = "신고 사유는 최대 500자까지 입력 가능합니다.")
    private String reason;
}
