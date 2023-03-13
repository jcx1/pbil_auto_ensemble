package weka.classifiers.meta.pbil;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.util.HashMap;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConditionalTest {

	@Test
	public void test001Parse() throws ParseException {
		HashMap<String, Parameter> parans = new HashMap<>();
		parans.put("-D", Parameter.parse("@parameter useSupervisedDiscretization -D false boolean"));
		parans.put("-K", Parameter.parse("@parameter useKernelEstimator -K false boolean"));

		Conditional conditional;

		conditional = Conditional.parse("@conditional -D == true", parans);
		assertNotNull(conditional);
		conditional = Conditional.parse("    @conditional -D == true", parans);
		assertNotNull(conditional);
		conditional = Conditional.parse("@conditional    -D == true", parans);
		assertNotNull(conditional);
		conditional = Conditional.parse("@conditional -D ==  true", parans);
		assertNotNull(conditional);
		conditional = Conditional.parse("@conditional -D == true    ", parans);
		assertNotNull(conditional);
		conditional = Conditional.parse("@conditional -D == true and -K == true", parans);
		assertNotNull(conditional);
		conditional = Conditional.parse("@conditional -D == true    and -K == true", parans);
		assertNotNull(conditional);
		conditional = Conditional.parse("@conditional -D == true and    -K == true", parans);
		assertNotNull(conditional);

		parans.clear();
		parans.put("-Q", Parameter.parse("@parameter useResampling -Q false boolean"));
		parans.put("-P", Parameter.parse("@parameter wheightThreshold -P 100 integer [50,100]"));

		conditional = Conditional.parse("@conditional -Q == true and -P != 100", parans);
		assertNotNull(conditional);
		conditional = Conditional.parse("@conditional -P != 100 and -P >= 50 and -P < 50 and -P <= 51 and -P > 55", parans);
		assertNotNull(conditional);
		
		try {
			Conditional.parse("@conditional -Q == true and -P != 0", parans);
			fail("Expected a exception.");
		} catch (ParseException e) {}
		
		try {
			Conditional.parse("@conditional -X == true and -P != 50", parans);
			fail("Expected a exception.");
		} catch (ParseException e) {}
		
		try {
			Conditional.parse("@conditional -Q == true and -P != 50 and", parans);
			fail("Expected a exception.");
		} catch (ParseException e) {}
		
		try {
			Conditional.parse("@conditional", parans);
			fail("Expected a exception.");
		} catch (ParseException e) {}
		
		try {
			Conditional.parse("@conditional -Q == true -P != 50", parans);
			fail("Expected a exception.");
		} catch (ParseException e) {}
		
		try {
			Conditional.parse("@conditional -Q == 1 and -P != 50", parans);
			fail("Expected a exception.");
		} catch (ParseException e) {}

		try {
			Conditional.parse("@conditional -Q = true and -P != 50", parans);
			fail("Expected a exception.");
		} catch (ParseException e) {}
	}

	@Test
	@SuppressWarnings("rawtypes")
	public void test002IsValid() throws ParseException {
		HashMap<String, Parameter> parans = new HashMap<>();
		parans.put("-D", Parameter.parse("@parameter useSupervisedDiscretization -D false boolean"));
		parans.put("-K", Parameter.parse("@parameter useKernelEstimator -K false boolean"));

		HashMap<String, Comparable> values = new HashMap<>();
		
		Conditional conditional;

		conditional = Conditional.parse("@conditional -D == true", parans);
		
		values.put("-D", true);
		assertTrue(conditional.isValid(values));
		values.put("-D", false);
		assertFalse(conditional.isValid(values));
		
		conditional = Conditional.parse("@conditional -D == true and -K == true", parans);
		values.put("-D", true);
		values.put("-K", true);
		assertTrue(conditional.isValid(values));
		values.put("-D", false);
		assertFalse(conditional.isValid(values));
		values.put("-D", true);
		values.put("-K", false);
		assertFalse(conditional.isValid(values));
		values.put("-D", false);
		assertFalse(conditional.isValid(values));
		
		parans.put("-P", Parameter.parse("@parameter wheightThreshold -P 100 integer [50,100]"));
		conditional = Conditional.parse("@conditional -P != 75", parans);
		values.put("-P", 75);
		assertFalse(conditional.isValid(values));
		values.put("-P", 74);
		assertTrue(conditional.isValid(values));
		values.put("-P", 76);
		assertTrue(conditional.isValid(values));
		
		conditional = Conditional.parse("@conditional -P == 75", parans);
		values.put("-P", 75);
		assertTrue(conditional.isValid(values));
		values.put("-P", 74);
		assertFalse(conditional.isValid(values));
		values.put("-P", 76);
		assertFalse(conditional.isValid(values));
		
		conditional = Conditional.parse("@conditional -P > 75", parans);
		values.put("-P", 75);
		assertFalse(conditional.isValid(values));
		values.put("-P", 74);
		assertFalse(conditional.isValid(values));
		values.put("-P", 76);
		assertTrue(conditional.isValid(values));
		
		conditional = Conditional.parse("@conditional -P >= 75", parans);
		values.put("-P", 75);
		assertTrue(conditional.isValid(values));
		values.put("-P", 74);
		assertFalse(conditional.isValid(values));
		values.put("-P", 76);
		assertTrue(conditional.isValid(values));
		
		conditional = Conditional.parse("@conditional -P < 75", parans);
		values.put("-P", 75);
		assertFalse(conditional.isValid(values));
		values.put("-P", 74);
		assertTrue(conditional.isValid(values));
		values.put("-P", 76);
		assertFalse(conditional.isValid(values));
		
		conditional = Conditional.parse("@conditional -P <= 75", parans);
		values.put("-P", 75);
		assertTrue(conditional.isValid(values));
		values.put("-P", 74);
		assertTrue(conditional.isValid(values));
		values.put("-P", 76);
		assertFalse(conditional.isValid(values));
		
		conditional = Conditional.parse("@conditional -P != 75 and -P != 74 and -P != 76", parans);
		values.put("-P", 75);
		assertFalse(conditional.isValid(values));
		values.put("-P", 74);
		assertFalse(conditional.isValid(values));
		values.put("-P", 76);
		assertFalse(conditional.isValid(values));
		values.put("-P", 73);
		assertTrue(conditional.isValid(values));
		values.put("-P", 77);
		assertTrue(conditional.isValid(values));
	}
}
















