package grid.bit.controller;

import grid.bit.AbstractIntegrationTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Sql("GridCellControllerTest.sql")
public class GridCellControllerTest extends AbstractIntegrationTest {
    private static final String BASE_URL = "/grid/cell";

    @Autowired
    private MockMvc mockMvc;

    @Disabled // ToDo: enable
    @Test
    public void set_changesValueInCell() throws Exception {
        mockMvc.perform(post(BASE_URL + "/{columnId}/{rowId}", 55500055530L, 55500055540L).contentType(APPLICATION_JSON)
                        .content("{\"value\":\"100110111011\"}"))
                .andExpect(status().isNoContent());
        flush();

        // ToDo: finish the test
    }
}