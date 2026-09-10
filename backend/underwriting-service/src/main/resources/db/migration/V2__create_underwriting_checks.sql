CREATE TABLE underwriting_checks (
    check_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    underwriting_case_id BIGINT NOT NULL,
    check_type VARCHAR(50) NOT NULL,
    check_status VARCHAR(30) NOT NULL,
    result_code VARCHAR(50) NULL,
    result_summary VARCHAR(255) NULL,
    executed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_underwriting_case
        FOREIGN KEY (underwriting_case_id)
        REFERENCES underwriting_cases (underwriting_case_id)
);

CREATE INDEX idx_check_case_id ON underwriting_checks (underwriting_case_id);
CREATE INDEX idx_check_type ON underwriting_checks (check_type);