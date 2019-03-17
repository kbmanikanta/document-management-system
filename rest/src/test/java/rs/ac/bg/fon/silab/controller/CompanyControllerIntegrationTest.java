package rs.ac.bg.fon.silab.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import rs.ac.bg.fon.silab.config.RestTestConfig;
import rs.ac.bg.fon.silab.dto.company.CompanySaveDto;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RestTestConfig.class)
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CompanyControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testInsert() throws Exception {
        CompanySaveDto company = new CompanySaveDto("SAP", "123123123");
        String companyJson = new ObjectMapper().writeValueAsString(company);

        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyJson))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.message", is("Company was successfully inserted.")));
    }

    @Test
    void testInsertWhenCompanyIsEmptyObject() throws Exception {
        CompanySaveDto company = new CompanySaveDto();
        String companyJson = new ObjectMapper().writeValueAsString(company);

        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyJson))

                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.message", is("Validation failed.")))
                .andExpect(jsonPath("$.errors.taxIdNumber", any(List.class)))
                .andExpect(jsonPath("$.errors.taxIdNumber", hasSize(1)))
                .andExpect(jsonPath("$.errors.name", any(List.class)))
                .andExpect(jsonPath("$.errors.name", hasSize(1)));
    }

    @Test
    void testInsertWhenCompanyHasEmptyProperties() throws Exception {
        CompanySaveDto company = new CompanySaveDto("", "");
        String companyJson = new ObjectMapper().writeValueAsString(company);

        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyJson))

                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.message", is("Validation failed.")))
                .andExpect(jsonPath("$.errors.taxIdNumber", any(List.class)))
                .andExpect(jsonPath("$.errors.taxIdNumber", hasSize(3)))
                .andExpect(jsonPath("$.errors.name", any(List.class)))
                .andExpect(jsonPath("$.errors.name", hasSize(1)));
    }

    @Test
    void testInsertWhenTaxIdNumberHasMoreThan9Digits() throws Exception {
        CompanySaveDto company = new CompanySaveDto("SAP", "1231231231");
        String companyJson = new ObjectMapper().writeValueAsString(company);

        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyJson))

                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.message", is("Validation failed.")))
                .andExpect(jsonPath("$.errors.taxIdNumber", any(List.class)))
                .andExpect(jsonPath("$.errors.taxIdNumber", hasSize(1)));
    }

    @Test
    void testInsertWhenTaxIdNumberHasLessThan9Digits() throws Exception {
        CompanySaveDto company = new CompanySaveDto("SAP", "123");
        String companyJson = new ObjectMapper().writeValueAsString(company);

        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyJson))

                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.message", is("Validation failed.")))
                .andExpect(jsonPath("$.errors.taxIdNumber", any(List.class)))
                .andExpect(jsonPath("$.errors.taxIdNumber", hasSize(1)));
    }

    @Test
    void testInsertWhenTaxIdNumberHasLetters() throws Exception {
        CompanySaveDto company = new CompanySaveDto("SAP", "123A23123");
        String companyJson = new ObjectMapper().writeValueAsString(company);

        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyJson))

                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.message", is("Validation failed.")))
                .andExpect(jsonPath("$.errors.taxIdNumber", any(List.class)))
                .andExpect(jsonPath("$.errors.taxIdNumber", hasSize(1)));
    }

    @Test
    void testInsertWhenCompanyWithPassedTaxIdNumberAlreadyExists() throws Exception {
        CompanySaveDto company1 = new CompanySaveDto("SAP", "123123123");
        String companyJson1 = new ObjectMapper().writeValueAsString(company1);

        CompanySaveDto company2 = new CompanySaveDto("Microsoft", "123123123");
        String companyJson2 = new ObjectMapper().writeValueAsString(company2);

        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyJson1));

        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyJson2))

                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.message", is("Validation failed.")))
                .andExpect(jsonPath("$.errors.taxIdNumber", any(List.class)))
                .andExpect(jsonPath("$.errors.taxIdNumber", hasSize(1)));
    }

    @Test
    void testUpdateWhenCompanyExists() throws Exception {
        CompanySaveDto company = new CompanySaveDto("SAP", "123123123");
        String companyJson = new ObjectMapper().writeValueAsString(company);

        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyJson));

        company.setName("Microsoft");
        company.setTaxIdNumber("111222333");
        companyJson = new ObjectMapper().writeValueAsString(company);

        mockMvc.perform(put("/companies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyJson))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.message", is("Company was successfully updated.")));
    }

    @Test
    void testUpdateWhenCompanyDoesNotExist() throws Exception {
        CompanySaveDto company = new CompanySaveDto("SAP", "123123123");
        String companyJson = new ObjectMapper().writeValueAsString(company);

        mockMvc.perform(put("/companies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyJson))

                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.message", is("Company was not found.")));
    }

    @Test
    void testUpdateWhenPassedCompanyIsNull() throws Exception {
        CompanySaveDto company = new CompanySaveDto("SAP", "123123123");
        String companyJson = new ObjectMapper().writeValueAsString(company);

        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyJson));

        company = null;
        companyJson = new ObjectMapper().writeValueAsString(company);

        mockMvc.perform(put("/companies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyJson))

                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.message", containsString("Required request body is missing")));
    }

    @Test
    void testUpdateWithoutChanges() throws Exception {
        CompanySaveDto company = new CompanySaveDto("SAP", "123123123");
        String companyJson = new ObjectMapper().writeValueAsString(company);

        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyJson));

        mockMvc.perform(put("/companies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyJson))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.message", is("Company was successfully updated.")));
    }

    @Test
    void testUpdateWhenCompanyWithPassedTaxIdNumberAlreadyExists() throws Exception {
        CompanySaveDto company1 = new CompanySaveDto("SAP", "123123123");
        String companyJson1 = new ObjectMapper().writeValueAsString(company1);

        CompanySaveDto company2 = new CompanySaveDto("Microsoft", "111222333");
        String companyJson2 = new ObjectMapper().writeValueAsString(company2);

        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyJson1));

        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyJson2));

        company2.setTaxIdNumber("123123123");
        companyJson2 = new ObjectMapper().writeValueAsString(company2);

        mockMvc.perform(put("/companies/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyJson2))

                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.message", is("Validation failed.")))
                .andExpect(jsonPath("$.errors.taxIdNumber", any(List.class)))
                .andExpect(jsonPath("$.errors.taxIdNumber", hasSize(1)));
    }

    @Test
    void testDeleteWhenCompanyExists() throws Exception {
        CompanySaveDto company = new CompanySaveDto("SAP", "123123123");
        String companyJson = new ObjectMapper().writeValueAsString(company);

        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyJson));

        mockMvc.perform(delete("/companies/1"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.message", is("Company was successfully deleted.")));
    }

    @Test
    void testDeleteWhenCompanyDoesNotExist() throws Exception {
        mockMvc.perform(delete("/companies/1"))

                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.message", is("Company was not found.")));
    }

    @Test
    void testGetByIdWhenCompanyExists() throws Exception {
        CompanySaveDto company = new CompanySaveDto("SAP", "123123123");
        String companyJson = new ObjectMapper().writeValueAsString(company);

        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyJson));

        mockMvc.perform(get("/companies/1"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is(company.getName())))
                .andExpect(jsonPath("$.taxIdNumber", is(company.getTaxIdNumber())));
    }

    @Test
    void testGetByIdWhenCompanyDoesNotExist() throws Exception {
        mockMvc.perform(get("/companies/1"))

                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.message", is("Company was not found.")));
    }

    @Test
    void testGetAll() throws Exception {
        CompanySaveDto company1 = new CompanySaveDto("SAP", "123123123");
        String companyJson1 = new ObjectMapper().writeValueAsString(company1);

        CompanySaveDto company2 = new CompanySaveDto("Microsoft", "111222333");
        String companyJson2 = new ObjectMapper().writeValueAsString(company2);

        CompanySaveDto company3 = new CompanySaveDto("Google", "101010101");
        String companyJson3 = new ObjectMapper().writeValueAsString(company3);

        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyJson1));
        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyJson2));
        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyJson3));

        mockMvc.perform(get("/companies"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", any(List.class)))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("SAP")))
                .andExpect(jsonPath("$[0].taxIdNumber", is("123123123")));
    }

    @Test
    void testGetByTaxIdNumberWhenCompanyExists() throws Exception {
        CompanySaveDto company = new CompanySaveDto("SAP", "123123123");
        String companyJson = new ObjectMapper().writeValueAsString(company);

        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyJson));

        mockMvc.perform(get("/companies?taxIdNumber=123123123"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", any(List.class)))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("SAP")))
                .andExpect(jsonPath("$[0].taxIdNumber", is("123123123")));
    }

    @Test
    void testGetByTaxIdNumberWhenCompanyDoesNotExist() throws Exception {
        mockMvc.perform(get("/companies?taxIdNumber=123123123"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", any(List.class)))
                .andExpect(jsonPath("$", hasSize(0)));
    }

}
