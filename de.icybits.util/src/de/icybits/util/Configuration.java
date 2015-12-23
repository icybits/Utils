package de.icybits.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;

/**
 * This configuration object can contain every object to configure something.
 * This objects should be immutable or the receivers should be able to monitor
 * internal changes. To persist the configuration the objects should be
 * serializable. Non serializable objects will be lost on serialization.
 *
 * @author Iceac Sarutobi
 *
 */
public class Configuration implements Serializable {

	private static final long serialVersionUID = -6249612829236510405L;

	// ********************* //
	// * static part start * //
	// ********************* //

	public static void put(Configuration configuration, String key, Object object) {
		if (configuration != null)
			configuration.put(key, object);
	}

	public static void put(Configuration configuration, Enum<?> enumKey, Object object) {
		if (configuration != null)
			configuration.put(enumKey, object);
	}

	public static Object get(Configuration configuration, String key) {
		if (configuration == null)
			return null;
		return configuration.get(key);
	}

	public static Object get(Configuration configuration, Enum<?> enumKey) {
		if (configuration == null)
			return null;
		return configuration.get(enumKey);
	}

	public static Object get(Configuration configuration, String key, Object defaultObject) {
		if (configuration == null)
			return defaultObject;
		return configuration.get(key, defaultObject);
	}

	public static Object get(Configuration configuration, Enum<?> enumKey, Object defaultObject) {
		if (configuration == null)
			return defaultObject;
		return configuration.get(enumKey, defaultObject);
	}

	public static String getString(Configuration configuration, String key) {
		if (configuration == null)
			return null;
		return configuration.getString(key);
	}

	public static String getString(Configuration configuration, Enum<?> enumKey) {
		if (configuration == null)
			return null;
		return configuration.getString(enumKey);
	}

	public static String getString(Configuration configuration, String key, String defaultObject) {
		if (configuration == null)
			return defaultObject;
		return configuration.getString(key, defaultObject);
	}

	public static String getString(Configuration configuration, Enum<?> enumKey, String defaultObject) {
		if (configuration == null)
			return defaultObject;
		return configuration.getString(enumKey, defaultObject);
	}

	public static Integer getInteger(Configuration configuration, String key) {
		if (configuration == null)
			return null;
		return configuration.getInteger(key);
	}

	public static Integer getInteger(Configuration configuration, Enum<?> enumKey) {
		if (configuration == null)
			return null;
		return configuration.getInteger(enumKey);
	}

	public static Integer getInteger(Configuration configuration, String key, Integer defaultObject) {
		if (configuration == null)
			return defaultObject;
		return configuration.getInteger(key, defaultObject);
	}

	public static Integer getInteger(Configuration configuration, Enum<?> enumKey, Integer defaultObject) {
		if (configuration == null)
			return defaultObject;
		return configuration.getInteger(enumKey, defaultObject);
	}

	public static Boolean getBoolean(Configuration configuration, String key) {
		if (configuration == null)
			return null;
		return configuration.getBoolean(key);
	}

	public static Boolean getBoolean(Configuration configuration, Enum<?> enumKey) {
		if (configuration == null)
			return null;
		return configuration.getBoolean(enumKey);
	}

	public static Boolean getBoolean(Configuration configuration, String key, Boolean defaultObject) {
		if (configuration == null)
			return defaultObject;
		return configuration.getBoolean(key, defaultObject);
	}

	public static Boolean getBoolean(Configuration configuration, Enum<?> enumKey, Boolean defaultObject) {
		if (configuration == null)
			return defaultObject;
		return configuration.getBoolean(enumKey, defaultObject);
	}

	public static Double getDouble(Configuration configuration, String key) {
		if (configuration == null)
			return null;
		return configuration.getDouble(key);
	}

	public static Double getDouble(Configuration configuration, Enum<?> enumKey) {
		if (configuration == null)
			return null;
		return configuration.getDouble(enumKey);
	}

