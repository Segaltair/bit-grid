package grid.bit.model;

import lombok.Data;

@Data
public class GridCell {
    private Long gridColumnId;
    private Long gridRowId;
    private String value;
}
