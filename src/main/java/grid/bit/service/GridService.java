package grid.bit.service;

import grid.bit.model.AbstractEntity;
import grid.bit.model.Grid;
import grid.bit.model.GridColumn;
import grid.bit.model.GridRow;
import grid.bit.model.request.GridCreateRequest;
import grid.bit.model.request.GridUpdateRequest;
import grid.bit.model.response.GridResponse;
import grid.bit.repository.GridCellRepository;
import grid.bit.repository.GridRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GridService {
    private final GridRepository gridRepository;
    private final GridCellRepository gridCellRepository;

    @Transactional
    public GridResponse createGrid(GridCreateRequest request) {
        var grid = buildGrid(request);
        gridRepository.save(grid);
        return new GridResponse(grid.getId(), grid.getName(), grid.getCellSize());
    }

    @Transactional
    public void updateGrid(Long id, GridUpdateRequest request) {
        var grid = gridRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        grid.setName(request.name());
        gridRepository.save(grid);
    }

    @Transactional
    public void deleteGrid(Long id) {
        gridRepository.findById(id).ifPresent(grid -> {
            var columnIds = grid.getColumns().stream()
                    .map(AbstractEntity::getId)
                    .toList();
            var rowIds = grid.getRows().stream()
                    .map(AbstractEntity::getId)
                    .toList();
            gridCellRepository.deleteByColumnIds(columnIds);
            gridCellRepository.deleteByRowIds(rowIds);
            gridRepository.delete(grid);
        });
    }

    @Transactional(readOnly = true)
    public List<GridResponse> getGrids() {
        return gridRepository.findAll().stream()
                .map(grid -> new GridResponse(grid.getId(), grid.getName(), grid.getCellSize()))
                .toList();
    }

    private Grid buildGrid(GridCreateRequest request) {
        var grid = new Grid();
        grid.setName(request.name());
        grid.setCellSize(request.cellSize());
        grid.setColumns(buildGridColumns(grid));
        grid.setRows(buildGridRows(grid));
        return grid;
    }

    private List<GridColumn> buildGridColumns(Grid grid) {
        var columns = new ArrayList<GridColumn>();
        var column = new GridColumn();
        column.setNumber(1);
        column.setGrid(grid);
        columns.add(column);
        return columns;
    }

    private List<GridRow> buildGridRows(Grid grid) {
        var rows = new ArrayList<GridRow>();
        var row = new GridRow();
        row.setNumber(1);
        row.setGrid(grid);
        rows.add(row);
        return rows;
    }
}