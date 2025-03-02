package cungthinh.productservice.utils;

import com.github.slugify.Slugify;
import org.springframework.stereotype.Component;

@Component
public class SlugGenerator {
    private static final Slugify slugify = new Slugify();

    public static String toSlug(String input) {
        return slugify.slugify(input);
    }
}
