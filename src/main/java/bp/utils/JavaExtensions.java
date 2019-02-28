package bp.utils;

import static java.util.Objects.isNull;

/**
 * More util methods for util gods!
 */
public interface JavaExtensions {

    static <T> boolean isEmpty(T val) {
        return isNull(val) || val.toString().trim().isEmpty();
    }

    static <T> boolean isNotEmpty(T val) {
        return !isEmpty(val);
    }
}
