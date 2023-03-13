package weka.classifiers.meta.pbil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.util.Arrays;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import weka.classifiers.meta.pbil.Interval.Type;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ParameterTest {

	@Test
	public void test001Parameter() {
		Interval interval;
		Parameter parameter;

		interval = new Interval(Type.Integer, Arrays.asList(0, 1, 2));
		parameter = new Parameter("p1", "-A", "0", interval, false);
		assertEquals("p1", parameter.name);
		assertEquals("-A", parameter.key);
		assertEquals("0", parameter.defaultValue);
		assertEquals(interval, parameter.interval);
		assertFalse(parameter.isClassifier);
		assertNotNull(parameter.subParameters);
		assertTrue(parameter.subParameters.isEmpty());
	}

	@Test
	public void test002Parse() throws ParseException {
		Parameter parameter;
		Parameter model;
		
		parameter = Parameter.parse("@parameter useADTree -D false boolean");
		assertEquals("useADTree", parameter.name);
		assertEquals("-D", parameter.key);
		assertEquals("false", parameter.defaultValue);
		assertNotNull(parameter.interval);
		assertEquals(Type.Boolean, parameter.interval.type);
		assertEquals(2, parameter.interval.size());
		assertFalse(parameter.isClassifier);
		assertNotNull(parameter.subParameters);
		assertTrue(parameter.subParameters.isEmpty());
		
		model = parameter;
		parameter = Parameter.parse("@parameter useADTree -D false boolean");
		isEquals(model, parameter);
		parameter = Parameter.parse("    @parameter useADTree -D false boolean");
		isEquals(model, parameter);
		parameter = Parameter.parse("@parameter    useADTree -D false boolean");
		isEquals(model, parameter);
		parameter = Parameter.parse("@parameter useADTree     -D false boolean");
		isEquals(model, parameter);
		parameter = Parameter.parse("@parameter useADTree -D     false boolean");
		isEquals(model, parameter);
		parameter = Parameter.parse("@parameter useADTree -D false     boolean");
		isEquals(model, parameter);
		parameter = Parameter.parse("@parameter useADTree -D false boolean   ");
		isEquals(model, parameter);
		parameter = Parameter.parse("   @parameter useADTree -D false boolean  ");
		isEquals(model, parameter);
		
		parameter = Parameter.parse("@parameter wheightThreshold -P 100 integer [50,100]");
		assertEquals("wheightThreshold", parameter.name);
		assertEquals("-P", parameter.key);
		assertEquals("100", parameter.defaultValue);
		assertNotNull(parameter.interval);
		assertEquals(Type.Integer, parameter.interval.type);
		assertEquals(51, parameter.interval.size());
		assertFalse(parameter.isClassifier);
		assertNotNull(parameter.subParameters);
		assertTrue(parameter.subParameters.isEmpty());
		
		parameter = Parameter.parse("@parameter useADTree -D 0 integer {0, 1, 5}");
		assertEquals("useADTree", parameter.name);
		assertEquals("-D", parameter.key);
		assertEquals("0", parameter.defaultValue);
		assertNotNull(parameter.interval);
		assertEquals(Type.Integer, parameter.interval.type);
		assertEquals(3, parameter.interval.size());
		assertFalse(parameter.isClassifier);
		assertNotNull(parameter.subParameters);
		assertTrue(parameter.subParameters.isEmpty());
		
		model = parameter;
		parameter = Parameter.parse("@parameter useADTree -D 0 integer {0, 1, 5}");
		isEquals(model, parameter);
		parameter = Parameter.parse("    @parameter useADTree -D 0 integer {0,1,5}");
		isEquals(model, parameter);
		parameter = Parameter.parse("@parameter    useADTree    -D 0 integer {0, 1, 5}");
		isEquals(model, parameter);
		parameter = Parameter.parse("@parameter useADTree -D 0    integer {0, 1, 5}");
		isEquals(model, parameter);
		parameter = Parameter.parse("@parameter useADTree -D 0 integer {0,    1, 5}");
		isEquals(model, parameter);
		parameter = Parameter.parse("  @parameter useADTree -D 0 integer {0, 1, 5}    ");
		isEquals(model, parameter);
		parameter = Parameter.parse("@parameter useADTree -D 0    integer {     0, 1, 5}");
		isEquals(model, parameter);
		parameter = Parameter.parse("@parameter useADTree -D 0 integer {0, 1, 5}");
		isEquals(model, parameter);
		parameter = Parameter.parse("@parameter useADTree -D 0 integer {0, 1, 5}   ");
		isEquals(model, parameter);
		
		parameter = Parameter.parse("@parameter useADTree -D 0.0 double {0.0, 1.0, 5.1}");
		assertEquals("useADTree", parameter.name);
		assertEquals("-D", parameter.key);
		assertEquals("0.0", parameter.defaultValue);
		assertNotNull(parameter.interval);
		assertEquals(Type.Double, parameter.interval.type);
		assertEquals(3, parameter.interval.size());
		assertFalse(parameter.isClassifier);
		assertNotNull(parameter.subParameters);
		assertTrue(parameter.subParameters.isEmpty());
		
		parameter = Parameter.parse("@parameter useADTree -D 0.0 double [0.0, 5.1]");
		assertEquals("useADTree", parameter.name);
		assertEquals("-D", parameter.key);
		assertEquals("0.0", parameter.defaultValue);
		assertNotNull(parameter.interval);
		assertEquals(Type.Double, parameter.interval.type);
		assertEquals(511, parameter.interval.size());
		assertFalse(parameter.isClassifier);
		assertNotNull(parameter.subParameters);
		assertTrue(parameter.subParameters.isEmpty());
		
		parameter = Parameter.parse("   @parameter    useADTree -D 0.0 double [0.0, 1.0,  3]  ");
		assertEquals("useADTree", parameter.name);
		assertEquals("-D", parameter.key);
		assertEquals("0.0", parameter.defaultValue);
		assertNotNull(parameter.interval);
		assertEquals(Type.Double, parameter.interval.type);
		assertEquals(1001, parameter.interval.size());
		assertFalse(parameter.isClassifier);
		assertNotNull(parameter.subParameters);
		assertTrue(parameter.subParameters.isEmpty());
		
		parameter = Parameter.parse("@parameter useADTree -D c1 classifier {c1, c2, c4}");
		assertEquals("useADTree", parameter.name);
		assertEquals("-D", parameter.key);
		assertEquals("c1", parameter.defaultValue);
		assertNotNull(parameter.interval);
		assertEquals(Type.String, parameter.interval.type);
		assertEquals(3, parameter.interval.size());
		assertTrue(parameter.isClassifier);
		assertNotNull(parameter.subParameters);
		assertTrue(parameter.subParameters.isEmpty());
		
		parameter = Parameter.parse("@parameter useADTree -D c1 string {c1, c2, c4}");
		assertEquals("useADTree", parameter.name);
		assertEquals("-D", parameter.key);
		assertEquals("c1", parameter.defaultValue);
		assertNotNull(parameter.interval);
		assertEquals(Type.String, parameter.interval.type);
		assertEquals(3, parameter.interval.size());
		assertFalse(parameter.isClassifier);
		assertNotNull(parameter.subParameters);
		assertTrue(parameter.subParameters.isEmpty());
		
		try {
			parameter = Parameter.parse("@parameter");
			fail("Expected a exception.");
		} catch(ParseException e){}
		
		try {
			parameter = Parameter.parse("@parameter useADTree -D c1 string {v1}");
			fail("Expected a exception.");
		} catch(ParseException e){}
		
		try {
			parameter = Parameter.parse("@paramter useADTree -D c1 string {v1}");
			fail("Expected a exception.");
		} catch(ParseException e){}
		
		try {
			parameter = Parameter.parse("@parameter  -D c1 string {c1}");
			fail("Expected a exception.");
		} catch(ParseException e){}
		
		try {
			parameter = Parameter.parse("@parameter useADTree  c1 string {c1}");
			fail("Expected a exception.");
		} catch(ParseException e){}
		
		try {
			parameter = Parameter.parse("@parameter useADTree -D  string {c1}");
			fail("Expected a exception.");
		} catch(ParseException e){}
		
		try {
			parameter = Parameter.parse("@parameter useADTree -D c1  {c1}");
			fail("Expected a exception.");
		} catch(ParseException e){}
		
		try {
			parameter = Parameter.parse("@parameter useADTree -D c1 string [c1]");
			fail("Expected a exception.");
		} catch(ParseException e){}
		
		try {
			parameter = Parameter.parse("@parameter use ADTree -D c1 string {c1}");
			fail("Expected a exception.");
		} catch(ParseException e){}
		
		try {
			parameter = Parameter.parse("parameter useADTree -D c1 string {v1}");
			fail("Expected a exception.");
		} catch(ParseException e){}
		
		try {
			parameter = Parameter.parse("@parameter useADTree -D c1 c2 string {v1}");
			fail("Expected a exception.");
		} catch(ParseException e){}
		
		try {
			parameter = Parameter.parse("@parameter useADTree -D c1 string {v1} [3]");
			fail("Expected a exception.");
		} catch(ParseException e){}
		
		try {
			parameter = Parameter.parse("@parameter useADTree -D c1 string [false, true]");
		} catch(ParseException e){}
	}
	
	private void isEquals(Parameter p1, Parameter parameter){
		assertEquals(p1.name, parameter.name);
		assertEquals(p1.key, parameter.key);
		assertEquals(p1.defaultValue, parameter.defaultValue);
		assertEquals(p1.interval.type, parameter.interval.type);
		assertEquals(p1.interval.size(), parameter.interval.size());
		assertEquals(p1.isClassifier, parameter.isClassifier);
		assertEquals(p1.subParameters, parameter.subParameters);
	}
}













