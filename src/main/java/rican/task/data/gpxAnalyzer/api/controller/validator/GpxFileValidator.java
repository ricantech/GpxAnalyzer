package rican.task.data.gpxAnalyzer.api.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class GpxFileValidator implements ConstraintValidator<GpxFile, MultipartFile> {

    private static final String GPX_FILE_EXTENSION = ".gpx";

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        if (value.getOriginalFilename() != null) {
            return value.getOriginalFilename().endsWith(GPX_FILE_EXTENSION);
        }
        return false;
    }
}