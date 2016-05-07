package com.rentit.maintenance.domain.repository;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.rentit.RentitRefApplication;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RentitRefApplication.class)
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class MaintenanceRepositoryTest {
    @Autowired
    MaintenanceRepository maintenanceRepository;

    @Test
    @Sql("plants-dataset.sql")
    public void queryPlantCatalog() {
        assertThat(maintenanceRepository.count()).isEqualTo(3l);

        assertThat(maintenanceRepository.numberOfCorrectiveRepairsPerYear(3))
                .hasSize(2)
                .extracting(Pair::getLeft, Pair::getRight)
                .contains(tuple(2014, 3L), tuple(2015, 2L));


        Map<Integer, BigDecimal> expected = ImmutableMap.of(2014, BigDecimal.valueOf(500), 2015, BigDecimal.valueOf(400));

        for (Pair<Integer, BigDecimal> pair: maintenanceRepository.costOfCorrectiveRepairsPerYear(3)) {
            assertThat(pair.getRight()).isCloseTo(expected.get(pair.getLeft()), withinPercentage(0.01));
        }
    }
}