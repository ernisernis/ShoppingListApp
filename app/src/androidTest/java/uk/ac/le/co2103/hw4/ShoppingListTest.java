package uk.ac.le.co2103.hw4;

import android.os.IBinder;
import android.view.View;
import android.view.WindowManager;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.Root;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4ClassRunner.class)
public class ShoppingListTest {

    @Rule
    public ActivityScenarioRule rule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testAddNewList() {
        onView(withId(R.id.recyclerview)).check(hasItemsCount(0));
        onView(withId(R.id.fab)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.etUploadName)).check(matches(isDisplayed()));
        onView(withId(R.id.bUploadImage)).check(matches(isDisplayed()));
        onView(withId(R.id.etUploadName)).perform(typeText("Birthday Party"), closeSoftKeyboard());
        onView(withId(R.id.bUploadImage)).perform(click());
        onView(withText("Birthday Party")).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerview)).check(hasItemsCount(1));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.singleproductitem)).perform(longClick());
        onView(withText("YES")).perform(click());
        onView(withId(R.id.fab)).perform(click());
    }

    @Test
    public void testDeleteList() {

        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.etUploadName)).perform(typeText("Birthday Party"), closeSoftKeyboard());
        onView(withId(R.id.bUploadImage)).perform(click());

        onView(withId(R.id.singleproductitem)).perform(longClick());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("YES")).perform(click());
    }


    @Test
    public void testAddProduct() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.fab)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.etUploadName)).check(matches(isDisplayed()));
        onView(withId(R.id.bUploadImage)).check(matches(isDisplayed()));
        onView(withId(R.id.etUploadName)).perform(typeText("Birthday Party"), closeSoftKeyboard());
        onView(withId(R.id.bUploadImage)).perform(click());
        onView(withText("Birthday Party")).check(matches(isDisplayed()));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.singleproductitem)).perform(click());
        onView(withId(R.id.fabAddProduct)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.editTextName)).perform(typeText("Cake"));
        onView(withId(R.id.editTextQuantity)).perform(typeText("1"));
        onView(withId(R.id.bUplaodProduct)).perform(click());
        onView(withId(R.id.recyclerview2)).check(hasItemsCount(1));

        onView(withId(R.id.singleproductitem2)).perform(click());
        onView(withText("DELETE")).perform(click());
        Espresso.pressBack();
        onView(withId(R.id.singleproductitem)).perform(longClick());
        onView(withText("YES")).perform(click());


    }

    @Test
    public void testAddDuplicateProduct() {

        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.etUploadName)).perform(typeText("Birthday Party"), closeSoftKeyboard());
        onView(withId(R.id.bUploadImage)).perform(click());

        onView(withId(R.id.singleproductitem)).perform(click());
        onView(withId(R.id.fabAddProduct)).perform(click());
        onView(withId(R.id.editTextName)).perform(typeText("Cake"));
        onView(withId(R.id.editTextQuantity)).perform(typeText("1"));
        onView(withId(R.id.bUplaodProduct)).perform(click());

        //onView(withId(R.id.singleproductitem)).perform(click());
        onView(withId(R.id.fabAddProduct)).perform(click());
        onView(withId(R.id.editTextName)).perform(typeText("Cake"));
        onView(withId(R.id.editTextQuantity)).perform(typeText("1"));
        onView(withId(R.id.bUplaodProduct)).perform(click());
        onView(withText(R.string.mssg)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));

        onView(withId(R.id.singleproductitem2)).perform(click());
        onView(withText("DELETE")).perform(click());
        Espresso.pressBack();
        onView(withId(R.id.singleproductitem)).perform(longClick());
        onView(withText("YES")).perform(click());
    }


    @Test
    public void testEditProduct() {


        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.etUploadName)).perform(typeText("Birthday Party"), closeSoftKeyboard());
        onView(withId(R.id.bUploadImage)).perform(click());

        onView(withId(R.id.singleproductitem)).perform(click());
        onView(withId(R.id.fabAddProduct)).perform(click());
        onView(withId(R.id.editTextName)).perform(typeText("Cake"));
        onView(withId(R.id.editTextQuantity)).perform(typeText("1"));
        onView(withId(R.id.bUplaodProduct)).perform(click());

        onView(withId(R.id.singleproductitem2)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("EDIT")).perform(click());
        onView(withId(R.id.increment)).perform(click());
        onView(withId(R.id.increment)).perform(click());
        onView(withId(R.id.saveButton)).perform(click());
        onView(withText("3")).check(matches(isDisplayed()));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.singleproductitem2)).perform(click());
        onView(withText("DELETE")).perform(click());
        Espresso.pressBack();
        onView(withId(R.id.singleproductitem)).perform(longClick());
        onView(withText("YES")).perform(click());

    }

    @Test
    public void testDeleteProduct() {

        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.etUploadName)).perform(typeText("Birthday Party"), closeSoftKeyboard());
        onView(withId(R.id.bUploadImage)).perform(click());

        onView(withId(R.id.singleproductitem)).perform(click());
        onView(withId(R.id.fabAddProduct)).perform(click());
        onView(withId(R.id.editTextName)).perform(typeText("Cake"));
        onView(withId(R.id.editTextQuantity)).perform(typeText("1"));
        onView(withId(R.id.bUplaodProduct)).perform(click());

        onView(withId(R.id.singleproductitem2)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("DELETE")).perform(click());
        onView(withId(R.id.recyclerview2)).check(hasItemsCount(0));
        Espresso.pressBack();
        onView(withId(R.id.singleproductitem)).perform(longClick());
        onView(withText("YES")).perform(click());

    }

    public static ViewAssertion hasItemsCount(final int count) {
        return new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException e) {
                if (!(view instanceof RecyclerView)) {
                    throw e;
                }
                RecyclerView rv = (RecyclerView) view;
                Assert.assertEquals(rv.getAdapter().getItemCount(), count);
            }
        };
    }

    public class ToastMatcher extends TypeSafeMatcher<Root> {

        @Override
        public void describeTo(Description description) {
            description.appendText("Product already exists");
        }

        @Override
        public boolean matchesSafely(Root root) {
            int type = root.getWindowLayoutParams().get().type;
            if ((type == WindowManager.LayoutParams.TYPE_TOAST)) {
                IBinder windowToken = root.getDecorView().getWindowToken();
                IBinder appToken = root.getDecorView().getApplicationWindowToken();
                if (windowToken == appToken) {
                    //means this window isn't contained by any other windows.
                    return true;
                }
            }
            return false;
        }
    }
}