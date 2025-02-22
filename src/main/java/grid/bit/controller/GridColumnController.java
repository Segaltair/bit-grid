package grid.bit.controller;

import grid.bit.model.response.GridColumnPrefixResponse;
import grid.bit.model.response.GridColumnResponse;
import grid.bit.service.GridColumnService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("grid/column")
@RestController
@RequiredArgsConstructor
public class GridColumnController {
    private final GridColumnService gridColumnService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GridColumnResponse insertColumn(
            @RequestParam(name = "afterColumnId") Long afterColumnId
    ) {
        return gridColumnService.insertColumn(afterColumnId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteColumn(@PathVariable("id") Long id) {
        gridColumnService.deleteColumn(id);
    }

    @GetMapping("/{id}/common-prefix")
    public GridColumnPrefixResponse getCommonPrefix(@PathVariable("id") Long id) {
        return gridColumnService.getCommonPrefix(id);
    }
}