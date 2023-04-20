package ru.netology.tests;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.models.User;
import ru.netology.pages.CardApplicationForm;
import ru.netology.utils.DataGenerator;

import static java.lang.String.format;
import static ru.netology.enums.ValidationMessages.*;

public class CardDeliveryTest extends BaseTest {

    private CardApplicationForm cardApplicationForm;
    private User user;

    @BeforeEach
    public void openMainPage() {
        cardApplicationForm = new CardApplicationForm();
        cardApplicationForm.open();
        user = DataGenerator.createRandomUser();
    }

    @Test
    public void shouldSuccessfullyRescheduleMeeting() {
        String deliveryDate = DataGenerator.incrementCurrentDateByDays(3);
        String secondDeliveryDate = DataGenerator.incrementCurrentDateByDays(4);
        String fullName = format("%s %s", user.getFirstname(), user.getLastname());

        cardApplicationForm
                .setCity(DataGenerator.getRandomCity())
                .setCurrentDay()
                .setName(fullName)
                .setPhone(user.getPhoneNumber())
                .clickAgreementCheckbox()
                .clickSubmitButton()
                .successNotificationShouldBeVisible(deliveryDate)
                .setSpecificDate(secondDeliveryDate)
                .clickSubmitButton()
                .replanPopoverShouldBeVisible()
                .clickReplanButton()
                .successNotificationShouldBeVisible(secondDeliveryDate);
    }

    @Test
    public void shouldValidateAgreementCheckbox() {
        String fullName = format("%s %s", user.getFirstname(), user.getLastname());

        cardApplicationForm
                .setCity(DataGenerator.getRandomCity())
                .setCurrentDay()
                .setName(fullName)
                .setPhone(user.getPhoneNumber())
                .clickSubmitButton()
                .agreementCheckboxShouldHaveValidationError();
    }


    @Test
    public void shouldValidateCity() {
        String invalidCity = "W" + DataGenerator.getRandomCity().substring(1);
        String fullName = format("%s %s", user.getFirstname(), user.getLastname());

        cardApplicationForm
                .setCity(invalidCity)
                .setCurrentDay()
                .setName(fullName)
                .setPhone(user.getPhoneNumber())
                .clickAgreementCheckbox()
                .clickSubmitButton()
                .cityValidationShouldHaveText(WRONG_DELIVERY_CITY.getValue());
    }

    @Test
    public void shouldValidateName() {
        String invalidName = "W" + user.getFirstname();

        cardApplicationForm
                .setCity(DataGenerator.getRandomCity())
                .setCurrentDay()
                .setName(invalidName)
                .setPhone(user.getPhoneNumber())
                .clickAgreementCheckbox()
                .clickSubmitButton()
                .nameValidationShouldHaveText(WRONG_NAME_OR_LASTNAME.getValue());
    }

    @Test
    public void shouldValidateDate() {
        String fullName = format("%s %s", user.getFirstname(), user.getLastname());

        cardApplicationForm
                .setCity(DataGenerator.getRandomCity())
                .setEmptyDate()
                .setName(fullName)
                .setPhone(user.getPhoneNumber())
                .clickAgreementCheckbox()
                .clickSubmitButton()
                .dateValidationShouldHaveText(INCORRECT_DATE.getValue());

    }

    @AfterEach
    public void tearDown() {
        Selenide.closeWebDriver();
    }

}
