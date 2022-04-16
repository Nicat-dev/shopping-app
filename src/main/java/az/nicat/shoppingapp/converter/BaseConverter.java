package az.nicat.shoppingapp.converter;

import java.util.List;

public abstract class BaseConverter<E,D> {
    public abstract D convert(E from);
    public abstract List<D> convert(List<E> from);
}
