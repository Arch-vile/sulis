package moonillusions.grails.testing.matchers;

import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;

public class FieldErrors extends BaseMatcher<DefaultGrailsDomainClass> {

    private Map<String, String> errors;

    public FieldErrors(Map<String, String> errorMap) {
        this.errors = errorMap;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Expected a domain object with errors: "
                + errors);
    }

    @Override
    public void describeMismatch(final Object item,
            final Description description) {
        description.appendText("errors were")
                .appendValue(createErrorsMap(item));
    }

    @Override
    public boolean matches(Object domain) {
        Map<String, String> errorMap = createErrorsMap(domain);
        return equalTo(this.errors).matches(errorMap);
    }

    private Map<String, String> createErrorsMap(Object domain) {
        // InvokerHelper.invokeMethod(domain, "save", null);
        BeanPropertyBindingResult validationErrors = (BeanPropertyBindingResult) InvokerHelper
                .invokeMethod(domain, "getErrors", null);
        List<FieldError> errors = validationErrors.getFieldErrors();

        Map<String, String> errorMap = new HashMap<String, String>();
        for (FieldError err : errors) {
            errorMap.put(err.getField(),
                    validationErrors.getFieldError(err.getField()).getCode());
        }
        return errorMap;
    }

    @Factory
    public static Matcher<DefaultGrailsDomainClass> fieldErrors(
            Map<String, String> errorMap) {
        return new FieldErrors(errorMap);
    }

    @Factory
    public static Matcher<DefaultGrailsDomainClass> noFieldErrors() {
        return new FieldErrors(new HashMap<String, String>());
    }

}