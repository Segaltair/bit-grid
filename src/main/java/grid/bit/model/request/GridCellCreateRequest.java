package grid.bit.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record GridCellCreateRequest(
        @Size(min = 1, max = 100000)
        String value
) {
}
