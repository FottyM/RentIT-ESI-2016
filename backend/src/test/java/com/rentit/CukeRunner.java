package com.rentit;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by lgbanuelos on 26/02/16.
 */
@RunWith(Cucumber.class)
@CucumberOptions(plugin={"pretty","html:target/cucumber"})
public class CukeRunner {
}
