package grid.bit.service;

import grid.bit.repository.GridRepository;
import org.springframework.stereotype.Service;

@Service
public class GridService {
    private final GridRepository gridRepository;

    public GridService(GridRepository gridRepository) {
        this.gridRepository = gridRepository;
    }
}