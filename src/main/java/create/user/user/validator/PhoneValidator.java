package create.user.user.validator;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import create.user.user.constraint.Phone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class PhoneValidator implements ConstraintValidator<Phone, String> {

  private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }

    try {
      PhoneNumber phone = phoneNumberUtil.parse(value, "");
      boolean isPossiblePhone = phoneNumberUtil.isPossibleNumber(phone);
      if (!isPossiblePhone) {
        return false;
      }

      String format = phoneNumberUtil.format(phone, PhoneNumberFormat.E164);
      return value.equals(format);
    } catch (Exception e) {
      return false;
    }
  }

}
