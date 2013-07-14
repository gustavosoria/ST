package test;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.InputElementFactory;
import net.sourceforge.jwebunit.api.IElement;
import net.sourceforge.jwebunit.htmlunit.HtmlUnitElementImpl;
import net.sourceforge.jwebunit.junit.WebTester;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.helpers.AttributesImpl;

/**
 * @author Gustavo German Soria 
 * ID: 163076
 *
 * File: EditAssignment.php 
 * Variables: ‘delete’, ‘selectclass’
 * Vulnerability: variables are passed  within hidden fields, and can be suitably altered by changing the values.
 */
public class EditAssignment {

    private WebTester tester;

    public EditAssignment() {
    }

    @Before
    public void setUp() {
        tester = new WebTester();
        tester.setBaseUrl("http://localhost:8888/schoolmate2/");
    }

    @Test
    //variable selectclass
    public void testVulnerability37a() {
        tester.beginAt("index.php");
        tester.assertMatch("Today's Message");
        tester.setTextField("username", "teacher1");
        tester.setTextField("password", "nonlaso");
        tester.submit();
        tester.assertMatch("topolino topolino's Classes");
        tester.clickLinkWithText("class1");
        tester.assertTextPresent("Class Settings");
        tester.clickLinkWithText("Assignments");
        tester.setWorkingForm("assignments");
        tester.checkCheckbox("delete[]", "1");
        tester.clickButtonWithText("Edit");
        tester.assertTextPresent("Edit Assignment");
        tester.setWorkingForm("editassignment");
        tester.setHiddenField("selectclass", "1 '> <a href=\"http://www.ibm.com\">Malicious link</a> <br / '");
        addSubmitButton("//form[@name='editassignment']");
        tester.submit();

        tester.assertLinkNotPresentWithText("Malicious link");
    }

    @Test
    //variable delete
    public void testVulnerability37b() {
        tester.beginAt("index.php");
        tester.assertMatch("Today's Message");
        tester.setTextField("username", "teacher1");
        tester.setTextField("password", "nonlaso");
        tester.submit();
        tester.assertMatch("topolino topolino's Classes");
        tester.clickLinkWithText("class1");
        tester.assertTextPresent("Class Settings");
        tester.clickLinkWithText("Assignments");
        tester.setWorkingForm("assignments");
        tester.checkCheckbox("delete[]", "1");
        tester.setTextField("delete[]","1'> <a href=http://www.ibm.com>Malicious link</a> <br / '");
        tester.clickButtonWithText("Edit");

        tester.assertLinkNotPresentWithText("Malicious link");
    }

    private void addSubmitButton(String expression) {
        IElement element = tester.getElementByXPath(expression);
        DomElement form = ((HtmlUnitElementImpl) element).getHtmlElement();
        InputElementFactory factory = InputElementFactory.instance;
        AttributesImpl attributes = new AttributesImpl();
        attributes.addAttribute("", "", "type", "", "submit");
        HtmlElement submit = factory.createElement(form.getPage(), "input", attributes);
        form.appendChild(submit);
    }
}