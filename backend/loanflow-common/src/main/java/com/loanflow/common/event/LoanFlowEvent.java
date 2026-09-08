package com.loanflow.common.event;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

@SuperBuilder
@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@name")
@NoArgsConstructor
public abstract class LoanFlowEvent {
    @Builder.Default
    String eventId = UUID.randomUUID().toString();
    String eventType;
    @Builder.Default
    Instant occurredAt = Instant.now();
}
