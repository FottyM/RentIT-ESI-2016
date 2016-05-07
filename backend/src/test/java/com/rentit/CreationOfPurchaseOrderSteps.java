package com.rentit;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlDateInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.rentit.inventory.domain.model.*;
import com.rentit.inventory.domain.repository.PlantInventoryEntryRepository;
import com.rentit.inventory.domain.repository.PlantInventoryItemRepository;
import com.rentit.inventory.domain.repository.PlantReservationRepository;
import com.rentit.sales.domain.repository.PurchaseOrderRepository;
import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.withinPercentage;


/**
 * Created by lgbanuelos on 26/02/16.
 */
@ContextConfiguration(classes = {RentitRefApplication.class, Jsr310JpaConverters.class}, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class CreationOfPurchaseOrderSteps {

    @Autowired
    private WebApplicationContext wac;

    private WebClient customerBrowser;
    HtmlPage customerPage;

    @Autowired
    PlantInventoryEntryRepository entryRepository;
    @Autowired
    PlantInventoryItemRepository itemRepository;
    @Autowired
    PlantReservationRepository reservationRepository;
    @Autowired
    PurchaseOrderRepository orderRepository;

    @Before
    public void setUp() {
        customerBrowser = MockMvcWebClientBuilder.webAppContextSetup(wac).build();
    }

    @After
    public void tearOff() {
        orderRepository.deleteAll();
        reservationRepository.deleteAll();
        itemRepository.deleteAll();
        entryRepository.deleteAll();

    }

    @Given("^the following plants are currently available for rental$")
    public void the_following_plants_are_currently_available_for_rental(DataTable table) throws Throwable {
        for (Map<String, String> row: table.asMaps(String.class, String.class)) {
            PlantInventoryEntry entry = entryRepository.save(PlantInventoryEntry.of(
                    PlantInventoryEntryID.of(Long.parseLong(row.get("id"))),
                    row.get("name"),
                    row.get("description"),
                    new BigDecimal(row.get("price"))
            ));

            itemRepository.save(PlantInventoryItem.of(
                    PlantInventoryItemID.of(Long.parseLong(row.get("id"))), // I am using the same identifier for both entity/item
                    UUID.randomUUID().toString(),
                    entry.getId(),
                    EquipmentCondition.SERVICEABLE
            ));
        }
    }

    @Given("^a customer is in the \"([^\"]*)\" web page$")
    public void a_customer_is_in_the_web_page(String pageTitle) throws Throwable {
        customerPage = customerBrowser.getPage("http://localhost/dashboard/catalog/form");
    }

    @Given("^no purchase order exists in the system$")
    public void no_purchase_order_exists_in_the_system() throws Throwable {
    }

    @When("^the customer queries the plant catalog for an \"([^\"]*)\" available from \"([^\"]*)\" to \"([^\"]*)\"$")
    public void the_customer_queries_the_plant_catalog_for_an_available_from_to(String plantName, String startDate, String endDate) throws Throwable {
        // The following elements are selected by their identifier
        HtmlTextInput nameInput = (HtmlTextInput) customerPage.getElementById("name");
        HtmlDateInput startDateInput = (HtmlDateInput)customerPage.getElementById("rental-start-date");
        HtmlDateInput endDateInput = (HtmlDateInput)customerPage.getElementById("rental-end-date");
        HtmlButton submit = (HtmlButton)customerPage.getElementById("submit-button");

        nameInput.setValueAttribute(plantName);
        startDateInput.setValueAttribute(startDate);
        endDateInput.setValueAttribute(endDate);

        customerPage = submit.click();
    }

    @Then("^(\\d+) plants are shown$")
    public void plants_are_shown(int numberOfPlants) throws Throwable {
        List<?> rows = customerPage.getByXPath("//tr[contains(@class, 'table-row')]");
        assertThat(rows.size())
                .isEqualTo(numberOfPlants);
    }

    @When("^the customer selects a \"([^\"]*)\"$")
    public void the_customer_selects_a(String plantDescription) throws Throwable {
        List<?> buttons = customerPage.getByXPath(String.format("//tr[./td = '%s']//button", plantDescription));
        HtmlButton button = (HtmlButton) buttons.get(0);
        customerPage = button.click();
    }

    @Then("^a purchase order should be created with a total price of (\\d+\\.\\d+)$")
    public void a_purchase_order_should_be_created_with_a_total_price_of(BigDecimal total) throws Throwable {
        DomElement element = customerPage.getElementById("total-cost");

        assertThat(new BigDecimal(element.getTextContent()))
                .isCloseTo(total, withinPercentage(0.01));
    }
}
