CREATE TABLE underwriting_cases (
    underwriting_case_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    application_id BIGINT NOT NULL,
    case_status VARCHAR(50) NOT NULL,
    decision_status VARCHAR(50) NULL,
    decision_reason VARCHAR(500) NULL,
    manual_review_required BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    completed_at TIMESTAMP NULL,

    CONSTRAINT uq_application_id UNIQUE (application_id)
);

CREATE INDEX idx_case_status ON underwriting_cases (case_status);
CREATE INDEX idx_decision_status ON underwriting_cases (decision_status);