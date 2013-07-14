package test;

import net.sourceforge.jwebunit.junit.WebTester;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Gustavo German Soria
 * ID: 163076
 * 
 * File: EditSemester.php 
 * Variable: ‘delete’
 * Vulnerability: variable is passed within a hidden field.
 */

public class EditSemester {
    
    private WebTester tester;
    
    public EditSemester() {
    }
    
    @Before
    public void setUp() {
        tester = new WebTester();
        tester.setBaseUrl("http://localhost:8888/schoolmate/");
    }
    
     @Test
    public void testVulnerability85() {
        tester.beginAt("index.php");
        tester.assertMatch("Today's Message");
        tester.setTextField("username", "test");
        tester.setTextField("password", "test");
        tester.submit();
        tester.assertMatch("Manage Classes");
        tester.clickLinkWithText("Semesters");
        tester.setWorkingForm("semesters");
        tester.checkCheckbox("delete[]", "2");
        tester.setTextField("delete[]","<a href=http://www.google.com>Malicious link</a><br />");

        tester.clickButtonWithText("Edit");
        tester.assertLinkNotPresentWithText("Malicious link");
    }
}
