package de.iu.require4testing.controller;

import de.iu.require4testing.model.Requirement;
import de.iu.require4testing.service.RequirementService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/requirements")
public class RequirementController {

    private final RequirementService requirementService;

    public RequirementController(RequirementService requirementService) {
        this.requirementService = requirementService;
    }

    @GetMapping
    public String showRequirements(Model model) {
        model.addAttribute("requirement", new Requirement());
        model.addAttribute("requirements", requirementService.findAll());
        return "requirements";
    }

    @PostMapping
    public String createRequirement(
            @Valid @ModelAttribute("requirement") Requirement requirement,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute(
                    "requirements",
                    requirementService.findAll()
            );
            return "requirements";
        }

        requirementService.save(requirement);
        return "redirect:/requirements";
    }
}