package test;

import net.sourceforge.jwebunit.junit.WebTester;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Gustavo German Soria
 * ID: 163076
 * 
 * File: EditTeacher.php 
 * Variable: ‘delete’
 * Vulnerability: variable is passed within a hidden field.
 */

public class EditTeacher {
    
    private WebTester tester;
    
    public EditTeacher() {
    }
    
    @Before
    public void setUp() {
        tester = new WebTester();
        tester.setBaseUrl("http://localhost:8888/schoolmate/");
    }
    
     @Test
    public void testVulnerability111() {
        tester.beginAt("index.php");
        tester.assertMatch("Today's Message");
        tester.setTextField("username", "test");
        tester.setTextField("password", "test");
        tester.submit();
        tester.assertMatch("Manage Classes");
        tester.clickLinkWithText("Teachers");
        tester.assertMatch("Manage Teachers");

        tester.setWorkingForm("teachers");
        tester.checkCheckbox("delete[]", "1");
        tester.setTextField("delete[]",
                "<a href=http://www.ibm.com>Malicious link</a>");
        tester.clickButtonWithText("Edit");
        tester.assertLinkNotPresentWithText("Malicious link");
    }
}
