package ru.dyadischevma.ringermodes.test;

import androidx.test.rule.ActivityTestRule;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ru.dyadischevma.ringermodes.view.MainActivity;

public class Steps {
    private Robot robot = new Robot();
    private ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class, false, true);

    @Given("^Application started$")
    public void start_app() {
        robot.createMainScreen(activityRule);
    }


    @When("User click's on button add")
    public void userClickSOnButtonAdd() {
        robot.clickCreateNew();
    }

    @And("User enters Regime's (\\S+)")
    public void userEntersRegimeSName(String name) {
        robot.enterName(name);
    }


    @And("User choose (\\S+)")
    public void userChooseRegime(String regime) {
        robot.chooseRegime(regime);
    }


    @And("User clicks Save icon")
    public void userClicksSaveIcon() {
        robot.clickSave();
    }

    @Then("User see new Regime")
    public void userSeeNewRegime() {

    }
}
