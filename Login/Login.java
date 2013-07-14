package test;

import net.sourceforge.jwebunit.junit.WebTester;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Gustavo German Soria ID: 163076
 * ID: 163076
 * 
 * File: Login.php line 
 * Variable: $text [‘sitetext’ inside the database table], $message
 * Vulnerability: the variable is directly taken from the database 
 * throught the simple query like:  “select sitetext from schoolinfo”.
 * It positive because during the update, the variable is stored 
 * (in the database) and used (during the web session) without 
 * any kind of control, so it is liable to a xss attack.
 */
public class Login {

    private WebTester tester;
    private String previousValue54 = "mytext";
    private String previousValue105 = "mymessage";

    public Login() {
    }

    @Before
    public void setUp() {
        tester = new WebTester();
        tester.setBaseUrl("http://localhost:8888/schoolmate/");
    }

    @Test
    //variable text
    public void testVulnerability54() {
        tester.beginAt("index.php");
        tester.assertMatch("Today's Message");

        tester.setTextField("username", "test");
        tester.setTextField("password", "test");
        tester.submit();
        tester.assertMatch("Manage Classes");
        tester.clickLinkWithText("School");
        tester.assertMatch("Manage School Information");
        //previousValue54 = tester.getElementByXPath("html//textarea[@name='sitetext']").getTextContent();
        tester.setTextField("sitetext", "<a href=http://www.ibm.com>Malicious link Text</a>");
        tester.clickButtonWithText(" Update ");
        tester.clickLinkWithText("Log Out");
        tester.assertLinkNotPresentWithText("Malicious link Text");
    }
    @Test
    //variable message
    public void testVulnerability105() {
        tester.beginAt("index.php");
        tester.assertMatch("Today's Message");

        tester.setTextField("username", "test");
        tester.setTextField("password", "test");
        tester.submit();
        tester.assertMatch("Manage Classes");
        tester.clickLinkWithText("School");
        tester.assertMatch("Manage School Information");
        //previousValue105 = tester.getElementByXPath("html//textarea[@name='sitemessage']").getTextContent();
        tester.setTextField("sitemessage", "<a href=http://www.ibm.com>Malicious link Text</a>");
        tester.clickButtonWithText(" Update ");
        tester.clickLinkWithText("Log Out");
        tester.assertLinkNotPresentWithText("Malicious link Text");
    }
    
    @After
    public void tearDown54() {
        tester.beginAt("index.php");
        tester.assertMatch("Today's Message");

        tester.setTextField("username", "test");
        tester.setTextField("password", "test");
        tester.submit();
        tester.assertMatch("Manage Classes");
        tester.clickLinkWithText("School");
        tester.assertMatch("Manage School Information");
        tester.setTextField("sitetext", this.previousValue54);
        tester.clickButtonWithText(" Update ");
    }
    @After
    public void tearDown105() {
        tester.beginAt("index.php");
        tester.assertMatch("Today's Message");

        tester.setTextField("username", "test");
        tester.setTextField("password", "test");
        tester.submit();
        tester.assertMatch("Manage Classes");
        tester.clickLinkWithText("School");
        tester.assertMatch("Manage School Information");
        tester.setTextField("sitemessage", this.previousValue105);
        tester.clickButtonWithText(" Update ");
    }
}
