package de.iu.require4testing.controller;

import de.iu.require4testing.model.TestCase;
import de.iu.require4testing.service.RequirementService;
import de.iu.require4testing.service.TestCaseService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/test-cases")
public class TestCaseController {

    private final TestCaseService testCaseService;
    private final RequirementService requirementService;

    public TestCaseController(
            TestCaseService testCaseService,
            RequirementService requirementService) {

        this.testCaseService = testCaseService;
        this.requirementService = requirementService;
    }

    @GetMapping
    public String showTestCases(Model model) {
        model.addAttribute("testCase", new TestCase());
        model.addAttribute("testCases", testCaseService.findAll());
        model.addAttribute(
                "requirements",
                requirementService.findAll()
        );

        return "test-cases";
    }

    @PostMapping
    public String createTestCase(
            @Valid @ModelAttribute("testCase") TestCase testCase,
            BindingResult bindingResult,
            @RequestParam(
                    name = "requirementId",
                    required = false
            ) Long requirementId,
            Model model) {

        if (requirementId == null) {
            bindingResult.rejectValue(
                    "requirement",
                    "requirement.required",
                    "Bitte wählen Sie eine Anforderung aus."
            );
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute(
                    "testCases",
                    testCaseService.findAll()
            );
            model.addAttribute(
                    "requirements",
                    requirementService.findAll()
            );
            model.addAttribute(
                    "selectedRequirementId",
                    requirementId
            );

            return "test-cases";
        }

        testCaseService.save(testCase, requirementId);
        return "redirect:/test-cases";
    }
}