	public static Double getDouble(Configuration configuration, String key, Double defaultObject) {
		if (configuration == null)
			return defaultObject;
		return configuration.getDouble(key, defaultObject);
	}

	public static Double getDouble(Configuration configuration, Enum<?> enumKey, Double defaultObject) {
		if (configuration == null)
			return defaultObject;
		return configuration.getDouble(enumKey, defaultObject);
	}

	public static <T> T getObject(Configuration configuration, String key, Class<T> clazz) {
		if (configuration == null)
			return null;
		return configuration.getObject(key, clazz);
	}

	public static <T> T getObject(Configuration configuration, Enum<?> enumKey, Class<T> clazz) {
		if (configuration == null)
			return null;
		return configuration.getObject(enumKey, clazz);
	}

	public static <T> T getObject(Configuration configuration, String key, Class<T> clazz, T defaultObject) {
		if (configuration == null)
			return defaultObject;
		return configuration.getObject(key, clazz, defaultObject);
	}

	public static <T> T getObject(Configuration configuration, Enum<?> enumKey, Class<T> clazz, T defaultObject) {
		if (configuration == null)
			return defaultObject;
		return configuration.getObject(enumKey, clazz, defaultObject);
	}

	// ******************* //
	// * static part end * //
	// ******************* //

	// ******************** //
	// * field part start * //
	// ******************** //

	private transient ObservableMap<String, Object> map = FXCollections.observableHashMap();

	private transient Configuration defaultConfiguration = null;

	// ****************** //
	// * field part end * //
	// ****************** //

	// ********************* //
	// * method part start * //
	// ********************* //

	public ObservableMap<String, Object> getMap() {
		return this.map;
	}

	public void setDefaultConfiguration(Configuration defaultConfiguration) {
		this.defaultConfiguration = defaultConfiguration;
	}

	public Configuration getDefaultConfiguration() {
		return this.defaultConfiguration;
	}

	/**
	 * Create a clone of this. Be aware that the map items are not cloned so
	 * changes within items applies to both original and clone.
	 */
	@Override
	public Configuration clone() {
		Configuration configuration = new Configuration();
		configuration.map.putAll(this.map);
		configuration.setDefaultConfiguration(getDefaultConfiguration());
		return configuration;
	}

	public void addListener(MapChangeListener<String, Object> listener) {
		this.map.addListener(listener);
		if (this.defaultConfiguration != null)
			this.defaultConfiguration.addListener(listener);
	}

	public void removeListener(MapChangeListener<String, Object> listener) {
		this.map.removeListener(listener);
		if (this.defaultConfiguration != null)
			this.defaultConfiguration.removeListener(listener);
	}

	public Set<String> keys() {
		HashSet<String> set = new HashSet<>(this.map.keySet());
		if (this.defaultConfiguration != null)
			set.addAll(this.defaultConfiguration.keys());
		return set;
	}

	public Object get(String key) {
		if (this.defaultConfiguration != null && !this.map.containsKey(key))
			return this.defaultConfiguration.get(key);
		return this.map.get(key);
	}

	public Object get(Enum<?> enumKey) {
		return get(getEnumString(enumKey));
	}

	public Object get(String key, Object defaultObject) {
		Object result = get(key);
		if (result == null)
			return defaultObject;
		return result;
	}

	public Object get(Enum<?> enumKey, Object defaultObject) {
		return get(getEnumString(enumKey), defaultObject);
	}

	public void put(String key, Object value) {
		this.map.put(key, value);
	}

	public void put(Enum<?> enumKey, Object value) {
		put(getEnumString(enumKey), value);
	}

	public boolean contains(String key) {
		boolean result = false;
		if (this.defaultConfiguration != null)
			result = result || this.defaultConfiguration.contains(key);
		result = result || this.map.containsKey(key);
		return result;
	}

