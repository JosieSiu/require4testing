package de.iu.require4testing.service;

import de.iu.require4testing.model.TestRun;
import de.iu.require4testing.repository.TestRunRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TestRunService {

    private final TestRunRepository testRunRepository;

    public TestRunService(TestRunRepository testRunRepository) {
        this.testRunRepository = testRunRepository;
    }

    public List<TestRun> findAll() {
        return testRunRepository.findAll();
    }

    public TestRun findById(Long id) {
        return testRunRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Der ausgewählte Testlauf wurde nicht gefunden."
                ));
    }

    @Transactional
    public TestRun save(TestRun testRun) {
        return testRunRepository.save(testRun);
    }
}