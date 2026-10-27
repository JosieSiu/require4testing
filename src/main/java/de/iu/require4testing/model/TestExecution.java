package de.iu.require4testing.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Size;

@Entity
@Table(
        name = "test_executions",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_test_run_test_case",
                columnNames = {
                        "test_run_id",
                        "test_case_id"
                }
        )
)
public class TestExecution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "test_run_id", nullable = false)
    private TestRun testRun;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "test_case_id", nullable = false)
    private TestCase testCase;

    @Enumerated(EnumType.STRING)
    @Column(name = "result_status", nullable = false, length = 20)
    private TestResultStatus resultStatus =
            TestResultStatus.NOT_EXECUTED;

    @Size(max = 2000)
    @Column(name = "actual_result", length = 2000)
    private String actualResult;

    public TestExecution() {
    }

    public TestExecution(TestRun testRun, TestCase testCase) {
        this.testRun = testRun;
        this.testCase = testCase;
        this.resultStatus = TestResultStatus.NOT_EXECUTED;
    }

    public Long getId() {
        return id;
    }

    public TestRun getTestRun() {
        return testRun;
    }

    public void setTestRun(TestRun testRun) {
        this.testRun = testRun;
    }

    public TestCase getTestCase() {
        return testCase;
    }

    public void setTestCase(TestCase testCase) {
        this.testCase = testCase;
    }

    public TestResultStatus getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(TestResultStatus resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getActualResult() {
        return actualResult;
    }

    public void setActualResult(String actualResult) {
        this.actualResult = actualResult;
    }
}