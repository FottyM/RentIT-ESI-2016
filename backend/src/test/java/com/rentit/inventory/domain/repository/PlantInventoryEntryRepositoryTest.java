package com.rentit.inventory.domain.repository;

import com.rentit.RentitRefApplication;
import com.rentit.common.domain.model.BusinessPeriod;
import com.rentit.inventory.application.service.InventoryService;
import com.rentit.inventory.domain.model.*;
import com.rentit.inventory.infrastructure.idgeneration.InventoryIdentifierGenerator;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static com.rentit.inventory.domain.repository.InventorySpecifications.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RentitRefApplication.class)
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class PlantInventoryEntryRepositoryTest {
    @Autowired
    PlantInventoryEntryRepository plantRepo;
    @Autowired
    PlantInventoryItemRepository plantInvItemRepo;
    @Autowired
    PlantReservationRepository reservRepo;
    @Autowired
    InventoryRepository inventoryRepository;
    @Autowired
    InventoryIdentifierGenerator inventoryIdentifierGenerator;
    @Autowired
    InventoryService inventoryService;

    @Test
    @Sql("plants-dataset.sql")
    public void queryPlantCatalog() {
        System.out.println(reservRepo.findAll());

        List<Pair<PlantInventoryEntry, Integer>> listp = inventoryRepository.findNumberOfAvailable("Mini", BusinessPeriod.of(LocalDate.now(), LocalDate.now().plusDays(2)));

        assertThat(listp)
                .filteredOn(pair -> pair.getLeft().getId().getId() == 1l)
                .extracting("right")
                .contains(2L)
            ;

        PlantInventoryEntry plant = plantRepo.findOne(PlantInventoryEntryID.of(1l));
        assertThat(inventoryRepository.checkIfAvailableFor(plant, BusinessPeriod.of(LocalDate.now(), LocalDate.now())))
            .isTrue();
        assertThat(inventoryRepository.checkIfPossiblyAvailableFor(plant, BusinessPeriod.of(LocalDate.now(), LocalDate.now())))
            .isTrue();

        PlantInventoryEntry unservPlant = plantRepo.findOne(PlantInventoryEntryID.of(2l));
        assertThat(inventoryRepository.checkIfAvailableFor(unservPlant, BusinessPeriod.of(LocalDate.now(), LocalDate.now())))
            .isFalse();
        assertThat(inventoryRepository.checkIfAvailableFor(unservPlant, BusinessPeriod.of(LocalDate.of(2016,4,18), LocalDate.of(2016,4,20))))
            .isFalse();

        assertThat(inventoryRepository.checkIfPossiblyAvailableFor(unservPlant, BusinessPeriod.of(LocalDate.now(), LocalDate.now())))
                .isFalse();
//        assertThat(inventoryRepository.checkIfPossiblyAvailableFor(unservPlant, BusinessPeriod.of(LocalDate.of(2016,4,18), LocalDate.of(2016,4,20))))
//            .isTrue();


        assertThat(inventoryRepository.findNumberOfAvailable("Mini", BusinessPeriod.of(LocalDate.now(), LocalDate.now().plusDays(2))).size())
            .isEqualTo(1);

        assertThat(plantRepo.count(plantNameContains("Mini").and(entryHasAvailableItemFor(BusinessPeriod.of(LocalDate.now(), LocalDate.now().plusDays(4)), EquipmentCondition.SERVICEABLE))))
            .isEqualTo(1l);


    }
}