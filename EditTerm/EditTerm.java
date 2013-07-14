package test;

import net.sourceforge.jwebunit.junit.WebTester;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Gustavo German Soria
 * ID: 163076
 * 
 * File: EditTerm.php 
 * Variable: ‘delete’
 * Vulnerability: variable is passed within a hidden field.
 */

public class EditTerm {
    
    private WebTester tester;
    
    public EditTerm() {
    }
    
    @Before
    public void setUp() {
        tester = new WebTester();
        tester.setBaseUrl("http://localhost:8888/schoolmate/");
    }

    @Test
    public void testVulnerability44() {
        tester.beginAt("index.php");
        tester.assertMatch("Today's Message");
        tester.setTextField("username", "test");
        tester.setTextField("password", "test");
        tester.submit();
        tester.clickLinkWithText("Terms");
        tester.assertMatch("Manage Terms");
        tester.setWorkingForm("terms");
        tester.checkCheckbox("delete[]", "3");
        tester.setTextField("delete[]", "<a href=http://www.ibm.com>Malicious link</a><br />");
        tester.clickButtonWithText("Edit");

        tester.assertLinkNotPresentWithText("Malicious link");
    }
}
