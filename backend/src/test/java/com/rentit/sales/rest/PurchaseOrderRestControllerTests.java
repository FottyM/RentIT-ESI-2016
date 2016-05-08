package com.rentit.sales.rest;

/**
 * Created by akaiz on 3/24/2016.
 */

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import com.rentit.RentitRefApplication;
import com.rentit.common.application.dto.BusinessPeriodDTO;
import com.rentit.inventory.application.dto.PlantInventoryEntryDTO;
import com.rentit.inventory.rest.PlantInventoryEntryRestController;
import com.rentit.sales.application.dto.PurchaseOrderDTO;
import com.rentit.sales.domain.model.POStatus;
import com.rentit.sales.domain.model.PurchaseOrder;
import com.rentit.sales.domain.model.PurchaseOrderExtensionID;
import com.rentit.sales.domain.repository.PurchaseOrderExtensionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.hateoas.core.AnnotationMappingDiscoverer;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RentitRefApplication.class)
@WebAppConfiguration
@DirtiesContext
@ActiveProfiles("test")
public class PurchaseOrderRestControllerTests {
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    PurchaseOrderExtensionRepository purchaseOrderExtensionRepository;
    private MockMvc mockMvc;
    @Autowired @Qualifier("_halObjectMapper")
    ObjectMapper mapper;
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }


    @Test
    @Sql("plants-dataset.sql")
    public void testPurchaseOrderAcceptance() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/inventory/plants?name=Exc&startDate=2016-03-14&endDate=2016-03-25"))
                .andReturn();
        List<PlantInventoryEntryDTO> plants =
                mapper.readValue(result.getResponse().getContentAsString(),
                        new TypeReference<List<PlantInventoryEntryDTO>>() { });

        PurchaseOrderDTO order = new PurchaseOrderDTO();
        order.setPlant(plants.get(2));
        order.setRentalPeriod(BusinessPeriodDTO.of(LocalDate.now(), LocalDate.now()));
        order.setEmail("akaizat1@gmail.com");


        result = mockMvc.perform(post("/api/sales/orders")
                .content(mapper.writeValueAsString(order))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", not(isEmptyOrNullString())))
                .andReturn();

        order = mapper.readValue(result.getResponse().getContentAsString(), PurchaseOrderDTO.class);

        assertThat(order.get_xlink("accept"), is(notNullValue()));

        MvcResult result2= mockMvc.perform(delete(order.get_xlink("reject").getHref()))
                .andReturn();
        PurchaseOrderDTO purchaseOrder= mapper.readValue(result2.getResponse().getContentAsString(),
                new TypeReference<PurchaseOrderDTO>() { });
        assertThat(purchaseOrder.getStatus(), is(POStatus.REJECTED));

        assertThat(purchaseOrder.get_xlink("resubmit"), is(notNullValue()));

       MvcResult result1= mockMvc.perform(post(order.get_xlink("accept").getHref()))
                .andReturn();
         PurchaseOrderDTO purchaseOrder2= mapper.readValue(result1.getResponse().getContentAsString(),
                new TypeReference<PurchaseOrderDTO>() { });
        assertThat(purchaseOrder2.getStatus(), is(POStatus.OPEN));


        BusinessPeriodDTO businessPeriodDTO=BusinessPeriodDTO.of(LocalDate.now(), LocalDate.now());
        MvcResult result3= mockMvc.perform(post(purchaseOrder2.get_xlink("extend").getHref())

                .content(mapper.writeValueAsString(businessPeriodDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", not(isEmptyOrNullString())))
                .andReturn();

        PurchaseOrderDTO order2 = new PurchaseOrderDTO();
        order2 = mapper.readValue(result3.getResponse().getContentAsString(), PurchaseOrderDTO.class);
        System.out.println(order2);


        assertThat(order2.get_xlink("acceptRpExtension"), is(notNullValue()));
        //
        MvcResult result4= mockMvc.perform(post(order2.get_xlink("acceptRpExtension").getHref()))
                .andReturn();
        PurchaseOrderDTO purchaseOrder4= mapper.readValue(result4.getResponse().getContentAsString(),
                new TypeReference<PurchaseOrderDTO>() { });

        for (PurchaseOrderExtensionID purchaseOrderExtensionID :purchaseOrder4.getExtensions()){


            assertThat(purchaseOrderExtensionRepository.findOne(purchaseOrderExtensionID).getStatus(), is(POStatus.OPEN));
        }
        MvcResult result5= mockMvc.perform(delete(order2.get_xlink("rejectRpExtension").getHref()))
                .andReturn();
        PurchaseOrderDTO purchaseOrder5= mapper.readValue(result5.getResponse().getContentAsString(),
                new TypeReference<PurchaseOrderDTO>() { });

        for (PurchaseOrderExtensionID purchaseOrderExtensionID :purchaseOrder5.getExtensions()){


            assertThat(purchaseOrderExtensionRepository.findOne(purchaseOrderExtensionID).getStatus(), is(POStatus.REJECTED));
        }


    }

    }





