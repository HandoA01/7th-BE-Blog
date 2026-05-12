package com.example.blog.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "회원가입 요청 DTO")
public class SignupRequest {

    @Schema(description = "이메일 (로그인 식별자)", example = "yeseo@test.com")
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @Schema(description = "비밀번호 (8자 이상)", example = "password1234")
    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, max = 255, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password;

    @Schema(description = "이름", example = "이예서")
    @NotBlank(message = "이름은 필수입니다.")
    @Size(max = 50)
    private String name;

    @Schema(description = "닉네임", example = "yeseo")
    @NotBlank(message = "닉네임은 필수입니다.")
    @Size(max = 50)
    private String nickname;

    @Schema(description = "전화번호 (선택)", example = "010-1234-5678")
    private String phoneNumber;
}
