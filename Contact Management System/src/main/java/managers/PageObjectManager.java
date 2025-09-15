package managers;

import org.openqa.selenium.WebDriver;
import pages.*;
import pages.DeleteContactPage;

public class PageObjectManager {
    private LoginPage loginPage;
    private ContactListPage contactListPage;
    private AddContactPage addContactPage;
    private RegistrationPage registrationPage;
    private EditContactPage editContactPage;
    private DeleteContactPage deleteContactPage;
    private SessionPage sessionPage;
    private UIPage uiPage;
    private ContactFormPage contactFormPage;

    public PageObjectManager(WebDriver driver) {
    }

    public LoginPage getLoginPage() {
        if (loginPage == null) loginPage = new LoginPage();
        return loginPage;
    }

    public ContactListPage getContactListPage() {
        if (contactListPage == null) contactListPage = new ContactListPage();
        return contactListPage;
    }

    public AddContactPage getAddContactPage() {
        if (addContactPage == null) addContactPage = new AddContactPage();
        return addContactPage;
    }

    public RegistrationPage getRegistrationPage() {
        if (registrationPage == null) {
            registrationPage = new RegistrationPage();
        }
        return registrationPage;
    }

    public EditContactPage getEditContactPage() {
        if (editContactPage == null) {editContactPage = new EditContactPage();
        }
        return editContactPage;
    }
    public DeleteContactPage getDeleteContactPage() {
        if (deleteContactPage == null) {
            deleteContactPage = new DeleteContactPage();
        }
        return deleteContactPage;
    }
    public SessionPage getSessionPage() {
        if (sessionPage == null) {
            sessionPage = new SessionPage();
        }
        return sessionPage;
    }
    public UIPage getUIPage() {
        if (uiPage == null) {
            uiPage = new UIPage();
        }
        return uiPage;
    }
    public ContactFormPage getContactFormPage() {
        if (contactFormPage == null) { contactFormPage = new ContactFormPage();
        }
        return contactFormPage;
    }
}
