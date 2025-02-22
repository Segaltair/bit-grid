package grid.bit.service;

import grid.bit.model.GridCell;
import grid.bit.model.request.GridCellCreateRequest;
import grid.bit.repository.GridCellRepository;
import grid.bit.repository.GridColumnRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GridCellService {
    private final GridCellRepository gridCellRepository;
    private final GridColumnRepository gridColumnRepository;

    @Transactional
    public void createCell(Long columnId, Long rowId, GridCellCreateRequest request) {
        if (request.value() == null) {
            gridCellRepository.deleteByColumnId(columnId);
        } else {
            validateValue(columnId, request.value());
            var cell = buildCell(columnId, rowId, request);
            gridCellRepository.createCell(cell);
        }
    }

    private void validateValue(Long columnId, String value) {
        for (char с : value.toCharArray()) {
            if (с != '0' && с != '1') {
                throw new IllegalArgumentException("value must contain 0 or 1");
            }
        }
        var cellSize = gridColumnRepository.findById(columnId)
                .orElseThrow(EntityNotFoundException::new)
                .getGrid()
                .getCellSize();
        if (value.length() != cellSize) {
            throw new IllegalArgumentException("value must be length of " + cellSize);
        }
    }

    private GridCell buildCell(Long columnId, Long rowId, GridCellCreateRequest request) {
        var cell = new GridCell();
        cell.setGridColumnId(columnId);
        cell.setGridRowId(rowId);
        cell.setValue(request.value());
        return cell;
    }
}
