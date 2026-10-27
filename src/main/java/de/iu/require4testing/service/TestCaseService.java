package de.iu.require4testing.service;

import de.iu.require4testing.model.Requirement;
import de.iu.require4testing.model.TestCase;
import de.iu.require4testing.repository.RequirementRepository;
import de.iu.require4testing.repository.TestCaseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TestCaseService {

    private final TestCaseRepository testCaseRepository;
    private final RequirementRepository requirementRepository;

    public TestCaseService(
            TestCaseRepository testCaseRepository,
            RequirementRepository requirementRepository) {

        this.testCaseRepository = testCaseRepository;
        this.requirementRepository = requirementRepository;
    }

    public List<TestCase> findAll() {
        return testCaseRepository.findAll();
    }

    @Transactional
    public TestCase save(TestCase testCase, Long requirementId) {
        Requirement requirement = requirementRepository
                .findById(requirementId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Die ausgewählte Anforderung wurde nicht gefunden."
                ));

        testCase.setRequirement(requirement);
        return testCaseRepository.save(testCase);
    }
}