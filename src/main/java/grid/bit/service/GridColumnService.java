package grid.bit.service;

import grid.bit.repository.GridColumnRepository;
import org.springframework.stereotype.Service;

@Service
public class GridColumnService {
    private final GridColumnRepository gridColumnRepository;

    public GridColumnService(GridColumnRepository gridColumnRepository) {
        this.gridColumnRepository = gridColumnRepository;
    }
}