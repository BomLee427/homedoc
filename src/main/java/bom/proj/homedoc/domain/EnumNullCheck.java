package bom.proj.homedoc.domain;

import static java.lang.Enum.valueOf;

public interface EnumNullCheck {
    static <E extends Enum<E>> E valueOfOrNull(Class<E> e, String name) {
        try {
            return valueOf(e, name);
        } catch (IllegalArgumentException|NullPointerException exception) {
            return null;
        }
    }
}
