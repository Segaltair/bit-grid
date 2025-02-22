package grid.bit.controller;

import grid.bit.model.request.GridCreateRequest;
import grid.bit.model.request.GridUpdateRequest;
import grid.bit.model.response.GridResponse;
import grid.bit.service.GridService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("grid")
@RestController
@RequiredArgsConstructor
public class GridController {
    private final GridService gridService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GridResponse createGrid(@RequestBody @Valid GridCreateRequest request) {
        return gridService.createGrid(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGrid(@PathVariable Long id, @RequestBody @Valid GridUpdateRequest request) {
        gridService.updateGrid(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGrid(@PathVariable Long id) {
        gridService.deleteGrid(id);
    }

    @GetMapping
    public List<GridResponse> getGrids() {
        return gridService.getGrids();
    }
}