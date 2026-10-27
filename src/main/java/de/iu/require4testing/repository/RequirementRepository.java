package de.iu.require4testing.repository;

import de.iu.require4testing.model.Requirement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequirementRepository
        extends JpaRepository<Requirement, Long> {
}
