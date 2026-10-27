package de.iu.require4testing.repository;

import de.iu.require4testing.model.TestExecution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestExecutionRepository
        extends JpaRepository<TestExecution, Long> {

    List<TestExecution> findByTestRunIdOrderByIdAsc(Long testRunId);
}