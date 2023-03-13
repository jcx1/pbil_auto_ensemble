package weka.classifiers.meta.pbil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import weka.classifiers.meta.pbil.Interval.Type;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IntervalTest {

	@Test
	@SuppressWarnings("rawtypes")
	public void test000Interval() {
		Interval interval;

		interval = new Interval(Type.Boolean, Arrays.asList(true));
		assertTrue(interval.isSet);
		assertTrue(interval.type == Type.Boolean);
		assertEquals(Boolean.TRUE, interval.minValue);
		assertEquals(Boolean.TRUE, interval.maxValue);
		assertNull(interval.decimalPlaces);
		assertEquals(interval.values, Arrays.asList(true));

		interval = new Interval(Type.Boolean, Arrays.asList(false, true));
		assertTrue(interval.isSet);
		assertTrue(interval.type == Type.Boolean);
		assertEquals(false, interval.minValue);
		assertEquals(true, interval.maxValue);
		assertNull(interval.decimalPlaces);
		assertEquals(interval.values, Arrays.asList(false, true));

		interval = new Interval(Type.Boolean, Arrays.asList(true, false));
		assertTrue(interval.isSet);
		assertTrue(interval.type == Type.Boolean);
		assertEquals(false, interval.minValue);
		assertEquals(true, interval.maxValue);
		assertNull(interval.decimalPlaces);
		assertEquals(interval.values, Arrays.asList(true, false));

		interval = new Interval(Type.Integer, Arrays.asList(0, 1, 2, 10));
		assertTrue(interval.isSet);
		assertTrue(interval.type == Type.Integer);
		assertTrue(interval.minValue instanceof Integer);
		assertTrue(interval.maxValue instanceof Integer);
		assertEquals(0, interval.minValue);
		assertEquals(10, interval.maxValue);
		assertNull(interval.decimalPlaces);
		assertEquals(interval.values, Arrays.asList(0, 1, 2, 10));

		interval = new Interval(Type.Integer, Arrays.asList(10, 13, 2, 10));
		assertTrue(interval.isSet);
		assertTrue(interval.type == Type.Integer);
		assertTrue(interval.minValue instanceof Integer);
		assertTrue(interval.maxValue instanceof Integer);
		assertEquals(2, interval.minValue);
		assertEquals(13, interval.maxValue);
		assertNull(interval.decimalPlaces);
		assertEquals(interval.values, Arrays.asList(10, 13, 2, 10));

		interval = new Interval(Type.Integer, Arrays.asList(100));
		assertTrue(interval.isSet);
		assertTrue(interval.type == Type.Integer);
		assertTrue(interval.minValue instanceof Integer);
		assertTrue(interval.maxValue instanceof Integer);
		assertEquals(100, interval.minValue);
		assertEquals(100, interval.maxValue);
		assertNull(interval.decimalPlaces);
		assertEquals(interval.values, Arrays.asList(100));

		interval = new Interval(Type.Double, Arrays.asList(0.45, 1.2, 3.0, 4.567));
		assertTrue(interval.isSet);
		assertTrue(interval.type == Type.Double);
		assertTrue(interval.minValue instanceof Double);
		assertTrue(interval.maxValue instanceof Double);
		assertEquals(0.45, interval.minValue);
		assertEquals(4.567, interval.maxValue);
		assertNull(interval.decimalPlaces);
		assertEquals(interval.values, Arrays.asList(0.45, 1.2, 3.0, 4.567));

		interval = new Interval(Type.Double, Arrays.asList(0.450));
		assertTrue(interval.isSet);
		assertTrue(interval.type == Type.Double);
		assertTrue(interval.minValue instanceof Double);
		assertTrue(interval.maxValue instanceof Double);
		assertEquals(0.45, interval.minValue);
		assertEquals(0.450, interval.maxValue);
		assertNull(interval.decimalPlaces);
		assertEquals(interval.values, Arrays.asList(0.45));

		interval = new Interval(Type.Double, Arrays.asList(0.0, 1.0));
		assertTrue(interval.isSet);
		assertTrue(interval.type == Type.Double);
		assertTrue(interval.minValue instanceof Double);
		assertTrue(interval.maxValue instanceof Double);
		assertEquals(new Double(0), interval.minValue);
		assertEquals(new Double(1), interval.maxValue);
		assertNull(interval.decimalPlaces);
		assertEquals(Arrays.asList(0.0, 1.0), interval.values);

		interval = new Interval(Type.String, Arrays.asList("v1", "v2", "v5"));
		assertTrue(interval.isSet);
		assertTrue(interval.type == Type.String);
		assertNull(interval.decimalPlaces);
		assertEquals(interval.values, Arrays.asList("v1", "v2", "v5"));

		interval = new Interval(Type.String, Arrays.asList("v1"));
		assertTrue(interval.isSet);
		assertTrue(interval.type == Type.String);
		assertNull(interval.decimalPlaces);
		assertEquals(interval.values, Arrays.asList("v1"));

		try {
			new Interval(Type.Boolean, new ArrayList<Comparable>());
			fail("Expected assertion error.");
		} catch (AssertionError e) {}
	}

	@Test
	public void test001Interval() {
		Interval interval;

		interval = new Interval(Type.Boolean, false, true);
		assertFalse(interval.isSet);
		assertTrue(interval.type == Type.Boolean);
		assertEquals(Boolean.FALSE, interval.minValue);
		assertEquals(Boolean.TRUE, interval.maxValue);
		assertNull(interval.decimalPlaces);
		assertNull(interval.values);

		interval = new Interval(Type.Boolean, true, true);
		assertFalse(interval.isSet);
		assertTrue(interval.type == Type.Boolean);
		assertEquals(true, interval.minValue);
		assertEquals(Boolean.TRUE, interval.maxValue);
		assertNull(interval.decimalPlaces);
		assertNull(interval.values);

		interval = new Interval(Type.Integer, 0, 10);
		assertFalse(interval.isSet);
		assertTrue(interval.type == Type.Integer);
		assertTrue(interval.minValue instanceof Integer);
		assertTrue(interval.maxValue instanceof Integer);
		assertEquals(0, interval.minValue);
		assertEquals(10, interval.maxValue);
		assertNull(interval.decimalPlaces);
		assertNull(interval.values);

		interval = new Interval(Type.Integer, 10, 10);
		assertFalse(interval.isSet);
		assertTrue(interval.type == Type.Integer);
		assertTrue(interval.minValue instanceof Integer);
		assertTrue(interval.maxValue instanceof Integer);
		assertEquals(10, interval.minValue);
		assertEquals(10, interval.maxValue);
		assertNull(interval.decimalPlaces);
		assertNull(interval.values);

		interval = new Interval(Type.Double, 0.45, 4.567);
		assertFalse(interval.isSet);
		assertTrue(interval.type == Type.Double);
		assertTrue(interval.minValue instanceof Double);
		assertTrue(interval.maxValue instanceof Double);
		assertEquals(0.45, interval.minValue);
		assertEquals(4.567, interval.maxValue);
		assertEquals(new Integer(2), interval.decimalPlaces);
		assertNull(interval.values);

		interval = new Interval(Type.Double, 5.4504, 5.4504);
		assertFalse(interval.isSet);
		assertTrue(interval.type == Type.Double);
		assertTrue(interval.minValue instanceof Double);
		assertTrue(interval.maxValue instanceof Double);
		assertEquals(5.4504, interval.minValue);
		assertEquals(5.4504, interval.maxValue);
		assertEquals(new Integer(2), interval.decimalPlaces);
		assertNull(interval.values);

		interval = new Interval(Type.Double, 0.0, 1.0);
		assertFalse(interval.isSet);
		assertTrue(interval.type == Type.Double);
		assertTrue(interval.minValue instanceof Double);
		assertTrue(interval.maxValue instanceof Double);
		assertEquals(new Double(0), interval.minValue);
		assertEquals(new Double(1), interval.maxValue);
		assertEquals(new Integer(2), interval.decimalPlaces);
		assertNull(interval.values);

		try {
			new Interval(Type.String, "minValue", "maxValue");
			fail("Expected assertion error.");
		} catch (AssertionError e) {}

		try {
			new Interval(Type.Boolean, true, false);
			fail("Expected assertion error. True (1) is bigger than false (0).");
		} catch (AssertionError e) {}

		try {
			new Interval(Type.Integer, 10, 1);
			fail("Expected assertion error.");
		} catch (AssertionError e) {}

		try {
			new Interval(Type.Double, 0.46, 0.45);
			fail("Expected assertion error.");
		} catch (AssertionError e) {}
	}

	@Test
	public void test002Interval() {
		Interval interval;

		interval = new Interval(Type.Double, 0.45, 4.567, 3);
		assertFalse(interval.isSet);
		assertTrue(interval.type == Type.Double);
		assertEquals(0.45, interval.minValue);
		assertEquals(4.567, interval.maxValue);
		assertEquals(new Integer(3), interval.decimalPlaces);
		assertNull(interval.values);

		interval = new Interval(Type.Double, 0.45, 0.45, 0);
		assertFalse(interval.isSet);
		assertTrue(interval.type == Type.Double);
		assertEquals(0.45, interval.minValue);
		assertEquals(0.45, interval.maxValue);
		assertEquals(new Integer(0), interval.decimalPlaces);
		assertNull(interval.values);

		try {
			new Interval(Type.String, "minValue", "maxValue");
			fail("Expected assertion error.");
		} catch (AssertionError e) {}

		try {
			new Interval(Type.Boolean, false, true, 3);
			fail("Expected assertion error.");
		} catch (AssertionError e) {}

		try {
			new Interval(Type.Boolean, true, false, 3);
			fail("Expected assertion error.");
		} catch (AssertionError e) {}

		try {
			new Interval(Type.Integer, 0, 1, 50);
			fail("Expected assertion error.");
		} catch (AssertionError e) {}

		try {
			new Interval(Type.Integer, 10, 1, 43);
			fail("Expected assertion error.");
		} catch (AssertionError e) {}

		try {
			new Interval(Type.Double, 0.45, 0.45, -1);
			fail("Expected assertion error.");
		} catch (AssertionError e) {}
	}

	@Test
	public void test004HasValue() {

		Interval interval;

		interval = new Interval(Type.Boolean, Arrays.asList(false));
		assertTrue(interval.contains(false));
		assertTrue(interval.contains(Boolean.FALSE));
		assertFalse(interval.contains(true));
		assertFalse(interval.contains(Boolean.TRUE));

		interval = new Interval(Type.Boolean, true, true);
		assertTrue(interval.contains(true));
		assertTrue(interval.contains(Boolean.TRUE));
		assertFalse(interval.contains(false));
		assertFalse(interval.contains(Boolean.FALSE));

		interval = new Interval(Type.Boolean, false, true);
		assertTrue(interval.contains(true));
		assertTrue(interval.contains(false));

		interval = new Interval(Type.Integer, Arrays.asList(-1, 2, 3));
		assertFalse(interval.contains(-2));
		assertTrue(interval.contains(-1));
		assertFalse(interval.contains(0));
		assertFalse(interval.contains(1));
		assertTrue(interval.contains(2));
		assertTrue(interval.contains(3));
		assertFalse(interval.contains(4));

		interval = new Interval(Type.Integer, -1, 3);
		assertFalse(interval.contains(-2));
		assertTrue(interval.contains(-1));
		assertTrue(interval.contains(0));
		assertTrue(interval.contains(1));
		assertTrue(interval.contains(2));
		assertTrue(interval.contains(3));
		assertFalse(interval.contains(4));

		interval = new Interval(Type.Double, Arrays.asList(0.2, 0.5, 0.3));
		assertFalse(interval.contains(0));
		assertFalse(interval.contains(0.1));
		assertTrue(interval.contains(0.2));
		assertTrue(interval.contains(0.3));
		assertFalse(interval.contains(0.22));
		assertFalse(interval.contains(0.20002));
		assertTrue(interval.contains(0.3));
		assertTrue(interval.contains(0.5));
		assertFalse(interval.contains(1));

		interval = new Interval(Type.Double, 0.4, 1.5);
		assertTrue(interval.contains(0.4));
		assertTrue(interval.contains(1.5));
		assertTrue(interval.contains(0.4000000));
		assertTrue(interval.contains(0.4000001));
		assertTrue(interval.contains((double) 1));
		assertTrue(interval.contains(1.4999));
		assertTrue(interval.contains(1.500000));
		assertFalse(interval.contains(0.39999));
		assertFalse(interval.contains(0.39999));
		assertFalse(interval.contains(1.50000001));
		assertFalse(interval.contains((double) 0));
		assertFalse(interval.contains(0.0));
	}

	@Test
	public void test005Size() {
		Interval interval;

		interval = new Interval(Type.Boolean, Arrays.asList(false));
		assertEquals(1, interval.size());

		interval = new Interval(Type.Boolean, true, true);
		assertEquals(1, interval.size());

		interval = new Interval(Type.Boolean, false, true);
		assertEquals(2, interval.size());

		interval = new Interval(Type.Integer, Arrays.asList(-1, 2, 3));
		assertEquals(3, interval.size());

		interval = new Interval(Type.Integer, Arrays.asList(-1));
		assertEquals(1, interval.size());

		interval = new Interval(Type.Integer, -1, 3);
		assertEquals(5, interval.size());

		interval = new Interval(Type.Integer, -1, -1);
		assertEquals(1, interval.size());

		interval = new Interval(Type.Integer, 0, 10);
		assertEquals(11, interval.size());

		interval = new Interval(Type.Double, Arrays.asList(0.2, 0.5, 0.3));
		assertEquals(3, interval.size());

		interval = new Interval(Type.Double, Arrays.asList(0.2));
		assertEquals(1, interval.size());

		interval = new Interval(Type.Double, 0.0, 0.0);
		assertEquals(1, interval.size());

		interval = new Interval(Type.Double, 0.0, 0.0, 0);
		assertEquals(1, interval.size());

		interval = new Interval(Type.Double, 0.0, 0.0, 3);
		assertEquals(1, interval.size());

		interval = new Interval(Type.Double, 0.0, 0.0, 2);
		assertEquals(1, interval.size());

		interval = new Interval(Type.Double, 0.0, 0.0, 10);
		assertEquals(1, interval.size());

		interval = new Interval(Type.Double, 0.0, 1.0);
		assertEquals(101, interval.size());

		interval = new Interval(Type.Double, 0.0, 1.0, 3);
		assertEquals(1001, interval.size());

		interval = new Interval(Type.Double, 1.01, 1.01);
		assertEquals(1, interval.size());

		interval = new Interval(Type.Double, 0.5, 1.5);
		assertEquals(101, interval.size());

		interval = new Interval(Type.Double, 0.5, 1.5, 4);
		assertEquals(10001, interval.size());

		interval = new Interval(Type.Double, 0.0, 1.001);
		assertEquals(101, interval.size());

		interval = new Interval(Type.Double, 0.0, 1.001, 6);
		assertEquals(1001001, interval.size());

		interval = new Interval(Type.Double, 0.0, 1.01, 1);
		assertEquals(11, interval.size());

		interval = new Interval(Type.Double, 0.0, 1.01, 0);
		assertEquals(2, interval.size());

		interval = new Interval(Type.Double, 0.0, 1.01, 2);
		assertEquals(102, interval.size());

		interval = new Interval(Type.Double, 0.0, 1.01, 5);
		assertEquals(101001, interval.size());
	}

	@Test
	public void test006GetValue() {
		Interval interval;

		interval = new Interval(Type.Boolean, Arrays.asList(false));
		assertEquals(false, interval.getValue(0));
		assertEquals(interval.minValue, interval.getValue(0));
		assertEquals(interval.maxValue, interval.getValue(interval.size() - 1));

		try {
			interval.getValue(1);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(-1);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(interval.size());
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Boolean, true, true);
		assertEquals(true, interval.getValue(0));
		assertEquals(interval.minValue, interval.getValue(0));
		assertEquals(interval.maxValue, interval.getValue(interval.size() - 1));

		try {
			interval.getValue(1);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(-1);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(interval.size());
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Boolean, false, true);
		assertEquals(false, interval.getValue(0));
		assertEquals(true, interval.getValue(1));
		assertEquals(interval.minValue, interval.getValue(0));
		assertEquals(interval.maxValue, interval.getValue(interval.size() - 1));

		try {
			interval.getValue(2);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(-1);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(interval.size());
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Integer, Arrays.asList(-1, 2, 3));
		assertEquals(-1, interval.getValue(0));
		assertEquals(2, interval.getValue(1));
		assertEquals(3, interval.getValue(2));
		assertEquals(interval.minValue, interval.getValue(0));
		assertEquals(interval.maxValue, interval.getValue(interval.size() - 1));

		try {
			interval.getValue(3);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(-1);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(interval.size());
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Integer, Arrays.asList(-1));
		assertEquals(-1, interval.getValue(0));
		assertEquals(interval.minValue, interval.getValue(0));
		assertEquals(interval.maxValue, interval.getValue(interval.size() - 1));

		try {
			interval.getValue(1);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(-1);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(interval.size());
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Integer, -1, 3);
		assertEquals(-1, interval.getValue(0));
		assertEquals(0, interval.getValue(1));
		assertEquals(1, interval.getValue(2));
		assertEquals(2, interval.getValue(3));
		assertEquals(3, interval.getValue(4));
		assertEquals(interval.minValue, interval.getValue(0));
		assertEquals(interval.maxValue, interval.getValue(interval.size() - 1));

		try {
			interval.getValue(4);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(-1);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(interval.size());
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Integer, -1, -1);
		assertEquals(-1, interval.getValue(0));
		assertEquals(interval.minValue, interval.getValue(0));
		assertEquals(interval.maxValue, interval.getValue(interval.size() - 1));

		try {
			interval.getValue(1);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(-1);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(interval.size());
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Integer, 0, 10);
		assertEquals(0, interval.getValue(0));
		assertEquals(1, interval.getValue(1));
		assertEquals(7, interval.getValue(7));
		assertEquals(10, interval.getValue(10));
		assertEquals(interval.minValue, interval.getValue(0));
		assertEquals(interval.maxValue, interval.getValue(interval.size() - 1));

		try {
			interval.getValue(11);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(-1);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(interval.size());
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Double, Arrays.asList(0.2, 0.5, 0.3));
		assertEquals(0.2, interval.getValue(0));
		assertEquals(0.5, interval.getValue(1));
		assertEquals(0.3, interval.getValue(2));
		assertEquals(interval.minValue, interval.getValue(0));
		assertEquals(interval.maxValue, interval.getValue(1));

		try {
			interval.getValue(3);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(-1);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(interval.size());
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Double, Arrays.asList(0.2));
		assertEquals(0.2, interval.getValue(0));
		assertEquals(interval.minValue, interval.getValue(0));
		assertEquals(interval.maxValue, interval.getValue(interval.size() - 1));

		try {
			interval.getValue(1);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(-1);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(interval.size());
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Double, 0.0, 0.0);
		assertEquals(.0, interval.getValue(0));
		assertEquals(interval.minValue, interval.getValue(0));
		assertEquals(interval.maxValue, interval.getValue(interval.size() - 1));

		try {
			interval.getValue(1);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(-1);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(interval.size());
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Double, 0.0, 0.0, 0);
		assertEquals(0.0, interval.getValue(0));
		assertEquals(interval.minValue, interval.getValue(0));
		assertEquals(interval.maxValue, interval.getValue(interval.size() - 1));

		try {
			interval.getValue(1);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(-1);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(interval.size());
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Double, 0.0, 0.0, 3);
		assertEquals(0.0, interval.getValue(0));
		assertEquals(interval.minValue, interval.getValue(0));
		assertEquals(interval.maxValue, interval.getValue(interval.size() - 1));

		try {
			interval.getValue(1);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(-1);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(interval.size());
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Double, 0.0, 0.0, 2);
		assertEquals(0.0, interval.getValue(0));
		assertEquals(interval.minValue, interval.getValue(0));
		assertEquals(interval.maxValue, interval.getValue(interval.size() - 1));

		try {
			interval.getValue(1);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(-1);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(interval.size());
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Double, 0.0, 0.0, 10);
		assertEquals(0.0, interval.getValue(0));
		assertEquals(interval.minValue, interval.getValue(0));
		assertEquals(interval.maxValue, interval.getValue(interval.size() - 1));

		try {
			interval.getValue(1);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(-1);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(interval.size());
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Double, 0.0, 1.0);
		assertEquals(0.0, interval.getValue(0));
		assertEquals(0.01, interval.getValue(1));
		assertEquals(0.10, interval.getValue(10));
		assertEquals(0.11, interval.getValue(11));
		assertEquals(0.54, interval.getValue(54));
		assertEquals(0.99, interval.getValue(99));
		assertEquals(1.0, interval.getValue(100));
		assertEquals(interval.minValue, interval.getValue(0));
		assertEquals(interval.maxValue, interval.getValue(interval.size() - 1));

		try {
			interval.getValue(101);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(-1);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(interval.size());
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Double, 0.0, 1.0, 3);
		assertEquals(0.0, interval.getValue(0));
		assertEquals(0.001, interval.getValue(1));
		assertEquals(0.010, interval.getValue(10));
		assertEquals(0.011, interval.getValue(11));
		assertEquals(0.054, interval.getValue(54));
		assertEquals(0.099, interval.getValue(99));
		assertEquals(0.1, interval.getValue(100));
		assertEquals(0.105, interval.getValue(105));
		assertEquals(0.547, interval.getValue(547));
		assertEquals(0.999, interval.getValue(999));
		assertEquals(0.1, interval.getValue(100));
		assertEquals(0.11, interval.getValue(110));
		assertEquals(0.54, interval.getValue(540));
		assertEquals(1.0, interval.getValue(1000));
		assertEquals(interval.minValue, interval.getValue(0));
		assertEquals(interval.maxValue, interval.getValue(interval.size() - 1));

		try {
			interval.getValue(1001);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(-1);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(interval.size());
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Double, 1.01, 1.01);
		assertEquals(1.01, interval.getValue(0));
		assertEquals(interval.minValue, interval.getValue(0));
		assertEquals(interval.maxValue, interval.getValue(interval.size() - 1));

		try {
			interval.getValue(1);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(-1);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(interval.size());
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Double, 0.5, 1.5);
		assertEquals(0.5, interval.getValue(0));
		assertEquals(0.51, interval.getValue(1));
		assertEquals(1.5, interval.getValue(100));
		assertEquals(1.49, interval.getValue(99));
		assertEquals(interval.minValue, interval.getValue(0));
		assertEquals(interval.maxValue, interval.getValue(interval.size() - 1));

		try {
			interval.getValue(101);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(-1);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(interval.size());
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Double, 0.5, 1.5, 4);
		assertEquals(0.5, interval.getValue(0));
		assertEquals(0.5001, interval.getValue(1));
		assertEquals(0.51, interval.getValue(100));
		assertEquals(0.6, interval.getValue(1000));
		assertEquals(1.4999, interval.getValue(9999));
		assertEquals(1.5, interval.getValue(10000));
		assertEquals(interval.minValue, interval.getValue(0));
		assertEquals(interval.maxValue, interval.getValue(interval.size() - 1));

		try {
			interval.getValue(10001);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(-1);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(interval.size());
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Double, 0.0, 1.001);
		assertEquals(0.0, interval.getValue(0));
		assertEquals(0.01, interval.getValue(1));
		assertEquals(0.1, interval.getValue(10));
		assertEquals(0.99, interval.getValue(99));
		assertEquals(1.00, interval.getValue(100));
		assertEquals(interval.minValue, interval.getValue(0));
		assertEquals(1.00, interval.getValue(interval.size() - 1));

		try {
			interval.getValue(101);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(-1);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(interval.size());
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Double, 0.0, 1.001, 6);
		assertEquals(0.0, interval.getValue(0));
		assertEquals(0.000001, interval.getValue(1));
		assertEquals(0.000002, interval.getValue(2));
		assertEquals(1.000999, interval.getValue(1000999));
		assertEquals(1.001, interval.getValue(1001000));
		assertEquals(interval.minValue, interval.getValue(0));
		assertEquals(interval.maxValue, interval.getValue(interval.size() - 1));

		try {
			interval.getValue(1001001);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(-1);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(interval.size());
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Double, 0.0, 1.01, 1);
		assertEquals(0.0, interval.getValue(0));
		assertEquals(0.5, interval.getValue(5));
		assertEquals(1.0, interval.getValue(10));
		assertEquals(interval.minValue, interval.getValue(0));
		assertEquals(1.0, interval.getValue(interval.size() - 1));

		try {
			interval.getValue(11);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(-1);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(interval.size());
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Double, 0.0, 1.01, 0);
		assertEquals(0.0, interval.getValue(0));
		assertEquals(1.0, interval.getValue(1));
		assertEquals(interval.minValue, interval.getValue(0));
		assertEquals(1.0, interval.getValue(interval.size() - 1));

		try {
			interval.getValue(2);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(-1);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(interval.size());
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Double, 0.0, 1.01, 2);
		assertEquals(0.0, interval.getValue(0));
		assertEquals(0.54, interval.getValue(54));
		assertEquals(1.01, interval.getValue(101));
		assertEquals(interval.minValue, interval.getValue(0));
		assertEquals(interval.maxValue, interval.getValue(interval.size() - 1));

		try {
			interval.getValue(102);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(-1);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(interval.size());
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Double, 0.0, 1.01, 5);
		assertEquals(0.0, interval.getValue(0));
		assertEquals(0.00001, interval.getValue(1));
		assertEquals(1.01, interval.getValue(101000));
		assertEquals(interval.minValue, interval.getValue(0));
		assertEquals(interval.maxValue, interval.getValue(interval.size() - 1));

		try {
			interval.getValue(interval.size());
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(101001);
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getValue(-1);
			fail("Expected exception.");
		} catch (AssertionError e) {}
	}

	@Test
	public void test007GetIndex() throws ParseException {
		Interval interval;

		interval = new Interval(Type.Boolean, Arrays.asList(false));
		assertEquals(0, interval.getIndex("false"));
		assertEquals(0, interval.getIndex(interval.minValue.toString()));
		assertEquals(interval.size() - 1, interval.getIndex(interval.maxValue.toString()));

		try {
			interval.getIndex("true");
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Boolean, true, true);
		assertEquals(0, interval.getIndex("true"));
		assertEquals(0, interval.getIndex(interval.minValue.toString()));
		assertEquals(interval.size() - 1, interval.getIndex(interval.maxValue.toString()));

		try {
			interval.getIndex("false");
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Boolean, false, true);
		assertEquals(false, interval.getValue(0));
		assertEquals(true, interval.getValue(1));
		assertEquals(interval.minValue, interval.getValue(0));
		assertEquals(interval.maxValue, interval.getValue(interval.size() - 1));

		assertEquals(0, interval.getIndex("false"));
		assertEquals(1, interval.getIndex("true"));
		assertEquals(0, interval.getIndex(interval.minValue.toString()));
		assertEquals(interval.size() - 1, interval.getIndex(interval.maxValue.toString()));

		interval = new Interval(Type.Integer, Arrays.asList(-1, 2, 3));
		assertEquals(0, interval.getIndex("-1"));
		assertEquals(1, interval.getIndex("2"));
		assertEquals(2, interval.getIndex("3"));
		assertEquals(0, interval.getIndex(interval.minValue.toString()));
		assertEquals(interval.size() - 1, interval.getIndex(interval.maxValue.toString()));

		try {
			interval.getIndex("0");
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getIndex("4");
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Integer, Arrays.asList(-1));
		assertEquals(0, interval.getIndex("-1"));
		assertEquals(0, interval.getIndex(interval.minValue.toString()));
		assertEquals(interval.size() - 1, interval.getIndex(interval.maxValue.toString()));

		try {
			interval.getIndex("0");
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getIndex("-2");
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Integer, -1, 3);
		assertEquals(0, interval.getIndex("-1"));
		assertEquals(1, interval.getIndex("0"));
		assertEquals(2, interval.getIndex("1"));
		assertEquals(3, interval.getIndex("2"));
		assertEquals(4, interval.getIndex("3"));
		assertEquals(0, interval.getIndex(interval.minValue.toString()));
		assertEquals(interval.size() - 1, interval.getIndex(interval.maxValue.toString()));

		try {
			interval.getIndex("-2");
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getIndex("4");
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Integer, -1, -1);
		assertEquals(0, interval.getIndex("-1"));
		assertEquals(0, interval.getIndex(interval.minValue.toString()));
		assertEquals(interval.size() - 1, interval.getIndex(interval.maxValue.toString()));

		try {
			interval.getIndex("-2");
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getIndex("0");
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Integer, 0, 10);
		assertEquals(0, interval.getIndex("0"));
		assertEquals(1, interval.getIndex("1"));
		assertEquals(7, interval.getIndex("7"));
		assertEquals(10, interval.getIndex("10"));
		assertEquals(0, interval.getIndex(interval.minValue.toString()));
		assertEquals(interval.size() - 1, interval.getIndex(interval.maxValue.toString()));

		try {
			interval.getIndex("-1");
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getIndex("11");
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Double, Arrays.asList(0.2, 0.5, 0.3));
		assertEquals(0, interval.getIndex("0.2"));
		assertEquals(1, interval.getIndex("0.5"));
		assertEquals(2, interval.getIndex("0.3"));
		assertEquals(0, interval.getIndex(interval.minValue.toString()));
		assertEquals(interval.size() - 1, interval.getIndex("0.3"));

		try {
			interval.getIndex("0.4");
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getIndex("0.1");
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Double, Arrays.asList(0.2));
		assertEquals(0, interval.getIndex("0.2"));
		assertEquals(0, interval.getIndex(interval.minValue.toString()));
		assertEquals(interval.size() - 1, interval.getIndex(interval.maxValue.toString()));

		try {
			interval.getIndex("0.1");
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getIndex("0.21");
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Double, 0.0, 0.0);
		assertEquals(0, interval.getIndex("0.0"));
		assertEquals(0, interval.getIndex(interval.minValue.toString()));
		assertEquals(interval.size() - 1, interval.getIndex(interval.maxValue.toString()));

		try {
			interval.getIndex("0.1");
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getIndex("1.0");
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Double, 0.0, 0.0, 0);
		assertEquals(0, interval.getIndex("0.0"));
		assertEquals(0, interval.getIndex(interval.minValue.toString()));
		assertEquals(interval.size() - 1, interval.getIndex(interval.maxValue.toString()));

		try {
			interval.getIndex("0.00001");
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getIndex("1.000000");
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Double, 0.0, 0.0, 3);
		assertEquals(0, interval.getIndex("0.000"));
		assertEquals(0, interval.getIndex(interval.minValue.toString()));
		assertEquals(interval.size() - 1, interval.getIndex(interval.maxValue.toString()));

		try {
			interval.getIndex("0.0001");
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getIndex("10.0");
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Double, 0.0, 0.0, 2);
		assertEquals(0, interval.getIndex("0.00"));
		assertEquals(0, interval.getIndex(interval.minValue.toString()));
		assertEquals(interval.size() - 1, interval.getIndex(interval.maxValue.toString()));

		try {
			interval.getIndex("0.001");
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getIndex("0.1");
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Double, 0.0, 0.0, 10);
		assertEquals(0, interval.getIndex("0.0000"));
		assertEquals(0, interval.getIndex(interval.minValue.toString()));
		assertEquals(interval.size() - 1, interval.getIndex(interval.maxValue.toString()));

		try {
			interval.getIndex("0.001");
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Double, 0.0, 1.0);
		assertEquals(0, interval.getIndex("0"));
		assertEquals(1, interval.getIndex("0.01"));
		assertEquals(10, interval.getIndex("0.1005"));
		assertEquals(10, interval.getIndex("0.10"));
		assertEquals(11, interval.getIndex("0.11"));
		assertEquals(54, interval.getIndex("0.54"));
		assertEquals(99, interval.getIndex("0.99"));
		assertEquals(99, interval.getIndex("0.999"));
		assertEquals(100, interval.getIndex("1.00"));
		assertEquals(0, interval.getIndex(interval.minValue.toString()));
		assertEquals(interval.size() - 1, interval.getIndex(interval.maxValue.toString()));

		try {
			interval.getIndex("-0.1");
			fail("Expected exception.");
		} catch (AssertionError e) {}

		try {
			interval.getIndex("1.5");
			fail("Expected exception.");
		} catch (AssertionError e) {}

		interval = new Interval(Type.Double, 0.0, 1.0, 3);
		assertEquals(0, interval.getIndex("0.0"));
		assertEquals(1, interval.getIndex("0.001"));
		assertEquals(10, interval.getIndex("0.01"));
		assertEquals(11, interval.getIndex("0.011"));
		assertEquals(54, interval.getIndex("0.054"));
		assertEquals(99, interval.getIndex("0.099"));
		assertEquals(100, interval.getIndex("0.1"));
		assertEquals(105, interval.getIndex("0.105"));
		assertEquals(547, interval.getIndex("0.547"));
		assertEquals(999, interval.getIndex("0.999"));
		assertEquals(100, interval.getIndex("0.1"));
		assertEquals(110, interval.getIndex("0.11"));
		assertEquals(540, interval.getIndex("0.54"));
		assertEquals(1000, interval.getIndex("1.0"));
		assertEquals(0, interval.getIndex(interval.minValue.toString()));
		assertEquals(interval.size() - 1, interval.getIndex(interval.maxValue.toString()));
	}

	@Test
	public void test008Convert() throws ParseException {
		Interval interval;

		interval = new Interval(Type.Boolean, false, true);
		assertTrue(interval.convert("true") instanceof Boolean);

		interval = new Interval(Type.Integer, 0, 100);
		assertTrue(interval.convert("10") instanceof Integer);

		interval = new Interval(Type.Double, 0.0, 100.01, 3);
		assertTrue(interval.convert("-0.10") instanceof Double);

		interval = new Interval(Type.String, Arrays.asList("v1", "v2"));
		assertTrue(interval.convert("v1") instanceof String);
	}

	@Test
	public void test009Parse() throws ParseException {
		Interval interval;

		interval = Interval.parse(Type.Boolean, "{true}");
		assertTrue(interval.isSet);
		assertTrue(interval.type == Type.Boolean);
		assertEquals(Boolean.TRUE, interval.minValue);
		assertEquals(Boolean.TRUE, interval.maxValue);
		assertNull(interval.decimalPlaces);
		assertEquals(interval.values, Arrays.asList(true));

		interval = Interval.parse(Type.Boolean, "{ false, true}");
		assertTrue(interval.isSet);
		assertTrue(interval.type == Type.Boolean);
		assertEquals(false, interval.minValue);
		assertEquals(true, interval.maxValue);
		assertNull(interval.decimalPlaces);
		assertEquals(interval.values, Arrays.asList(false, true));

		interval = Interval.parse(Type.Boolean, "{true , false}");
		assertTrue(interval.isSet);
		assertTrue(interval.type == Type.Boolean);
		assertEquals(false, interval.minValue);
		assertEquals(true, interval.maxValue);
		assertNull(interval.decimalPlaces);
		assertEquals(interval.values, Arrays.asList(true, false));

		interval = Interval.parse(Type.Integer, "{  0, 1 ,2    ,10  }");
		assertTrue(interval.isSet);
		assertTrue(interval.type == Type.Integer);
		assertTrue(interval.minValue instanceof Integer);
		assertTrue(interval.maxValue instanceof Integer);
		assertEquals(0, interval.minValue);
		assertEquals(10, interval.maxValue);
		assertNull(interval.decimalPlaces);
		assertEquals(interval.values, Arrays.asList(0, 1, 2, 10));

		interval = Interval.parse(Type.Integer, "{10,13,2,10}");
		assertTrue(interval.isSet);
		assertTrue(interval.type == Type.Integer);
		assertTrue(interval.minValue instanceof Integer);
		assertTrue(interval.maxValue instanceof Integer);
		assertEquals(2, interval.minValue);
		assertEquals(13, interval.maxValue);
		assertNull(interval.decimalPlaces);
		assertEquals(interval.values, Arrays.asList(10, 13, 2, 10));

		interval = Interval.parse(Type.Integer, " { 100 } ");
		assertTrue(interval.isSet);
		assertTrue(interval.type == Type.Integer);
		assertTrue(interval.minValue instanceof Integer);
		assertTrue(interval.maxValue instanceof Integer);
		assertEquals(100, interval.minValue);
		assertEquals(100, interval.maxValue);
		assertNull(interval.decimalPlaces);
		assertEquals(interval.values, Arrays.asList(100));

		interval = Interval.parse(Type.Double, "{0.45, 1.2, 3.0, 4.567}");
		assertTrue(interval.isSet);
		assertTrue(interval.type == Type.Double);
		assertTrue(interval.minValue instanceof Double);
		assertTrue(interval.maxValue instanceof Double);
		assertEquals(0.45, interval.minValue);
		assertEquals(4.567, interval.maxValue);
		assertNull(interval.decimalPlaces);
		assertEquals(interval.values, Arrays.asList(0.45, 1.2, 3.0, 4.567));

		interval = Interval.parse(Type.Double, "        {0.450}");
		assertTrue(interval.isSet);
		assertTrue(interval.type == Type.Double);
		assertTrue(interval.minValue instanceof Double);
		assertTrue(interval.maxValue instanceof Double);
		assertEquals(0.45, interval.minValue);
		assertEquals(0.450, interval.maxValue);
		assertNull(interval.decimalPlaces);
		assertEquals(interval.values, Arrays.asList(0.45));

		interval = Interval.parse(Type.Double, "{ 0.0 ,1.0}");
		assertTrue(interval.isSet);
		assertTrue(interval.type == Type.Double);
		assertTrue(interval.minValue instanceof Double);
		assertTrue(interval.maxValue instanceof Double);
		assertEquals(new Double(0), interval.minValue);
		assertEquals(new Double(1), interval.maxValue);
		assertNull(interval.decimalPlaces);
		assertEquals(Arrays.asList(0.0, 1.0), interval.values);

		interval = Interval.parse(Type.String, "{ v1, v2, v5 }");
		assertTrue(interval.isSet);
		assertTrue(interval.type == Type.String);
		assertNull(interval.decimalPlaces);
		assertEquals(interval.values, Arrays.asList("v1", "v2", "v5"));

		interval = Interval.parse(Type.String, " {v1}");
		assertTrue(interval.isSet);
		assertTrue(interval.type == Type.String);
		assertNull(interval.decimalPlaces);
		assertEquals(interval.values, Arrays.asList("v1"));

		interval = Interval.parse(Type.Boolean, "[false,true]");
		assertFalse(interval.isSet);
		assertTrue(interval.type == Type.Boolean);
		assertEquals(Boolean.FALSE, interval.minValue);
		assertEquals(Boolean.TRUE, interval.maxValue);
		assertNull(interval.decimalPlaces);
		assertNull(interval.values);

		interval = Interval.parse(Type.Boolean, "[true, true]");
		assertFalse(interval.isSet);
		assertTrue(interval.type == Type.Boolean);
		assertEquals(true, interval.minValue);
		assertEquals(Boolean.TRUE, interval.maxValue);
		assertNull(interval.decimalPlaces);
		assertNull(interval.values);

		interval = Interval.parse(Type.Integer, "[0,10] ");
		assertFalse(interval.isSet);
		assertTrue(interval.type == Type.Integer);
		assertTrue(interval.minValue instanceof Integer);
		assertTrue(interval.maxValue instanceof Integer);
		assertEquals(0, interval.minValue);
		assertEquals(10, interval.maxValue);
		assertNull(interval.decimalPlaces);
		assertNull(interval.values);

		interval = Interval.parse(Type.Integer, "[10, 10]");
		assertFalse(interval.isSet);
		assertTrue(interval.type == Type.Integer);
		assertTrue(interval.minValue instanceof Integer);
		assertTrue(interval.maxValue instanceof Integer);
		assertEquals(10, interval.minValue);
		assertEquals(10, interval.maxValue);
		assertNull(interval.decimalPlaces);
		assertNull(interval.values);

		interval = Interval.parse(Type.Double, "[0.45, 4.567]");
		assertFalse(interval.isSet);
		assertTrue(interval.type == Type.Double);
		assertTrue(interval.minValue instanceof Double);
		assertTrue(interval.maxValue instanceof Double);
		assertEquals(0.45, interval.minValue);
		assertEquals(4.567, interval.maxValue);
		assertEquals(new Integer(2), interval.decimalPlaces);
		assertNull(interval.values);

		interval = Interval.parse(Type.Double, "[5.4504, 5.4504]");
		assertFalse(interval.isSet);
		assertTrue(interval.type == Type.Double);
		assertTrue(interval.minValue instanceof Double);
		assertTrue(interval.maxValue instanceof Double);
		assertEquals(5.4504, interval.minValue);
		assertEquals(5.4504, interval.maxValue);
		assertEquals(new Integer(2), interval.decimalPlaces);
		assertNull(interval.values);

		interval = new Interval(Type.Double, 0.0, 1.0);
		interval = Interval.parse(Type.Double, "[0.0, 1.0]");
		assertFalse(interval.isSet);
		assertTrue(interval.type == Type.Double);
		assertTrue(interval.minValue instanceof Double);
		assertTrue(interval.maxValue instanceof Double);
		assertEquals(new Double(0), interval.minValue);
		assertEquals(new Double(1), interval.maxValue);
		assertEquals(new Integer(2), interval.decimalPlaces);
		assertNull(interval.values);

		interval = Interval.parse(Type.Double, "[0.45,4.567,3]");
		assertFalse(interval.isSet);
		assertTrue(interval.type == Type.Double);
		assertEquals(0.45, interval.minValue);
		assertEquals(4.567, interval.maxValue);
		assertEquals(new Integer(3), interval.decimalPlaces);
		assertNull(interval.values);

		interval = Interval.parse(Type.Double, "[0.45, 0.45, 0]");
		assertFalse(interval.isSet);
		assertTrue(interval.type == Type.Double);
		assertEquals(0.45, interval.minValue);
		assertEquals(0.45, interval.maxValue);
		assertEquals(new Integer(0), interval.decimalPlaces);
		assertNull(interval.values);
		
		try {
			Interval.parse(Type.Double, "{}");
			fail("Expected a exception.");
		} catch (ParseException e) {}
		
		try {
			Interval.parse(Type.Double, "[]");
			fail("Expected a exception.");
		} catch (ParseException e) {}
		
		try {
			Interval.parse(Type.Double, "{      }");
			fail("Expected a exception.");
		} catch (ParseException e) {}
		
		try {
			Interval.parse(Type.Double, "[     ]");
			fail("Expected a exception.");
		} catch (ParseException e) {}
		try {
			Interval.parse(Type.Double, "{   ,   }");
			fail("Expected a exception.");
		} catch (ParseException e) {}
		
		try {
			Interval.parse(Type.Double, "[  ,   ]");
			fail("Expected a exception.");
		} catch (ParseException e) {}
		try {
			Interval.parse(Type.Double, "{  ,  ,  }");
			fail("Expected a exception.");
		} catch (ParseException e) {}

		try {
			Interval.parse(Type.Double, "[   , 0.0  ]");
			fail("Expected a exception.");
		} catch (ParseException e) {}

		try {
			Interval.parse(Type.String, "[v1, v2]");
			fail("Expected a exception.");
		} catch (ParseException e) {}

		try {
			Interval.parse(Type.Double, "{0.0 0.1}");
			fail("Expected a exception.");
		} catch (ParseException e) {}

		try {
			Interval.parse(Type.Double, "{0.0, 0.1]");
			fail("Expected a exception.");
		} catch (ParseException e) {}

		try {
			Interval.parse(Type.Double, "0.4");
			fail("Expected a exception.");
		} catch (ParseException e) {}

		try {
			Interval.parse(Type.Double, "");
			fail("Expected a exception.");
		} catch (ParseException e) {}

		try {
			Interval.parse(Type.Double, "{0.5]");
			fail("Expected a exception.");
		} catch (ParseException e) {}

		try {
			Interval.parse(Type.Double, "[0.4,0.4}");
			fail("Expected a exception.");
		} catch (ParseException e) {}

		try {
			Interval.parse(Type.Double, "[0.4 0.4]");
			fail("Expected a exception.");
		} catch (ParseException e) {}

		try {
			Interval.parse(Type.Double, "[0.4 0.4 0.4]");
			fail("Expected a exception.");
		} catch (ParseException e) {}

		try {
			Interval.parse(Type.Integer, "[1,2,3]");
			fail("Expected a exception.");
		} catch (ParseException e) {}

		try {
			Interval.parse(Type.Double, "[1]");
			fail("Expected a exception.");
		} catch (ParseException e) {}

		try {
			Interval.parse(Type.Integer, "[1,2,3,4]");
			fail("Expected a exception.");
		} catch (ParseException e) {}

		try {
			Interval.parse(Type.Boolean, "[true,false,3]");
			fail("Expected a exception.");
		} catch (ParseException e) {}

		try {
			Interval.parse(Type.Integer, "[1.2,2,3]");
			fail("Expected a exception.");
		} catch (ParseException e) {}

		try {
			Interval.parse(Type.Boolean, "[false,yes]");
			fail("Expected a exception.");
		} catch (ParseException e) {}

		try {
			Interval.parse(Type.Boolean, "{text}");
			fail("Expected a exception.");
		} catch (ParseException e) {}
	}
}
