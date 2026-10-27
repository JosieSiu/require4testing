package de.iu.require4testing.service;

import de.iu.require4testing.model.TestCase;
import de.iu.require4testing.model.TestExecution;
import de.iu.require4testing.model.TestRun;
import de.iu.require4testing.repository.TestCaseRepository;
import de.iu.require4testing.repository.TestExecutionRepository;
import de.iu.require4testing.repository.TestRunRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import de.iu.require4testing.model.TestResultStatus;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class TestExecutionService {

    private final TestExecutionRepository testExecutionRepository;
    private final TestRunRepository testRunRepository;
    private final TestCaseRepository testCaseRepository;

    public TestExecutionService(
            TestExecutionRepository testExecutionRepository,
            TestRunRepository testRunRepository,
            TestCaseRepository testCaseRepository) {

        this.testExecutionRepository = testExecutionRepository;
        this.testRunRepository = testRunRepository;
        this.testCaseRepository = testCaseRepository;
    }

    public List<TestExecution> findByTestRunId(Long testRunId) {
        return testExecutionRepository
                .findByTestRunIdOrderByIdAsc(testRunId);
    }

    @Transactional
    public void assignTestCases(
            Long testRunId,
            String testerName,
            List<Long> testCaseIds) {

        if (testerName == null || testerName.isBlank()) {
            throw new IllegalArgumentException(
                    "Der Name der testenden Person darf nicht leer sein."
            );
        }

        String normalizedTesterName = testerName.trim();

        if (normalizedTesterName.length() > 120) {
            throw new IllegalArgumentException(
                    "Der Name darf höchstens 120 Zeichen enthalten."
            );
        }

        if (testCaseIds == null || testCaseIds.isEmpty()) {
            throw new IllegalArgumentException(
                    "Es muss mindestens ein Testfall ausgewählt werden."
            );
        }

        TestRun testRun = testRunRepository.findById(testRunId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Der ausgewählte Testlauf wurde nicht gefunden."
                ));

        Set<Long> selectedIds = new HashSet<>(testCaseIds);

        List<TestCase> selectedTestCases =
                testCaseRepository.findAllById(selectedIds);

        if (selectedTestCases.size() != selectedIds.size()) {
            throw new IllegalArgumentException(
                    "Mindestens ein ausgewählter Testfall wurde nicht gefunden."
            );
        }

        List<TestExecution> existingExecutions =
                testExecutionRepository
                        .findByTestRunIdOrderByIdAsc(testRunId);

        Set<Long> alreadyAssignedIds = new HashSet<>();

        for (TestExecution execution : existingExecutions) {
            Long testCaseId = execution.getTestCase().getId();

            if (selectedIds.contains(testCaseId)) {
                alreadyAssignedIds.add(testCaseId);
            } else {
                testExecutionRepository.delete(execution);
            }
        }

        for (TestCase testCase : selectedTestCases) {
            if (!alreadyAssignedIds.contains(testCase.getId())) {
                TestExecution execution =
                        new TestExecution(testRun, testCase);

                testExecutionRepository.save(execution);
            }
        }

        testRun.setTesterName(normalizedTesterName);
        testRunRepository.save(testRun);
    }
    @Transactional
    public void updateResult(
            Long testRunId,
            Long testExecutionId,
            TestResultStatus resultStatus,
            String actualResult) {

        if (resultStatus == null) {
            throw new IllegalArgumentException(
                    "Es muss ein Ergebnisstatus ausgewählt werden."
            );
        }

        TestExecution execution =
                testExecutionRepository.findById(testExecutionId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Die Testausführung wurde nicht gefunden."
                                )
                        );

        if (!execution.getTestRun().getId().equals(testRunId)) {
            throw new IllegalArgumentException(
                    "Die Testausführung gehört nicht zu diesem Testlauf."
            );
        }

        String normalizedActualResult =
                actualResult == null ? "" : actualResult.trim();

        if (normalizedActualResult.length() > 2000) {
            throw new IllegalArgumentException(
                    "Das tatsächliche Ergebnis darf höchstens "
                            + "2000 Zeichen enthalten."
            );
        }

        if (resultStatus != TestResultStatus.NOT_EXECUTED
                && normalizedActualResult.isBlank()) {

            throw new IllegalArgumentException(
                    "Bei einem ausgeführten Test muss das "
                            + "tatsächliche Ergebnis eingetragen werden."
            );
        }

        execution.setResultStatus(resultStatus);

        execution.setActualResult(
                normalizedActualResult.isBlank()
                        ? null
                        : normalizedActualResult
        );

        testExecutionRepository.save(execution);
    }
}