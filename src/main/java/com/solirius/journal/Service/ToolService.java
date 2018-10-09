package com.solirius.journal.Service;

import com.solirius.journal.model.Tool;
import com.solirius.journal.repository.ToolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ToolService {

    private ToolRepository toolRepository;

    @Autowired
    public ToolService(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
    }

    public Optional<Tool> getTool(Integer toolId) {
        return toolRepository.findById(toolId);
    }

    public Optional<Tool> getTool(String toolName) {
        return toolRepository.findByName(toolName);
    }

    public List<Tool> getAllTools() {
        return toolRepository.findAllByOrderByIdAsc();
    }

    public Tool createTool(Tool tool) {
        return toolRepository.save(tool);
    }

    public void destroyTool(Tool tool) {
        toolRepository.delete(tool);
    }
}
