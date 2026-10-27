package de.iu.require4testing.repository;

import de.iu.require4testing.model.TestRun;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRunRepository
        extends JpaRepository<TestRun, Long> {
}