package com.playo.hackerfeed;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by vichu on 12/10/17.
 */

@RunWith(AndroidJUnit4.class)
public class TestLauncherActivity {

    @Rule
    public ActivityTestRule<LauncherActivity> activityTestRule = new ActivityTestRule<>(LauncherActivity.class);

    @Before
    public void init() {
        activityTestRule.getActivity().findViewById(R.id.edt_category);
    }

    @Test
    public void testEditCategory() {
        
    }

}
