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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateItem() throws Exception {

        String taskJson = "{\n" +
                "    \"title\": \"Desenvolvimento\"\n" +
                "}";

        MvcResult taskResult = mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(status().isCreated())
                .andReturn();

        String taskResponse = taskResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> itemMap = objectMapper.readValue(taskResponse, new TypeReference<Map<String, Object>>() {});
        Long taskId = Long.valueOf((Integer) itemMap.get("id"));

        String itemJson = "{\n" +
                "    \"title\" : \"Recursos Humanos\",\n" +
                "    \"description\" : \"Organização das tarefas\",\n" +
                "    \"status\" : 1,\n" +
                "    \"highlighted\" : true\n" +
                "}";

        mockMvc.perform(post("/items/tasks/{id}", taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(itemJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Recursos Humanos"))
                .andExpect(jsonPath("$.description").value("Organização das tarefas"))
                .andExpect(jsonPath("$.status").value("DOING"))
                .andExpect(jsonPath("$.highlighted").value(true));
    }

    @Test
    void shouldUpdateItem() throws Exception {

        String taskJson = "{\n" +
                "    \"title\": \"Desenvolvimento\"\n" +
                "}";

        MvcResult taskResult = mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(status().isCreated())
                .andReturn();

        String taskResponse = taskResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> taskMap = objectMapper.readValue(taskResponse, new TypeReference<Map<String, Object>>() {});
        Long taskId = Long.valueOf((Integer) taskMap.get("id"));

        String itemJson = "{\n" +
                "    \"title\" : \"Recursos Humanos\",\n" +
                "    \"description\" : \"Organização das tarefas\",\n" +
                "    \"status\" : 1,\n" +
                "    \"highlighted\" : true\n" +
                "}";

        MvcResult itemResult = mockMvc.perform(post("/items/tasks/{id}", taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(itemJson))
                .andExpect(status().isCreated())
                .andReturn();

        String itemResponse = itemResult.getResponse().getContentAsString();
        Map<String, Object> itemMap = objectMapper.readValue(itemResponse, new TypeReference<Map<String, Object>>() {});
        Long itemId = Long.valueOf((Integer) itemMap.get("id"));

        String updatedItemJson = "{\n" +
                "    \"title\" : \"Administração\",\n" +
                "    \"description\" : \"Organização das tarefas\",\n" +
                "    \"status\" : 1,\n" +
                "    \"highlighted\" : true\n" +
                "}";

        mockMvc.perform(put("/items/{id}", itemId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedItemJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Administração"))
                .andExpect(jsonPath("$.description").value("Organização das tarefas"))
                .andExpect(jsonPath("$.status").value("DOING"))
                .andExpect(jsonPath("$.highlighted").value(true));
    }

    @Test
    void shouldDeleteItem() throws Exception {

        String taskJson = "{\n" +
                "    \"title\": \"Desenvolvimento\"\n" +
                "}";

        MvcResult taskResult = mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(status().isCreated())
                .andReturn();

        String taskResponse = taskResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> taskMap = objectMapper.readValue(taskResponse, new TypeReference<Map<String, Object>>() {});
        Long taskId = Long.valueOf((Integer) taskMap.get("id"));

        String itemJson = "{\n" +
                "    \"title\" : \"Recursos Humanos\",\n" +
                "    \"description\" : \"Organização das tarefas\",\n" +
                "    \"status\" : 1,\n" +
                "    \"highlighted\" : true\n" +
                "}";

        MvcResult itemResult = mockMvc.perform(post("/items/tasks/{id}", taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(itemJson))
                .andExpect(status().isCreated())
                .andReturn();

        String itemResponse = itemResult.getResponse().getContentAsString();
        Map<String, Object> itemMap = objectMapper.readValue(itemResponse, new TypeReference<Map<String, Object>>() {});
        Long itemId = Long.valueOf((Integer) itemMap.get("id"));

        mockMvc.perform(delete("/items/{id}", itemId))
                .andExpect(status().isNoContent());

        String itemsAfterDeletion = mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertFalse(itemsAfterDeletion.contains("\"id\":" + itemId));
    }

    @Test
    void shouldUpdateItemStatus() throws Exception {

        String taskJson = "{\n" +
                "    \"title\": \"Desenvolvimento\"\n" +
                "}";

        MvcResult taskResult = mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(status().isCreated())
                .andReturn();

        String taskResponse = taskResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> taskMap = objectMapper.readValue(taskResponse, new TypeReference<Map<String, Object>>() {});
        Long taskId = Long.valueOf((Integer) taskMap.get("id"));

        String itemJson = "{\n" +
                "    \"title\" : \"Recursos Humanos\",\n" +
                "    \"description\" : \"Organização das tarefas\",\n" +
                "    \"status\" : 1,\n" +
                "    \"highlighted\" : true\n" +
                "}";

        MvcResult itemResult = mockMvc.perform(post("/items/tasks/{id}", taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(itemJson))
                .andExpect(status().isCreated())
                .andReturn();

        String itemResponse = itemResult.getResponse().getContentAsString();
        Map<String, Object> itemMap = objectMapper.readValue(itemResponse, new TypeReference<Map<String, Object>>() {});
        Long itemId = Long.valueOf((Integer) itemMap.get("id"));

        String updatedStatusItemJson = "{\n" +
                "    \"status\" : 2\n" +
                "}";

        mockMvc.perform(patch("/items/status/{id}", itemId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedStatusItemJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("DONE"));
    }

    @Test
    void shouldUpdateItemHighlighted() throws Exception {

        String taskJson = "{\n" +
                "    \"title\": \"Desenvolvimento\"\n" +
                "}";

        MvcResult taskResult = mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(status().isCreated())
                .andReturn();

        String taskResponse = taskResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> taskMap = objectMapper.readValue(taskResponse, new TypeReference<Map<String, Object>>() {});
        Long taskId = Long.valueOf((Integer) taskMap.get("id"));

        String itemJson = "{\n" +
                "    \"title\" : \"Recursos Humanos\",\n" +
                "    \"description\" : \"Organização das tarefas\",\n" +
                "    \"status\" : 1,\n" +
                "    \"highlighted\" : true\n" +
                "}";

        MvcResult itemResult = mockMvc.perform(post("/items/tasks/{id}", taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(itemJson))
                .andExpect(status().isCreated())
                .andReturn();

        String itemResponse = itemResult.getResponse().getContentAsString();
        Map<String, Object> itemMap = objectMapper.readValue(itemResponse, new TypeReference<Map<String, Object>>() {});
        Long itemId = Long.valueOf((Integer) itemMap.get("id"));

        String updatedHighlightedItemJson = "{\n" +
                "    \"highlighted\" : false\n" +
                "}";

        mockMvc.perform(patch("/items/highlighted/{id}", itemId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedHighlightedItemJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.highlighted").value(false));
    }

    @Test
    void shouldFindAllItems() throws Exception {

        String taskJson = "{\n" +
                "    \"title\": \"Desenvolvimento\"\n" +
                "}";

        MvcResult taskResult = mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(status().isCreated())
                .andReturn();

        String taskResponse = taskResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> taskMap = objectMapper.readValue(taskResponse, new TypeReference<Map<String, Object>>() {});
        Long taskId = Long.valueOf((Integer) taskMap.get("id"));

        String itemJson = "{\n" +
                "    \"title\" : \"Recursos Humanos\",\n" +
                "    \"description\" : \"Organização das tarefas\",\n" +
                "    \"status\" : 1,\n" +
                "    \"highlighted\" : true\n" +
                "}";

        MvcResult itemResult = mockMvc.perform(post("/items/tasks/{id}", taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(itemJson))
                .andExpect(status().isCreated())
                .andReturn();

        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Recursos Humanos"));
    }
}