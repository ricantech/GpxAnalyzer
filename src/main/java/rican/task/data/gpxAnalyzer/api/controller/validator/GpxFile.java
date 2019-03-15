package rican.task.data.gpxAnalyzer.api.controller.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target({METHOD, PARAMETER})
@Constraint(validatedBy = GpxFileValidator.class)
public @interface GpxFile {
    String message() default "Uploaded file is not GPX file (.gpx extension expected)";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
