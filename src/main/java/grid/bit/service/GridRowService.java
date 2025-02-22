package grid.bit.service;

import grid.bit.model.Grid;
import grid.bit.model.GridRow;
import grid.bit.model.response.GridRowResponse;
import grid.bit.repository.GridCellRepository;
import grid.bit.repository.GridRowRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class GridRowService {
    private final GridRowRepository gridRowRepository;
    private final GridCellRepository gridCellRepository;

    @Transactional
    public GridRowResponse insertRow(Long afterRowId) {
        var row = gridRowRepository.findById(afterRowId)
                .orElseThrow(EntityNotFoundException::new);
        var newRow = buildRow(row.getGrid(), row.getNumber() + 1);
        var rows = row.getGrid().getRows();
        rows.sort((c1, c2) -> c2.getNumber().compareTo(c1.getNumber()));
        rows.forEach(c -> {
            if (c.getNumber() > row.getNumber()) {
                c.setNumber(c.getNumber() + 1);
                gridRowRepository.saveAndFlush(c);
            }
        });
        gridRowRepository.save(newRow);
        return new GridRowResponse(newRow.getId(), newRow.getNumber());
    }

    @Transactional
    public void deleteRow(Long id) {
        gridRowRepository.findById(id).ifPresent(gridRow -> {
            gridCellRepository.deleteByRowId(id);
            gridRowRepository.deleteById(gridRow.getId());
            gridRowRepository.flush();
            var rows = gridRow.getGrid().getRows();
            rows.sort(Comparator.comparing(GridRow::getNumber));
            rows.forEach(c -> {
                if (c.getNumber() > gridRow.getNumber()) {
                    c.setNumber(c.getNumber() - 1);
                    gridRowRepository.saveAndFlush(c);
                }
            });
        });
    }

    private GridRow buildRow(Grid grid, int number) {
        var row = new GridRow();
        row.setNumber(number);
        row.setGrid(grid);
        return row;
    }
}
