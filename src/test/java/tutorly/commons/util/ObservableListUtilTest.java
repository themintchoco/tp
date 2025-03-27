package tutorly.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;

public class ObservableListUtilTest {

    @Test
    public void arrayList() {
        ObservableList<Integer> observableList = ObservableListUtil.arrayList();
        assertTrue(observableList instanceof ObservableList<Integer>);
    }

    @Test
    public void unmodifiableList() {
        ObservableList<Integer> observableList = ObservableListUtil.arrayList();
        ObservableList<Integer> unmodifiableList = ObservableListUtil.unmodifiableList(observableList);
        assertTrue(unmodifiableList instanceof ObservableList<Integer>);
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableList.remove(0));
    }

    @Test
    public void filteredList() {
        ObservableList<Integer> sourceList = ObservableListUtil.arrayList();
        ObservableList<Integer> matchingList = ObservableListUtil.arrayList();

        Predicate<Integer> predicate = i -> matchingList.contains(i);

        ObservableList<Integer> filteredList = ObservableListUtil.filteredList(
                sourceList, predicate, List.of(matchingList));

        assertTrue(filteredList instanceof ObservableList<?>);
        assertEquals(filteredList, sourceList.stream().filter(predicate).toList());

        sourceList.add(1);
        assertEquals(filteredList, sourceList.stream().filter(predicate).toList());

        matchingList.add(1);
        assertEquals(filteredList, sourceList.stream().filter(predicate).toList());

        matchingList.add(2);
        assertEquals(filteredList, sourceList.stream().filter(predicate).toList());
    }

}
