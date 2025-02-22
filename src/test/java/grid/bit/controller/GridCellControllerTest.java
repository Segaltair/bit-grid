package grid.bit.controller;

import grid.bit.AbstractIntegrationTest;
import grid.bit.repository.GridCellRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Sql("GridCellControllerTest.sql")
public class GridCellControllerTest extends AbstractIntegrationTest {
    private static final String BASE_URL = "/grid/cell";

    @Autowired
    private GridCellRepository gridCellRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void set_changesValueInCell() throws Exception {
        mockMvc.perform(post(BASE_URL + "/{columnId}/{rowId}", 55500055530L, 55500055540L).contentType(APPLICATION_JSON)
                        .content("{\"value\":\"100110111011\"}"))
                .andExpect(status().isNoContent());
        flush();

        var cells = gridCellRepository.findByColumnId(55500055530L);
        assertThat(cells)
                .hasSize(1)
                .matches(c -> 55500055530L == c.getFirst().getGridColumnId())
                .matches(c -> 55500055540L == c.getFirst().getGridRowId())
                .matches(c -> "100110111011".equals(c.getFirst().getValue()));
    }
}