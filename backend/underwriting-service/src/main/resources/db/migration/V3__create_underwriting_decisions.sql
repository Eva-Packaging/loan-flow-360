CREATE TABLE underwriting_decisions (
    decision_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    underwriting_case_id BIGINT NOT NULL,
    decision_type VARCHAR(50) NOT NULL,
    recommended_amount DECIMAL(15,2) NULL,
    recommended_term_months INT NULL,
    decision_source VARCHAR(30) NOT NULL,
    decided_by_user_id BIGINT NULL,
    decision_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_decision_case
            FOREIGN KEY (underwriting_case_id)
            REFERENCES underwriting_cases(underwriting_case_id)
);

CREATE INDEX idx_decision_case_id ON underwriting_decisions (underwriting_case_id);
CREATE INDEX idx_decision_at ON underwriting_decisions (decision_at);