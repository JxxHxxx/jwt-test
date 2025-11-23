package com.jxx.jwt.authentication.dto;

import lombok.Builder;

@Builder
public record LoginResponse(
        String employeeId,
        String roleLevel,
        String memberName,
        Long memberId
) {
}
