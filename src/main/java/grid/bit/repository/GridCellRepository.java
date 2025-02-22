package grid.bit.repository;


import grid.bit.model.GridCell;
import grid.bit.repository.mapper.GridCellRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GridCellRepository {
    private final JdbcOperations jdbcOperations;

    public void deleteByColumnId(Long gridColumnId) {
        jdbcOperations.update("delete from grid_cell where grid_column_id = ?", gridColumnId);
    }

    public void deleteByRowId(Long gridRowId) {
        jdbcOperations.update("delete from grid_cell where grid_row_id = ?", gridRowId);
    }

    public List<GridCell> findByColumnId(Long gridColumnId) {
        return jdbcOperations.query("select * from grid_cell where grid_column_id = ?", ps -> {
            ps.setLong(1, gridColumnId);
        }, new GridCellRowMapper());
    }

    public void createCell(GridCell cell) {
        jdbcOperations.update("insert into grid_cell(grid_column_id, grid_row_id, value) values(?, ?, ?) " +
                        "on conflict (grid_column_id, grid_row_id) do update set value = ?",
                ps -> {
                    ps.setLong(1, cell.getGridColumnId());
                    ps.setLong(2, cell.getGridRowId());
                    ps.setString(3, cell.getValue());
                    ps.setString(4, cell.getValue());
                });
    }

    public void deleteByColumnIds(List<Long> columnIds) {
        if (!columnIds.isEmpty()) {
            String inSql = String.join(",", Collections.nCopies(columnIds.size(), "?"));
            jdbcOperations.update(
                    String.format("delete from grid_cell where grid_column_id in (%s)", inSql),
                    columnIds.toArray()
            );
        }
    }

    public void deleteByRowIds(List<Long> rowIds) {
        if (!rowIds.isEmpty()) {
            String inSql = String.join(",", Collections.nCopies(rowIds.size(), "?"));
            jdbcOperations.update(
                    String.format("delete from grid_cell where grid_row_id in (%s)", inSql),
                    rowIds.toArray());
        }
    }
}
