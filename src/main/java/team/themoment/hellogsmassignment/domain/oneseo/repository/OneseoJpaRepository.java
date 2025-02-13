package team.themoment.hellogsmassignment.domain.oneseo.repository;

import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team.themoment.hellogsmassignment.domain.oneseo.entity.Oneseo;

import java.awt.print.Pageable;
import java.util.Optional;

public interface OneseoJpaRepository extends JpaRepository<Oneseo, Long> {
    @Query("""
                SELECT o FROM Oneseo o 
                JOIN FETCH o.member m
                JOIN FETCH o.oneseoPrivacyDetail op
                JOIN FETCH o.entranceTestResult et 
                JOIN FETCH et.entranceTestFactorsDetail etf
                WHERE o.id = :id
            """)
    Optional<Oneseo> findByIdFetchJoin(Long id);
}
