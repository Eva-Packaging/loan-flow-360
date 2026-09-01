CREATE TABLE manual_review_notes (
    review_note_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    underwriting_case_id BIGINT NOT NULL,
    reviewer_user_id BIGINT NOT NULL,
    note_text VARCHAR(1000) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_review_note_case
             FOREIGN KEY (underwriting_case_id)
             REFERENCES underwriting_cases (underwriting_case_id)
);

CREATE INDEX idx_review_note_case_id ON manual_review_notes (underwriting_case_id);
CREATE INDEX idx_review_note_created_at ON manual_review_notes (created_at);