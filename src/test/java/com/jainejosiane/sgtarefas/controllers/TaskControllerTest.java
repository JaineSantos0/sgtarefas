package com.jainejosiane.sgtarefas.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateTaskWithoutItem() throws Exception {

        String taskJson = "{\n" +
                "    \"title\" : \"producao\",\n" +
                "    \"itemList\" : []\n" +
                "}";

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("producao"));
    }

    @Test
    void shouldCreateTaskWithItems() throws Exception {

        String taskJson = "{\n" +
                "    \"title\" : \"producao\",\n" +
                "    \"itemList\" : [\n" +
                "        {\n" +
                "            \"title\" : \"Administração\",\n" +
                "            \"description\" : \"Organização das tarefas de Desenvolvimento\",\n" +
                "            \"status\" : 1,\n" +
                "            \"highlighted\" : true\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("producao"))
                .andExpect(jsonPath("$.itemList[0].title").value("Administração"))
                .andExpect(jsonPath("$.itemList[0].description").value("Organização das tarefas de Desenvolvimento"))
                .andExpect(jsonPath("$.itemList[0].status").value("DOING"))
                .andExpect(jsonPath("$.itemList[0].highlighted").value(true));
    }

    @Test
    void shouldFindAllTasks() throws Exception {

        String taskJson = "{\n" +
                "    \"title\" : \"producao\",\n" +
                "    \"itemList\" : []\n" +
                "}";

        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(taskJson));

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("producao"));
    }

    @Test
    void shouldFindTaskById() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        String taskJson = "{\n" +
                "    \"title\" : \"producao\",\n" +
                "    \"itemList\" : []\n" +
                "}";

        MvcResult taskResult = mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andReturn();

        String taskResponse = taskResult.getResponse().getContentAsString();
        Map<String, Object> itemMap = objectMapper.readValue(taskResponse, new TypeReference<Map<String, Object>>() {});
        Long taskId = Long.valueOf((Integer) itemMap.get("id"));

        mockMvc.perform(get("/tasks/{id}", taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("producao"));
    }
}