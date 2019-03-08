package de.icybits.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * @author Iceac Sarutobi
 *
 */
public class MultidimensionalMatrix<K, V> {

	private final int dimensionCount;

	private ArrayList<ArrayList<K>> dimensionKeys = new ArrayList<ArrayList<K>>();

	private HashMap<K, Object> matrix = new HashMap<K, Object>();

	private Comparator<K> comparator = null;

	/**
	 * @param dimensionCount
	 *            the number of dimensionCount in this
	 *            {@link MultidimensionalMatrix}.
	 */
	public MultidimensionalMatrix(int dimensionCount) {
		this(dimensionCount, null);
	}

	/**
	 * @param dimensionCount
	 *            the number of dimensionCount in this
	 *            {@link MultidimensionalMatrix}.
	 * @param comparator
	 *          the {@link Comparator} to sort the keys.
	 */
	public MultidimensionalMatrix(int dimensionCount, Comparator<K> comparator) {
		super();
		if (dimensionCount < 1)
			throw new IllegalArgumentException("The minimum value of dimensionCount is 1, input = " + dimensionCount);
		this.dimensionCount = dimensionCount;
		for (int i = 0; i < dimensionCount; i++) {
			dimensionKeys.add(new ArrayList<K>());
		}
		this.comparator = comparator;
	}

	/**
	 * Set the {@link Comparator} to sort the keys.
	 * 
	 * @param comperator
	 *          the {@link Comparator} to sort the keys.
	 */
	public void setComparator(Comparator<K> comperator) {
		this.comparator = comperator;
		for (List<K> keys : dimensionKeys) {
			keys.sort(comperator);
		}
	}

	/**
	 * Returns how much dimensions this {@link MultidimensionalMatrix} has.
	 * 
	 * @return The dimension count.
	 */
	public final int getDimensionCount() {
		return this.dimensionCount;
	}

	/**
	 * Returns whether the dimension keys are sorted.
	 * 
	 * @return <code>true</code> if the dimension keys are sorted.
	 */
	public boolean isDimensionKeysSorted() {
		return comparator != null;
	}

	/**
	 * Returns how much keys the given dimension has.
	 * 
	 * @param dimension
	 *            the 0 based dimension index of the
	 *            {@link MultidimensionalMatrix}.
	 * @return The key count of the dimension keys.
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if the given dimension is not -1 < dimension <
	 *             {@link #getDimensionCount()}.
	 */
	public int getDimensionKeyCount(int dimension) {
		if (!(-1 < dimension && dimension < getDimensionCount()))
			throw new IndexOutOfBoundsException("dimension is out of range, dimension = " + dimension + ", dimension range = 0 - " + (getDimensionCount() - 1));
		ArrayList<K> keyList = dimensionKeys.get(dimension);
		return keyList.size();
	}

	/**
	 * Returns the key of the dimension at index position.
	 * 
	 * @param dimension
	 *            the 0 based dimension index of the
	 *            {@link MultidimensionalMatrix}.
	 * @param index
	 *            the 0 based key index in the dimension.
	 * @return The key related with the index.
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if the given dimension is not -1 < dimension <
	 *             {@link #getDimensionCount()} or the given index is not -1 <
	 *             index < {@link #getDimensionKeyCount(int)}.
	 */
	public K getDimensionKey(int dimension, int index) {
		if (!(-1 < dimension && dimension < getDimensionCount()))
			throw new IndexOutOfBoundsException("dimension is out of range, dimension = " + dimension + ", dimension range = 0 - " + (getDimensionCount() - 1));
		if (!(-1 < index && index < getDimensionKeyCount(dimension)))
			throw new IndexOutOfBoundsException("index is out of range, index = " + index + ", dimension key range = 0 - " + (getDimensionKeyCount(dimension) - 1));
		ArrayList<K> keyList = dimensionKeys.get(dimension);
		return keyList.get(index);
	}

	/**
	 * Returns the index position of the key in the dimension or -1 if the given
	 * key do not exists int this {@link MultidimensionalMatrix}.
	 * 
	 * @param dimension
	 *            the 0 based dimension index of the
	 *            {@link MultidimensionalMatrix}.
	 * @param key
	 *            a key in the dimension.
	 * @return The index of the given key.
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if the given dimension is not -1 < dimension <
	 *             {@link #getDimensionCount()}.
	 */
	public int getDimensionIndex(int dimension, K key) {
		if (!(-1 < dimension && dimension < getDimensionCount()))
			throw new IndexOutOfBoundsException("dimension is out of range, dimension = " + dimension + ", dimension range = 0 - " + (getDimensionCount() - 1));
		if (key == null)
			throw new NullPointerException("Key must not be null!");
		ArrayList<K> keyList = dimensionKeys.get(dimension);
		return keyList.indexOf(key);
	}

	/**
	 * Returns all keys within the given dimension.
	 * 
	 * @param dimension
	 *            the 0 based dimension index of the
	 *            {@link MultidimensionalMatrix}.
	 * @return A list with all keys in the given dimension.
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if the given dimension is not -1 < dimension <
	 *             {@link #getDimensionCount()}.
	 */
	public List<K> getDimensionKeys(int dimension) {
		if (!(-1 < dimension && dimension < getDimensionCount()))
			throw new IndexOutOfBoundsException("dimension is out of range, dimension = " + dimension + ", dimension range = 0 - " + (getDimensionCount() - 1));
		return Collections.unmodifiableList(dimensionKeys.get(dimension));
	}

