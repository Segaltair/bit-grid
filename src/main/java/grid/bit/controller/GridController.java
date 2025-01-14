package grid.bit.controller;

import grid.bit.service.GridService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("grid")
@RestController
public class GridController {
    private final GridService gridService;

    public GridController(GridService gridService) {
        this.gridService = gridService;
    }

    // ToDo: implement getAll, create, update and delete
}