package grid.bit.repository.mapper;

import grid.bit.model.GridCell;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GridCellRowMapper implements RowMapper<GridCell> {
    @Override
    public GridCell mapRow(ResultSet rs, int rowNum) throws SQLException {
        var cell = new GridCell();
        cell.setGridColumnId(rs.getLong("grid_column_id"));
        cell.setGridRowId(rs.getLong("grid_row_id"));
        cell.setValue(rs.getString("value"));
        return cell;
    }
}
