package test;

import net.sourceforge.jwebunit.junit.WebTester;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Gustavo German Soria
 */
public class ManageClass {

    private WebTester tester;
    private String previousValue = null;

    public ManageClass() {
    }

    @Before
    public void setUp() {
        tester = new WebTester();
        tester.setBaseUrl("http://localhost:8888/schoolmate/");
    }

    @After
    public void tearDown() {
        if (previousValue != null) {
            tester.beginAt("index.php");
            tester.assertMatch("Today's Message");

            tester.setTextField("username", "test");
            tester.setTextField("password", "test");
            tester.submit();

            tester.assertMatch("Manage Classes");
            tester.clickLinkWithText("Classes");
            tester.clickLinkWithText("1");
            tester.setWorkingForm("classes");

            tester.assertCheckboxPresent("delete[]", "3");
            tester.checkCheckbox("delete[]", "3");
            tester.clickButtonWithText("Edit");

            tester.assertMatch("Edit Class");
            tester.setTextField("title", previousValue);

            tester.clickButtonWithText("Edit Class");
        }
    }

    @Test
    public void VulnerabilityManageClass() {
        tester.beginAt("index.php");
        tester.assertMatch("Today's Message");

        tester.setTextField("username", "test");
        tester.setTextField("password", "test");
        tester.submit();

        tester.assertMatch("Manage Classes");
        tester.clickLinkWithText("Classes");
        tester.clickLinkWithText("1");
        tester.setWorkingForm("classes");

        tester.assertMatch("classname");
        tester.assertCheckboxPresent("delete[]", "3");
        tester.checkCheckbox("delete[]", "3");
        tester.clickButtonWithText("Edit");

        tester.assertMatch("Edit Class");
        previousValue = tester.getElementByXPath("html//input[@name='title']").getTextContent();
        tester.setTextField("title", "<a href=ask.it>a</a>");

        tester.clickButtonWithText("Edit Class");

        tester.assertLinkNotPresentWithText("a");
    }
}
