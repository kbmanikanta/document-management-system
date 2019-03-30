package rs.ac.bg.fon.silab.mock;

import javax.validation.Path;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class PropertyPathMock implements Path {

    private String propertyName;

    public PropertyPathMock(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    public Iterator<Node> iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer<? super Node> consumer) {

    }

    @Override
    public Spliterator<Node> spliterator() {
        return null;
    }

    @Override
    public String toString() {
        return propertyName;
    }

}
