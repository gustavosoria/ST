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
 * File: EditGrade.php 
 * Variable: ‘delete’, ‘selectclass, ‘assignment’
 * Vulnerability: variable are passed within hidden fields.
 */

public class EditGrade {
    
    private WebTester tester;
    
    public EditGrade() {
    }
    
    @Before
    public void setUp() {
        tester = new WebTester();
        tester.setBaseUrl("http://localhost:8888/schoolmate/");
    }
    
     @Test
    //variable delete
    public void testVulnerability76a() {
        tester.beginAt("index.php");
        tester.assertMatch("Today's Message");
        tester.setTextField("username", "teacher1");
        tester.setTextField("password", "nonlaso");
        tester.submit();
        tester.assertMatch("topolino topolino's Classes");
        tester.clickLinkWithExactText("class1");
        tester.clickLinkWithText("Grades");
        tester.assertTextPresent("Grades ");
        tester.setWorkingForm("grades");
        tester.checkCheckbox("delete[]", "2");
        tester.setTextField("delete[]","2 '> <a href=http://www.google.com>Malicious link</a> <br / '");
        tester.clickButtonWithText("Edit");
        tester.assertLinkNotPresentWithText("Malicious link");
    }

    @Test
    //variable selectclass
    public void testVulnerability76b() {
        tester.beginAt("index.php");
        tester.assertMatch("Today's Message");
        tester.setTextField("username", "teacher1");
        tester.setTextField("password", "nonlaso");
        tester.submit();
        tester.assertMatch("topolino topolino's Classes");
        tester.clickLinkWithExactText("class1");
        tester.clickLinkWithText("Grades");
        tester.assertTextPresent("Grades ");
        tester.setWorkingForm("grades");
        tester.checkCheckbox("delete[]", "2");
        tester.clickButtonWithText("Edit");
        tester.setWorkingForm("editgrade");
        tester.setHiddenField("selectclass", "6 '> <a href=http://www.ibm.com>Malicious link</a> <br / '");
        addSubmitButton("//form[@name='editgrade']");
        tester.submit();
        tester.assertLinkNotPresentWithText("Malicious link");
    }
    
    @Test
    //variable assignment
    public void testVulnerability76c() {
        tester.beginAt("index.php");
        tester.assertMatch("Today's Message");
        tester.setTextField("username", "teacher1");
        tester.setTextField("password", "nonlaso");
        tester.submit();
        tester.assertMatch("topolino topolino's Classes");
        tester.clickLinkWithExactText("class1");
        tester.clickLinkWithText("Grades");
        tester.assertTextPresent("Grades ");
        tester.setWorkingForm("grades");
        tester.checkCheckbox("delete[]", "2");
        tester.clickButtonWithText("Edit");
        tester.setWorkingForm("editgrade");
        tester.setHiddenField("assignment","6 '> <a href=http://www.ibm.com>Malicious link</a> <br / '");
        addSubmitButton("//form[@name='editgrade']");
        tester.submit();
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
