import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDeque61BTest {

    @Test
     /** In this test, we have three different assert statements that verify that addFirst works correctly. */
     public void addFirstTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addFirst("back"); // after this call we expect: ["back"]
         assertThat(lld1.toList()).containsExactly("back").inOrder();

         lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
         assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

         lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
     }

     @Test
     /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
     public void addLastTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addLast("front"); // after this call we expect: ["front"]
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
     }



     @Test
     /** This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

         assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
     }

    // Below, you'll write your own tests for LinkedListDeque61B.
    @Test
    public void toListTest(){
         LinkedListDeque61B<String> deque = new LinkedListDeque61B<>();
         assertWithMessage("It should be [] when there is nothing in list.").
                 that(deque.toList()).
                 isEqualTo(List.of());
         deque.addFirst("wanna");
         assertWithMessage("It should be ['wanna'] when there is just 'wanna' in list.").
                that(deque.toList()).
                isEqualTo(List.of("wanna"));
         deque.addFirst("I");
         assertWithMessage("It should be ['I','wanna'] after adding I firstly.").
                that(deque.toList()).
                isEqualTo(List.of("I","wanna"));
         deque.addLast("gohome");
         assertWithMessage("It should be ['I','wanna','gohome'] after adding gohome last.").
                that(deque.toList()).
                isEqualTo(List.of("I","wanna","gohome"));
     }
    @Test
    public void isEmptyTest(){
        LinkedListDeque61B<String> deque = new LinkedListDeque61B<>();
        assertWithMessage("It should be true when there is nothing in list.").
                that(deque.isEmpty()).
                isTrue();
        deque.addFirst("wanna");
        assertWithMessage("It should be false when there is just 'wanna' in list.").
                that(deque.isEmpty()).
                isFalse();
        deque.addFirst("I");
        assertWithMessage("It should be false after adding I firstly.").
                that(deque.isEmpty()).
                isFalse();
        deque.addLast("gohome");
        assertWithMessage("It should be false after adding gohome last.").
                that(deque.isEmpty()).
                isFalse();
    }
    @Test
    public void sizeTest(){
        LinkedListDeque61B<String> deque = new LinkedListDeque61B<>();

        // flag="size"
        assertWithMessage("It should be 0 when there is nothing in list.")
                .that(deque.size())
                .isEqualTo(0);

        deque.addFirst("wanna");
        assertWithMessage("It should be 1 when there is just 'wanna' in list.")
                .that(deque.size())
                .isEqualTo(1);

        deque.addFirst("I");
        assertWithMessage("It should be 2 after adding I firstly.")
                .that(deque.size())
                .isEqualTo(2);

        deque.addLast("gohome");
        assertWithMessage("It should be 3 after adding gohome last.")
                .that(deque.size())
                .isEqualTo(3);

        // size_after_remove_to_empty
        while (deque.size() > 0) {
            deque.removeFirst();
        }
        assertWithMessage("Size should be 0 after removing all elements to empty.")
                .that(deque.size())
                .isEqualTo(0);

        // size_after_remove_from_empty
        deque.removeFirst();
        deque.removeLast();
        assertWithMessage("Size still remains 0 after remove from empty deque.")
                .that(deque.size())
                .isEqualTo(0);
    }
    @Test
    public void testGet() {
        LinkedListDeque61B<String> deque = new LinkedListDeque61B<>();
        deque.addFirst("wanna");
        deque.addFirst("I");
        deque.addLast("gohome");
        assertWithMessage("Index 0 should return first element 'I'")
                .that(deque.get(0))
                .isEqualTo("I");
        assertWithMessage("Index 1 should return middle element 'wanna'")
                .that(deque.get(1))
                .isEqualTo("wanna");
        assertWithMessage("Index 2 should return last element 'gohome'")
                .that(deque.get(2))
                .isEqualTo("gohome");
        assertWithMessage("Negative index should return null")
                .that(deque.get(-1))
                .isEqualTo(null);
        assertWithMessage("Index out of bound return null")
                .that(deque.get(3))
                .isEqualTo(null);
        LinkedListDeque61B<String> emptyDeque = new LinkedListDeque61B<>();
        assertWithMessage("Get from empty deque should return null")
                .that(emptyDeque.get(0))
                .isEqualTo(null);
    }
    @Test
    public void testGetRicursive() {
        LinkedListDeque61B<String> deque = new LinkedListDeque61B<>();
        deque.addFirst("wanna");
        deque.addFirst("I");
        deque.addLast("gohome");
        assertWithMessage("Index 0 should return first element 'I'")
                .that(deque.getRecursive(0))
                .isEqualTo("I");
        assertWithMessage("Index 1 should return middle element 'wanna'")
                .that(deque.getRecursive(1))
                .isEqualTo("wanna");
        assertWithMessage("Index 2 should return last element 'gohome'")
                .that(deque.getRecursive(2))
                .isEqualTo("gohome");
        assertWithMessage("Negative index should return null")
                .that(deque.getRecursive(-1))
                .isEqualTo(null);
        assertWithMessage("Index out of bound should return null")
                .that(deque.getRecursive(3))
                .isEqualTo(null);
        LinkedListDeque61B<String> emptyDeque = new LinkedListDeque61B<>();
        assertWithMessage("Get from empty deque should return null")
                .that(emptyDeque.getRecursive(0))
                .isEqualTo(null);
    }
    @Test
    public void testRemoveFirst() {
        LinkedListDeque61B<String> deque = new LinkedListDeque61B<>();
        deque.addLast("I");
        deque.addLast("wanna");
        deque.addLast("gohome");
        deque.addLast("now");
        assertWithMessage("Initial deque list should be [I, wanna, gohome, now].")
                .that(deque.toList())
                .isEqualTo(List.of("I", "wanna", "gohome", "now"));

        // flag: remove_first
        String firstOut = deque.removeFirst();
        assertWithMessage("removeFirst should return the first element 'I'.")
                .that(firstOut)
                .isEqualTo("I");
        assertWithMessage("After removeFirst once, list should be [wanna, gohome, now].")
                .that(deque.toList())
                .isEqualTo(List.of("wanna", "gohome", "now"));
        deque.removeFirst();
        assertWithMessage("After removeFirst twice, list should be [gohome, now].")
                .that(deque.toList())
                .isEqualTo(List.of("gohome", "now"));

        // remove_first_to_one
        String secondLast = deque.removeFirst();
        assertWithMessage("removeFirst on second-to-last element returns 'gohome'.")
                .that(secondLast)
                .isEqualTo("gohome");
        assertWithMessage("After remove to one element, list should be [now].")
                .that(deque.toList())
                .isEqualTo(List.of("now"));

        // flag: remove_first_to_empty
        String lastOut = deque.removeFirst();
        assertWithMessage("removeFirst on the last element returns 'now'.")
                .that(lastOut)
                .isEqualTo("now");
        assertWithMessage("Deque should be empty after removing last element via removeFirst.")
                .that(deque.toList())
                .isEqualTo(List.of());
    }
    @Test
    public void testRemoveLast() {
        LinkedListDeque61B<String> deque = new LinkedListDeque61B<>();
        deque.addLast("I");
        deque.addLast("wanna");
        deque.addLast("gohome");
        deque.addLast("now");
        assertWithMessage("Initial deque list should be [I, wanna, gohome, now].")
                .that(deque.toList())
                .isEqualTo(List.of("I", "wanna", "gohome", "now"));

        // flag: remove_last
        String lastOut = deque.removeLast();
        assertWithMessage("removeLast should return the last element 'now'.")
                .that(lastOut)
                .isEqualTo("now");
        assertWithMessage("After removeLast once, list should be [I, wanna, gohome].")
                .that(deque.toList())
                .isEqualTo(List.of("I", "wanna", "gohome"));

        // flag: remove_last_to_one
        deque.removeLast();
        assertWithMessage("After removeLast twice, list should be [I, wanna].")
                .that(deque.toList())
                .isEqualTo(List.of("I", "wanna"));
        String secondLast = deque.removeLast();
        assertWithMessage("removeLast on second-to-last element returns 'wanna'.")
                .that(secondLast)
                .isEqualTo("wanna");
        assertWithMessage("After remove to one element, list should be [I].")
                .that(deque.toList())
                .isEqualTo(List.of("I"));

        // flag: remove_last_to_empty
        String finalOut = deque.removeLast();
        assertWithMessage("removeLast on the last element returns 'I'.")
                .that(finalOut)
                .isEqualTo("I");
        assertWithMessage("Deque should be empty after removing last element via removeLast.")
                .that(deque.toList())
                .isEqualTo(List.of());
    }
    @Test
    public void testAddFirstCoverage() {
        LinkedListDeque61B<String> deque = new LinkedListDeque61B<>();

        // add_first_from_empty
        deque.addFirst("I");
        assertWithMessage("It should add I to empty deque using addFirst.")
                .that(deque.toList())
                .isEqualTo(List.of("I"));

        // add_first_nonempty
        deque.addFirst("wanna");
        assertWithMessage("It should add wanna to front when deque is not empty.")
                .that(deque.toList())
                .isEqualTo(List.of("wanna", "I"));
        while(deque.size() > 0) {
            deque.removeFirst();
        }

        // add_first_after_remove_to_empty
        deque.addFirst("gohome");
        assertWithMessage("addFirst should work after removing all elements to empty.")
                .that(deque.toList())
                .isEqualTo(List.of("gohome"));
    }
    @Test
    public void testAddLastCoverage() {
        LinkedListDeque61B<String> deque = new LinkedListDeque61B<>();

        // add_last_from_empty
        deque.addLast("I");
        assertWithMessage("It should add I to empty deque using addLast.")
                .that(deque.toList())
                .isEqualTo(List.of("I"));

        // add_last_nonempty
        deque.addLast("wanna");
        assertWithMessage("It should add wanna to back when deque is not empty.")
                .that(deque.toList())
                .isEqualTo(List.of("I", "wanna"));
        while(deque.size() > 0) {
            deque.removeFirst();
        }
        //add_last_after_remove_to_empty
        deque.addLast("gohome");
        assertWithMessage("addLast should work after removing all elements to empty.")
                .that(deque.toList())
                .isEqualTo(List.of("gohome"));
    }
}