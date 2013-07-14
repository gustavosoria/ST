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

public class EditClass {
    
    private WebTester tester;
    
    public EditClass() {
    }
    
    @Before
    public void setUp() {
        tester = new WebTester();
        tester.setBaseUrl("http://localhost:8888/schoolmate/");
    }
    
     @Test
    public void testVulnerability239() {
        tester.beginAt("index.php");
        tester.assertMatch("Today's Message");
        tester.setTextField("username", "test");
        tester.setTextField("password", "test");
        tester.submit();
        tester.assertMatch("Manage Classes");
        tester.clickLinkWithText("Classes");
        tester.setWorkingForm("classes");
        tester.checkCheckbox("delete[]", "6");
        tester.setTextField("delete[]", "6'><a href=http://www.ibm.com>Malicious link</a><br />");
        tester.clickButtonWithText("Edit");
        tester.assertLinkNotPresentWithText("Malicious link");;
    }
}
