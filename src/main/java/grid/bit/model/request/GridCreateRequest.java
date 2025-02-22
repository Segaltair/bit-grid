package grid.bit.model.request;

import jakarta.validation.constraints.*;

public record GridCreateRequest(
        @NotBlank
        @Size(min = 1, max = 200)
        String name,

        @NotNull
        @Min(1)
        @Max(100000)
        Integer cellSize
) {
}
