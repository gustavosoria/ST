package test;

import net.sourceforge.jwebunit.junit.WebTester;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Gustavo German Soria
 * ID: 163076
 * 
 * File: EditClass.php 
 * Variable: ‘delete’
 * Vulnerability: variable is passed within a hidden field.
 */

public class EditUser {
    
    private WebTester tester;
    
    public EditUser() {
    }
    
    @Before
    public void setUp() {
        tester = new WebTester();
        tester.setBaseUrl("http://localhost:8888/schoolmate/");
    }
    
     @Test
    public void testVulnerability149() {
        tester.beginAt("index.php");
        tester.assertMatch("Today's Message");
        tester.setTextField("username", "test");
        tester.setTextField("password", "test");
        tester.submit();
        tester.assertMatch("Manage Classes");
        tester.clickLinkWithText("Users");
        tester.assertMatch("Manage Users");
        tester.setWorkingForm("users");
        tester.checkCheckbox("delete[]", "2");
        tester.setTextField("delete[]", "2 '><a href=http://www.ibm.com>Malicious link</a> <br />");
        tester.clickButtonWithText("Edit");
        tester.assertLinkNotPresentWithText("Malicious link");
    }
}
