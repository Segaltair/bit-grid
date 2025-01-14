package grid.bit.controller;

import grid.bit.AbstractIntegrationTest;
import grid.bit.model.Grid;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@Sql("GridControllerTest.sql")
public class GridControllerTest extends AbstractIntegrationTest {
    private static final String BASE_URL = "/grid";

    @Autowired
    private MockMvc mockMvc;

    @Disabled // ToDo: enable
    @Test
    public void getAll_returnsCollectionOfGrids() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(content().json("[{id: 55500055510, name: 'A', cellSize: 5}, {id: 55500055520, name: 'B', cellSize: 10}]", true));
    }

    @Disabled // ToDo: enable
    @Test
    public void create_storesNewGrid() throws Exception {
        mockMvc.perform(post(BASE_URL).contentType(APPLICATION_JSON).accept(APPLICATION_JSON)
                        .content("{\"name\": \"C\", \"cellSize\": 100}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("id").exists());
        flushAndClear();

        Grid grid = findByName(Grid.class, "C");
        assertThat(grid).isNotNull();
        assertThat(grid.getCellSize()).isEqualTo(100);
        assertThat(grid.getColumns()).hasSize(1);
        assertThat(grid.getRows()).hasSize(1);
    }

    @Disabled // ToDo: enable
    @Test
    public void create_invalidName_rejects() throws Exception {
        mockMvc.perform(post(BASE_URL).contentType(APPLICATION_JSON).accept(APPLICATION_JSON)
                        .content("{\"name\": \" \", \"cellSize\": 100}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(content().string(containsString("name")));
    }

    @Disabled // ToDo: enable
    @Test
    public void update_changesGridName() throws Exception {
        mockMvc.perform(put(BASE_URL + "/{id}", 55500055510L).contentType(APPLICATION_JSON)
                        .content("{\"name\": \"Bar\"}"))
                .andExpect(status().isNoContent());
        flushAndClear();

        Grid grid = findByName(Grid.class, "Bar");
        assertThat(grid).isNotNull();
        assertThat(grid.getId()).isEqualTo(55500055510L);
    }

    @Disabled // ToDo: enable
    @Test
    public void delete_removesGridAndItsData() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/{id}", 55500055510L))
                .andExpect(status().isNoContent());
        flush();

        assertThat(find(Grid.class, 55500055510L)).isNull();
    }
}