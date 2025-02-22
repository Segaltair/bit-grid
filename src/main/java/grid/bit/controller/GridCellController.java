package grid.bit.controller;

import grid.bit.model.request.GridCellCreateRequest;
import grid.bit.service.GridCellService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("grid/cell")
@RestController
@RequiredArgsConstructor
public class GridCellController {
    private final GridCellService gridCellService;

    @PostMapping("/{columnId}/{rowId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setCellValue(
            @PathVariable("columnId") Long columnId,
            @PathVariable("rowId") Long rowId,
            @RequestBody @Valid GridCellCreateRequest request
    ) {
        gridCellService.createCell(columnId, rowId, request);
    }
}