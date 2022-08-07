package com.github.yildizmy.dto.response;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
@Builder
public class CommandDto {
    Long id;
}
