package grid.bit.controller;

import grid.bit.model.response.GridRowResponse;
import grid.bit.service.GridRowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("grid/row")
@RestController
@RequiredArgsConstructor
public class GridRowController {
    private final GridRowService gridRowService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GridRowResponse insertRow(
            @RequestParam(name = "afterRowId") Long afterRowId
    ) {
        return gridRowService.insertRow(afterRowId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRow(@PathVariable("id") Long id) {
        gridRowService.deleteRow(id);
    }
}
