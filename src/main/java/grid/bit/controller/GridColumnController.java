package grid.bit.controller;

import grid.bit.service.GridColumnService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("grid/column")
@RestController
public class GridColumnController {
    private final GridColumnService gridColumnService;

    public GridColumnController(GridColumnService gridColumnService) {
        this.gridColumnService = gridColumnService;
    }

    // ToDo: implement insert, delete and getCommonPrefix
}