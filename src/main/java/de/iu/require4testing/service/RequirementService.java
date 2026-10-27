package de.iu.require4testing.service;

import de.iu.require4testing.model.Requirement;
import de.iu.require4testing.repository.RequirementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RequirementService {

    private final RequirementRepository requirementRepository;

    public RequirementService(RequirementRepository requirementRepository) {
        this.requirementRepository = requirementRepository;
    }

    public List<Requirement> findAll() {
        return requirementRepository.findAll();
    }

    @Transactional
    public Requirement save(Requirement requirement) {
        return requirementRepository.save(requirement);
    }
}