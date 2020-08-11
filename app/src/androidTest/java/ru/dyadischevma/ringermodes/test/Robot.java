package ru.dyadischevma.ringermodes.test;


import android.view.View;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;

import ru.dyadischevma.ringermodes.R;
import ru.dyadischevma.ringermodes.data.RingerMode;
import ru.dyadischevma.ringermodes.view.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;

public class Robot {
    public void createMainScreen(ActivityTestRule<MainActivity> testRule) {
        testRule.launchActivity(null);
    }

    public void clickCreateNew() {
        onView(ViewMatchers.withId(ru.dyadischevma.ringermodes.R.id.floatingActionButton)).perform(click());
    }

    public void enterName(String name) {
        onView(ViewMatchers.withId(ru.dyadischevma.ringermodes.R.id.editTextTextName)).perform(typeText(name));
    }

    public void chooseRegime(String regime) {
        RingerMode ringerMode = RingerMode.valueOf(regime);
        Matcher<View> matcher;
        switch (ringerMode) {
            case SILENT:
                matcher = ViewMatchers.withId(ru.dyadischevma.ringermodes.R.id.radioButtonSilent);
                break;
            case VIBRATE:
                matcher = ViewMatchers.withId(ru.dyadischevma.ringermodes.R.id.radioButtonVibrate);
                break;
            default:
                matcher = ViewMatchers.withId(ru.dyadischevma.ringermodes.R.id.radioButtonNormal);
        }
        onView(matcher).perform(click());
    }

    public void clickSave() {
        onView(ViewMatchers.withId(R.id.floatingActionButtonSave)).perform(click());
    }
}