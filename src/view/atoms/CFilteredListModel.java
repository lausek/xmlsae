package view.atoms;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javax.swing.DefaultListModel;

/**
 * Custom ListModel with filter logic. You can call method filter to modify the
 * displayed list items. All visible list items can be obtained by
 * getDisplayedList while getList also returns every hidden element.
 * 
 * @author lausek
 *
 * @param <T>
 *            A list item of type CListItem
 */
@SuppressWarnings("serial")
public class CFilteredListModel<T extends CListItem> extends DefaultListModel<T> {

	private List<T> hiddenBuffer;

	public void filter(Predicate<T> filter) {
		List<T> nextHiddenBuffer = new ArrayList<>();

		// Remove invalid elements first
		// Run in reversed order because normal one would lead to inconsistencies
		for (int i = getSize() - 1; i >= 0; i--) {
			T item = get(i);
			if (!filter.test(item)) {
				nextHiddenBuffer.add(item);
				removeElementAt(i);
			}
		}

		// Check if old buffer now contains fitting entries
		if (hiddenBuffer != null) {
			for (int i = hiddenBuffer.size() - 1; i >= 0; i--) {
				T item = hiddenBuffer.get(i);
				// If previously hidden item now matches, remove it from hiddenBuffer
				if (filter.test(item)) {
					addElement(item);
					hiddenBuffer.remove(i);
				}
			}
		} else {
			hiddenBuffer = new ArrayList<>();
		}

		hiddenBuffer.addAll(nextHiddenBuffer);
	}

	/**
	 * This list contains all items that are currently displayed.
	 * 
	 * @return
	 */
	public List<T> getDisplayedList() {
		List<T> buffer = new ArrayList<>();
		for (int i = 0; i < getSize(); i++) {
			buffer.add(get(i));
		}
		return buffer;
	}

	/**
	 * This returns every element that could be in the list even if it is currently
	 * not displayed.
	 * 
	 * @return
	 */
	public List<T> getList() {
		List<T> buffer = getDisplayedList();
		if (hiddenBuffer != null) {
			buffer.addAll(hiddenBuffer);
		}
		return buffer;
	}

}