	public boolean contains(Enum<?> enumKey) {
		return contains(getEnumString(enumKey));
	}

	public String getString(String key) {
		if (get(key) instanceof String)
			return (String) get(key);
		return null;
	}

	public String getString(Enum<?> enumKey) {
		return getString(getEnumString(enumKey));
	}

	public String getString(String key, String defaultString) {
		String result = getString(key);
		if (result == null)
			return defaultString;
		return result;
	}

	public String getString(Enum<?> enumKey, String defaultString) {
		String result = getString(getEnumString(enumKey), defaultString);
		if (result == null)
			return defaultString;
		return result;
	}

	public Integer getInteger(String key) {
		if (get(key) instanceof Integer)
			return (Integer) get(key);
		return null;
	}

	public Integer getInteger(Enum<?> enumKey) {
		return getInteger(getEnumString(enumKey));
	}

	public Integer getInteger(String key, Integer defaultInteger) {
		Integer result = getInteger(key);
		if (result == null)
			return defaultInteger;
		return result;
	}

	public Integer getInteger(Enum<?> enumKey, Integer defaultInteger) {
		return getInteger(getEnumString(enumKey), defaultInteger);
	}

	public Boolean getBoolean(String key) {
		if (get(key) instanceof Boolean)
			return (Boolean) get(key);
		return null;
	}

	public Boolean getBoolean(Enum<?> enumKey) {
		return getBoolean(getEnumString(enumKey));
	}

	public Boolean getBoolean(String key, Boolean defaultBoolean) {
		Boolean result = getBoolean(key);
		if (result == null)
			return defaultBoolean;
		return result;
	}

	public Boolean getBoolean(Enum<?> enumKey, Boolean defaultBoolean) {
		return getBoolean(getEnumString(enumKey), defaultBoolean);
	}

	public Double getDouble(String key) {
		if (get(key) instanceof Double)
			return (Double) get(key);
		return null;
	}

	public Double getDouble(Enum<?> enumKey) {
		return getDouble(getEnumString(enumKey));
	}

	public Double getDouble(String key, Double defaultDouble) {
		Double result = getDouble(key);
		if (result == null)
			return defaultDouble;
		return result;
	}

	public Double getDouble(Enum<?> enumKey, Double defaultDouble) {
		return getDouble(getEnumString(enumKey), defaultDouble);
	}

	public <T> T getObject(String key, Class<T> clazz) {
		Object object = get(key);
		if (clazz.isInstance(object))
			return clazz.cast(object);
		return null;
	}

	public <T> T getObject(Enum<?> enumKey, Class<T> clazz) {
		return getObject(getEnumString(enumKey), clazz);
	}

	public <T> T getObject(String key, Class<T> clazz, T defaultObject) {
		T result = getObject(key, clazz);
		if (result == null)
			return defaultObject;
		return result;
	}

	public <T> T getObject(Enum<?> enumKey, Class<T> clazz, T defaultObject) {
		return getObject(getEnumString(enumKey), clazz, defaultObject);
	}

	/**
	 * We forget the entries which are not {@link Serializable}, assuming that if
	 * it is not {@link Serializable} it must not persist.
	 */
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		HashMap<String, Serializable> sMap = new HashMap<String, Serializable>();
		for (String key : this.map.keySet()) {
			Object value = this.map.get(key);
			if (value instanceof Serializable) {
				sMap.put(key, (Serializable) value);
			}
		}
		out.writeObject(sMap);
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		this.map = FXCollections.observableHashMap();
		HashMap<?, ?> sMap = (HashMap<?, ?>) in.readObject();
		for (Object key : sMap.keySet()) {
			this.map.put((String) key, sMap.get(key));
		}
	}

	private String getEnumString(Enum<?> enumKey) {
		if(enumKey != null)
			return enumKey.toString();
		return null;
	}
	
	// ******************* //
	// * method part end * //
	// ******************* //
}
