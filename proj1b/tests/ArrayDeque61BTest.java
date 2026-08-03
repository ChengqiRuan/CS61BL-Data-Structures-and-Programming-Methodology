import com.google.common.truth.Subject;
import deque.ArrayDeque61B;

import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.NoSuchElementException;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class ArrayDeque61BTest {

     @Test
     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
     void noNonTrivialFields() {
         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                 .toList();

         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
     }
    @Test
    void add_first_from_empty() {
        ArrayDeque61B<Integer> dq = new ArrayDeque61B<>();
        dq.addFirst(5);
        assertThat(dq.size()).isEqualTo(1);
        assertThat(dq.toList()).isEqualTo(List.of(5));
    }

    @Test
    void add_last_from_empty() {
        ArrayDeque61B<Integer> dq = new ArrayDeque61B<>();
        dq.addLast(9);
        assertThat(dq.size()).isEqualTo(1);
        assertThat(dq.toList()).isEqualTo(List.of(9));
    }

    @Test
    void add_first_nonempty() {
        ArrayDeque61B<Integer> dq = new ArrayDeque61B<>();
        dq.addFirst(2);
        dq.addFirst(1);
        assertThat(dq.size()).isEqualTo(2);
        assertThat(dq.toList()).isEqualTo(List.of(1, 2));
    }

    @Test
    void add_last_nonempty() {
        ArrayDeque61B<Integer> dq = new ArrayDeque61B<>();
        dq.addLast(10);
        dq.addLast(20);
        assertThat(dq.size()).isEqualTo(2);
        assertThat(dq.toList()).isEqualTo(List.of(10, 20));
    }

    @Test
    void add_first_trigger_resize() {
        ArrayDeque61B<Integer> dq = new ArrayDeque61B<>();
        for (int i = 1; i <= 8; i++) {
            dq.addLast(i);
        }
        dq.addFirst(0);
        assertThat(dq.size()).isEqualTo(9);
        assertThat(dq.toList()).isEqualTo(List.of(0,1,2,3,4,5,6,7,8));
    }

    @Test
    void add_last_trigger_resize() {
        ArrayDeque61B<Integer> dq = new ArrayDeque61B<>();
        for (int i = 1; i <= 8; i++) {
            dq.addLast(i);
        }
        dq.addLast(9);
        assertThat(dq.size()).isEqualTo(9);
        assertThat(dq.toList()).isEqualTo(List.of(1,2,3,4,5,6,7,8,9));
    }

    @Test
    void add_first_after_remove_to_empty() {
        ArrayDeque61B<Integer> dq = new ArrayDeque61B<>();
        dq.addLast(3);
        dq.addLast(7);
        dq.removeFirst();
        dq.removeFirst();
        assertThat(dq.size()).isEqualTo(0);
        dq.addFirst(11);
        assertThat(dq.toList()).isEqualTo(List.of(11));
    }

    @Test
    void add_last_after_remove_to_empty() {
        ArrayDeque61B<Integer> dq = new ArrayDeque61B<>();
        dq.addFirst(4);
        dq.addFirst(2);
        dq.removeLast();
        dq.removeLast();
        assertThat(dq.size()).isEqualTo(0);
        dq.addLast(19);
        assertThat(dq.toList()).isEqualTo(List.of(19));
    }
    @Test
    void remove_first() {
        ArrayDeque61B<Integer> dq = new ArrayDeque61B<>();
        dq.addLast(1);
        dq.addLast(2);
        int removed = dq.removeFirst();
        assertThat(removed).isEqualTo(1);
        assertThat(dq.toList()).isEqualTo(List.of(2));
    }

    @Test
    void remove_last() {
        ArrayDeque61B<Integer> dq = new ArrayDeque61B<>();
        dq.addLast(1);
        dq.addLast(2);
        int removed = dq.removeLast();
        assertThat(removed).isEqualTo(2);
        assertThat(dq.toList()).isEqualTo(List.of(1));
    }

    @Test
    void remove_first_to_empty() {
        ArrayDeque61B<Integer> dq = new ArrayDeque61B<>();
        dq.addLast(1);
        dq.addLast(2);
        dq.removeFirst();
        dq.removeFirst();
        assertThat(dq.size()).isEqualTo(0);
        assertThat(dq.toList()).isEqualTo(List.of());
    }

    @Test
    void remove_last_to_empty() {
        ArrayDeque61B<Integer> dq = new ArrayDeque61B<>();
        dq.addLast(1);
        dq.addLast(2);
        dq.removeLast();
        dq.removeLast();
        assertThat(dq.size()).isEqualTo(0);
        assertThat(dq.toList()).isEqualTo(List.of());
    }

    @Test
    void remove_first_to_one() {
        ArrayDeque61B<Integer> dq = new ArrayDeque61B<>();
        dq.addLast(1);
        dq.addLast(2);
        dq.addLast(3);
        dq.removeFirst();
        dq.removeFirst();
        assertThat(dq.size()).isEqualTo(1);
        assertThat(dq.toList()).isEqualTo(List.of(3));
    }

    @Test
    void remove_last_to_one() {
        ArrayDeque61B<Integer> dq = new ArrayDeque61B<>();
        dq.addLast(1);
        dq.addLast(2);
        dq.addLast(3);
        dq.removeLast();
        dq.removeLast();
        assertThat(dq.size()).isEqualTo(1);
        assertThat(dq.toList()).isEqualTo(List.of(1));
    }

    @Test
    void remove_first_trigger_resize() {
        ArrayDeque61B<Integer> dq = new ArrayDeque61B<>();
        for (int i = 1; i <= 16; i++) {
            dq.addLast(i);
        }
        for (int i = 0; i <= 11; i++) {
            dq.removeFirst();
        }
        assertThat(dq.toList()).isEqualTo(List.of(13,14,15,16));
    }

    @Test
    void remove_last_trigger_resize() {
        ArrayDeque61B<Integer> dq = new ArrayDeque61B<>();
        for (int i = 1; i <= 16; i++) {
            dq.addLast(i);
        }
        for (int i = 0; i <= 11; i++) {
            dq.removeLast();
        }
        assertThat(dq.toList()).isEqualTo(List.of(1,2,3,4));
    }

    @Test
    void get_valid() {
        ArrayDeque61B<Integer> dq = new ArrayDeque61B<>();
        dq.addLast(10);
        dq.addLast(20);
        assertThat(dq.get(1)).isEqualTo(20);
    }

    @Test
    void get_oob_large() {
        ArrayDeque61B<Integer> dq = new ArrayDeque61B<>();
        dq.addLast(5);
        assertThat(dq.get(99)).isNull();
    }

    @Test
    void get_oob_neg() {
        ArrayDeque61B<Integer> dq = new ArrayDeque61B<>();
        dq.addLast(5);
        assertThat(dq.get(-1)).isNull();
    }

    @Test
    void size() {
        ArrayDeque61B<Integer> dq = new ArrayDeque61B<>();
        dq.addLast(7);
        assertThat(dq.size()).isEqualTo(1);
    }

    @Test
    void size_after_remove_to_empty() {
        ArrayDeque61B<Integer> dq = new ArrayDeque61B<>();
        dq.addLast(2);
        dq.removeFirst();
        assertThat(dq.size()).isEqualTo(0);
    }

    @Test
    void size_after_remove_from_empty() {
        ArrayDeque61B<Integer> dq = new ArrayDeque61B<>();
        Integer res = dq.removeFirst();
        assertEquals(null, res);
        assertEquals(0, dq.size());
    }

    @Test
    void is_empty_true() {
        ArrayDeque61B<Integer> dq = new ArrayDeque61B<>();
        assertThat(dq.isEmpty()).isTrue();
    }

    @Test
    void is_empty_false() {
        ArrayDeque61B<Integer> dq = new ArrayDeque61B<>();
        dq.addLast(7);
        assertThat(dq.isEmpty()).isFalse();
    }

    @Test
    void to_list_empty() {
        ArrayDeque61B<Integer> dq = new ArrayDeque61B<>();
        assertThat(dq.toList()).isEqualTo(List.of());
    }

    @Test
    void to_list_nonempty() {
        ArrayDeque61B<Integer> dq = new ArrayDeque61B<>();
        dq.addLast(2);
        dq.addLast(5);
        assertThat(dq.toList()).isEqualTo(List.of(2, 5));
    }

    @Test
    void resize_up_and_resize_down() {
        ArrayDeque61B<Integer> dq = new ArrayDeque61B<>();
        for (int i = 1; i <= 8; i++) {
            dq.addLast(i);
        }
        dq.addLast(9);
        assertThat(dq.toList()).isEqualTo(List.of(1,2,3,4,5,6,7,8,9));
        for (int i = 0; i < 7; i++) {
            dq.removeLast();
        }
        assertThat(dq.toList()).isEqualTo(List.of(1,2));
    }


}
