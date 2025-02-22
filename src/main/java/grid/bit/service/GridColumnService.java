package grid.bit.service;

import grid.bit.model.Grid;
import grid.bit.model.GridCell;
import grid.bit.model.GridColumn;
import grid.bit.model.response.GridColumnPrefixResponse;
import grid.bit.model.response.GridColumnResponse;
import grid.bit.repository.GridCellRepository;
import grid.bit.repository.GridColumnRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GridColumnService {
    private final GridColumnRepository gridColumnRepository;
    private final GridCellRepository gridCellRepository;

    @Transactional
    public GridColumnResponse insertColumn(Long afterColumnId) {
        var column = gridColumnRepository.findById(afterColumnId)
                .orElseThrow(EntityNotFoundException::new);
        var newColumn = buildColumn(column.getGrid(), column.getNumber() + 1);
        var columns = column.getGrid().getColumns();
        columns.sort((c1, c2) -> c2.getNumber().compareTo(c1.getNumber()));
        columns.forEach(c -> {
            if (c.getNumber() > column.getNumber()) {
                c.setNumber(c.getNumber() + 1);
                gridColumnRepository.saveAndFlush(c);
            }
        });
        gridColumnRepository.save(newColumn);
        return new GridColumnResponse(newColumn.getId(), newColumn.getNumber());
    }

    @Transactional
    public void deleteColumn(Long id) {
        gridColumnRepository.findById(id).ifPresent(gridColumn -> {
            gridCellRepository.deleteByColumnId(id);
            gridColumnRepository.deleteById(gridColumn.getId());
            gridColumnRepository.flush();
            var columns = gridColumn.getGrid().getColumns();
            columns.sort(Comparator.comparing(GridColumn::getNumber));
            columns.forEach(c -> {
                if (c.getNumber() > gridColumn.getNumber()) {
                    c.setNumber(c.getNumber() - 1);
                    gridColumnRepository.saveAndFlush(c);
                }
            });
        });
    }

    @Transactional(readOnly = true)
    public GridColumnPrefixResponse getCommonPrefix(Long id) {
        // todo limit up to 10000 per query and add while has rows
        List<GridCell> cells = gridCellRepository.findByColumnId(id);
        if (cells.isEmpty()) {
            return new GridColumnPrefixResponse("");
        }
        var prefix = cells.get(0).getValue();
        if (cells.size() == 1) {
            return new GridColumnPrefixResponse(prefix);
        }
        for (int i = 1; i < cells.size(); i++) {
            char[] first = prefix.toCharArray();
            char[] second = cells.get(i).getValue().toCharArray();
            int index = 0;
            while (first.length != index && first[index] == second[index]) {
                index++;
            }
            if (index == 0) {
                return new GridColumnPrefixResponse("");
            }
            prefix = prefix.substring(0, index);
        }
        return new GridColumnPrefixResponse(prefix);
    }

    private GridColumn buildColumn(Grid grid, int number) {
        var column = new GridColumn();
        column.setNumber(number);
        column.setGrid(grid);
        return column;
    }
}