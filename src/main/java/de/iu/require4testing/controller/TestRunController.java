package de.iu.require4testing.controller;

import de.iu.require4testing.model.TestRun;
import de.iu.require4testing.service.TestRunService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test-runs")
public class TestRunController {

    private final TestRunService testRunService;

    public TestRunController(TestRunService testRunService) {
        this.testRunService = testRunService;
    }

    @GetMapping
    public String showTestRuns(Model model) {
        model.addAttribute("testRun", new TestRun());
        model.addAttribute("testRuns", testRunService.findAll());

        return "test-runs";
    }

    @PostMapping
    public String createTestRun(
            @Valid @ModelAttribute("testRun") TestRun testRun,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute(
                    "testRuns",
                    testRunService.findAll()
            );

            return "test-runs";
        }

        testRunService.save(testRun);
        return "redirect:/test-runs";
    }
}