package de.iu.require4testing.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "test_cases")
public class TestCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Der Titel darf nicht leer sein.")
    @Size(max = 120)
    @Column(nullable = false, length = 120)
    private String title;

    @NotBlank(message = "Die Beschreibung darf nicht leer sein.")
    @Size(max = 2000)
    @Column(nullable = false, length = 2000)
    private String description;

    @NotBlank(message = "Das erwartete Ergebnis darf nicht leer sein.")
    @Size(max = 2000)
    @Column(name = "expected_result", nullable = false, length = 2000)
    private String expectedResult;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "requirement_id", nullable = false)
    private Requirement requirement;

    public TestCase() {
    }

    public TestCase(
            String title,
            String description,
            String expectedResult,
            Requirement requirement) {

        this.title = title;
        this.description = description;
        this.expectedResult = expectedResult;
        this.requirement = requirement;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    public Requirement getRequirement() {
        return requirement;
    }

    public void setRequirement(Requirement requirement) {
        this.requirement = requirement;
    }
}