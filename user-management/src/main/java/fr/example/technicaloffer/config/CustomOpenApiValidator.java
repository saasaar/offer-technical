package fr.example.technicaloffer.config;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.example.technicaloffer.api.CountryEnum;
import fr.example.technicaloffer.api.GenderEnum;
import fr.example.technicaloffer.constraint.annotation.DateAge;
import fr.example.technicaloffer.constraint.annotation.Country;
import fr.example.technicaloffer.constraint.annotation.Gender;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.oas.models.media.Schema;

@Configuration
public class CustomOpenApiValidator extends ModelResolver {

	private final List<Package> allowedPackages = List.of(DateAge.class.getPackage());

	public CustomOpenApiValidator(ObjectMapper mapper) {
		super(mapper);
	}

	@Override
	protected void applyBeanValidatorAnnotations(Schema property, Annotation[] annotations, Schema parent) {
		super.applyBeanValidatorAnnotations(property, annotations, parent);
		if (annotations != null) {
			Arrays.asList(annotations).stream().filter(a -> allowedPackages.contains(a.annotationType().getPackage()))
					.forEach(e -> {
						var extensions = property.getExtensions();
						Class<? extends Annotation> annotationType = e.annotationType();
						String extensionKey = "x-" + annotationType.getSimpleName();
						if (!(extensions != null && extensions.containsKey(extensionKey))) {
							Object value = describeAnnotation(e, annotationType);
							property.addExtension(extensionKey, value);
						}
					});
		}

	}

	private Object describeAnnotation(Annotation annotation, Class<? extends Annotation> annotationType) {
		Object ret = true;
		if (annotationType.equals(Country.class)) {
			ret = CountryEnum.values();
		}
		if (annotationType.equals(Gender.class)) {
			ret = GenderEnum.values();
		}
		if (annotationType.equals(DateAge.class)) {
			DateAge age = (DateAge) annotation;
			return new StringBuilder("Age must be between ").append(age.minAge()).append(" and ").append(age.maxAge())
					.toString();
		}
		return ret;
	}

}
