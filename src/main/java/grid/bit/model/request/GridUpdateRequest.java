package grid.bit.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record GridUpdateRequest(
        @NotBlank
        @Size(min = 1, max = 200)
        String name
) {
}
