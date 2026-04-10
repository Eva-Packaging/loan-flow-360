package entites;

import entites.enums.CheckStatus;
import entites.enums.CheckType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "underwriting_checks")
@Getter
@Setter
@NoArgsConstructor

public class UnderwritingChecks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checkId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "underwriting_case_id")
    private UnderwritingCases underwritingCase;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CheckType checkType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CheckStatus checkStatus;

    @Column(length = 50)
    private String resultCode;

    @Column(length = 255)
    private String resultSummary;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime excutedAt;





}