	/**
	 * Returns the value at the given index position. Or <code>null</code> if
	 * there is no value at the given position.
	 * 
	 * @param indexes
	 *            the indexes for every dimension in this
	 *            {@link MultidimensionalMatrix}.
	 * @return the value at the given index position.
	 * 
	 * @throws NullPointerException
	 *             if the index array is null
	 * @throws IndexOutOfBoundsException
	 *             if the count of index positions is not equals dimension count
	 *             or one of the index positions is not -1 < index position <
	 *             {@link #getDimensionKeyCount(int)}.
	 */
	public V getValueByIndex(int... indexes) {
		if (indexes == null)
			throw new NullPointerException("indexes must not be null!");
		if (indexes.length != getDimensionCount())
			throw new IndexOutOfBoundsException("indexes length = " + indexes.length + " must be equals dimension count = " + getDimensionCount());
		Object[] result = new Object[indexes.length];
		for (int i = 0; i < indexes.length; i++) {
			result[i] = getDimensionKey(i, indexes[i]);
		}
		return getValueInternal(result);
	}

	/**
	 * Returns the value at the given index position. Or <code>null</code> if
	 * there is no value at the given position.
	 * 
	 * @param indexes
	 *            the indexes for every dimension in this
	 *            {@link MultidimensionalMatrix}.
	 * @return the value at the given index position.
	 * 
	 * @throws NullPointerException
	 *             if the index array is null
	 * @throws IndexOutOfBoundsException
	 *             if the count of index positions is not equals dimension
	 *             count.
	 */
	@SuppressWarnings("unchecked")
	public V getValueByKey(K... keys) {
		if (keys == null)
			throw new NullPointerException("keys must not be null!");
		if (keys.length != getDimensionCount())
			throw new IndexOutOfBoundsException("keys length = " + keys.length + " must be equals dimension count = " + getDimensionCount());
		return getValueInternal((Object[]) keys);
	}

	@SuppressWarnings("unchecked")
	private V getValueInternal(Object... keys) {
		Object result = matrix.get(keys[0]);
		for (int i = 1; i < keys.length; i++) {
			if (result == null)
				return null;
			HashMap<?, ?> map = (HashMap<?, ?>) result;
			result = map.get(keys[i]);
		}
		return (V) result;
	}

	@SuppressWarnings("unchecked")
	public void setValue(V value, K... keys) {
		if (keys == null)
			throw new NullPointerException("keys must not be null!");
		if (keys.length != getDimensionCount())
			throw new IllegalArgumentException("keys length = " + keys.length + " must be equals dimension count = " + getDimensionCount());
		HashMap<K, Object> resultMap = matrix;
		for (int dimensionIndex = 0; dimensionIndex < keys.length - 1; dimensionIndex++) {
			if (resultMap == null)
				break;
			HashMap<K, Object> tmpMap = (HashMap<K, Object>) resultMap.get(keys[dimensionIndex]);
			if (value != null) {
				if (tmpMap == null) {
					tmpMap = new HashMap<>();
					resultMap.put(keys[dimensionIndex], tmpMap);
				}
				addDimensionKey(dimensionIndex, keys[dimensionIndex]);
			}
			resultMap = tmpMap;
		}
		if (resultMap != null) {
			int lastDimensionIndex = keys.length - 1;
			resultMap.put(keys[lastDimensionIndex], value);
			addDimensionKey(lastDimensionIndex, keys[lastDimensionIndex]);
		}
	}

	private void addDimensionKey(int dimension, K key) {
		int dimIndex = getDimensionIndex(dimension, key);
		if (dimIndex < 0) {
			if (comparator != null) {
				ArrayList<K> keyList = dimensionKeys.get(dimension);
				keyList.add(key);
				keyList.sort(comparator);
			} else {
				ArrayList<K> keyList = dimensionKeys.get(dimension);
				keyList.add(key);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void removeValue(K... keys) {
		setValue(null, keys);
	}

	public void clear() {
		matrix.clear();
		for (ArrayList<K> keyList : dimensionKeys) {
			keyList.clear();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Entry<K, V>> getCombinations() {
		List<Entry<K, V>> list = new ArrayList<>();
		for (K key : getDimensionKeys(0))
			fillCombinations(1, list, key);
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void fillCombinations(int dimension, List<Entry<K, V>> list, K... keys) {
		if (dimension < getDimensionCount()) {
			for (K key : getDimensionKeys(dimension)) {
				K[] subKeys = Arrays.copyOf(keys, keys.length + 1);
				subKeys[subKeys.length - 1] = key;
				fillCombinations(dimension + 1, list, subKeys);
			}
		} else {
			list.add(new Entry(getValueByKey(keys), keys));
		}
	}

	public static class Entry<K, V> {
		private List<K> keys;
		private V value;

		@SuppressWarnings("unchecked")
		public Entry(V value, K... keys) {
			this.keys = new ArrayList<K>();
			for (K key : keys) {
				this.keys.add(key);
			}
			this.value = value;
		}

		public List<K> getKeys() {
			return this.keys;
		}

		public V getValue() {
			return this.value;
		}

		@Override
		public String toString() {
			return keys + " " + value;
		}
	}
}