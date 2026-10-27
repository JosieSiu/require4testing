package de.iu.require4testing.controller;

import de.iu.require4testing.model.TestExecution;
import de.iu.require4testing.model.TestRun;
import de.iu.require4testing.service.TestCaseService;
import de.iu.require4testing.service.TestExecutionService;
import de.iu.require4testing.service.TestRunService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import de.iu.require4testing.model.TestResultStatus;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/test-runs")
public class TestExecutionController {

    private final TestExecutionService testExecutionService;
    private final TestRunService testRunService;
    private final TestCaseService testCaseService;

    public TestExecutionController(
            TestExecutionService testExecutionService,
            TestRunService testRunService,
            TestCaseService testCaseService) {

        this.testExecutionService = testExecutionService;
        this.testRunService = testRunService;
        this.testCaseService = testCaseService;
    }

    @GetMapping("/{testRunId}/assign")
    public String showAssignment(
            @PathVariable Long testRunId,
            @RequestParam(
                    name = "success",
                    defaultValue = "false"
            ) boolean success,
            Model model) {

        prepareModel(testRunId, model);
        model.addAttribute("success", success);

        return "test-run-assignment";
    }

    @PostMapping("/{testRunId}/assign")
    public String assignTestCases(
            @PathVariable Long testRunId,
            @RequestParam(
                    name = "testerName",
                    required = false
            ) String testerName,
            @RequestParam(
                    name = "testCaseIds",
                    required = false
            ) List<Long> testCaseIds,
            Model model) {

        List<Long> submittedTestCaseIds =
                testCaseIds == null ? List.of() : testCaseIds;

        try {
            testExecutionService.assignTestCases(
                    testRunId,
                    testerName,
                    submittedTestCaseIds
            );

            return "redirect:/test-runs/"
                    + testRunId
                    + "/assign?success=true";

        } catch (IllegalArgumentException exception) {
            prepareModel(testRunId, model);

            model.addAttribute(
                    "assignmentError",
                    exception.getMessage()
            );
            model.addAttribute("testerName", testerName);
            model.addAttribute(
                    "selectedTestCaseIds",
                    submittedTestCaseIds
            );
            model.addAttribute("success", false);

            return "test-run-assignment";
        }
    }
    @GetMapping("/{testRunId}/results")
    public String showResults(
            @PathVariable Long testRunId,
            @RequestParam(
                    name = "success",
                    defaultValue = "false"
            ) boolean success,
            Model model) {

        prepareResultsModel(testRunId, model);
        model.addAttribute("success", success);

        return "test-run-results";
    }

    @PostMapping("/{testRunId}/results/{executionId}")
    public String updateResult(
            @PathVariable Long testRunId,
            @PathVariable Long executionId,
            @RequestParam TestResultStatus resultStatus,
            @RequestParam(
                    name = "actualResult",
                    required = false
            ) String actualResult,
            Model model) {

        try {
            testExecutionService.updateResult(
                    testRunId,
                    executionId,
                    resultStatus,
                    actualResult
            );

            return "redirect:/test-runs/"
                    + testRunId
                    + "/results?success=true";

        } catch (IllegalArgumentException exception) {
            prepareResultsModel(testRunId, model);

            model.addAttribute(
                    "resultError",
                    exception.getMessage()
            );
            model.addAttribute("success", false);

            return "test-run-results";
        }
    }
    private void prepareResultsModel(
            Long testRunId,
            Model model) {

        model.addAttribute(
                "testRun",
                testRunService.findById(testRunId)
        );

        model.addAttribute(
                "executions",
                testExecutionService.findByTestRunId(testRunId)
        );

        model.addAttribute(
                "resultStatuses",
                TestResultStatus.values()
        );
    }
    private void prepareModel(Long testRunId, Model model) {
        TestRun testRun = testRunService.findById(testRunId);

        List<TestExecution> executions =
                testExecutionService.findByTestRunId(testRunId);

        List<Long> assignedTestCaseIds = new ArrayList<>();

        for (TestExecution execution : executions) {
            assignedTestCaseIds.add(
                    execution.getTestCase().getId()
            );
        }

        model.addAttribute("testRun", testRun);
        model.addAttribute(
                "testCases",
                testCaseService.findAll()
        );
        model.addAttribute(
                "selectedTestCaseIds",
                assignedTestCaseIds
        );
        model.addAttribute(
                "testerName",
                testRun.getTesterName()
        );
    }
}