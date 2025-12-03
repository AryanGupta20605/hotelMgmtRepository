package com.restapiproject.hotelMgmt;

public class JunitConcepts {
	
	/*
	 * open source testing framework for the Java programming language
	 * tool for developers to create tests - units of the code
	 * ensure the quality and maintainability of Java codes
	 *
	 * light weight testing framework , we use it for unit testing
	 * write automated tests that validate small peices of code(units)(Classes)
	 *
	 * benefits - exceotions , enables to document expected behavior, enables test driven developement TDD
	 *
	 * Junit 5 -> more modular approach , backward compatibility
	 * Spring boot -> spring-boot-starter-test -> bundles Junit and test utilies
	 *
	 * Add Junit5 dependency -> org.junit.jupiter - scope -> test : does not get with thw production code
	 *
	 *Junit - Test Framrwork - Architecture
	 *Modules -> Platform - discovers and run tests
	 *        -> Jupiter - new programming model (annotations, assertions)
	 *        -> Vintage - rund Junit 3/4 test on Junit5 platform
	 *oncepts : test discovery, execution, lifecyle methods, assertions.....
	 *
	 *Junit - class - write test methods - annotate them with @Test
	 *        pattern AAA -> A : Arrange -> prepare data/mocks
	 *                       A : Act -> invoke method under test
	 *                       A : Assert -> verify the outcome using assertions
	 *                       
	 * public class Calculatot {
	 *  public int add(int a, int b){
	 *       return a+b;
	 *      }
	 *  }
	 *  
	 *  public class CalculatorTest {	 *  
	 *     @Test
	 *     void testAdd() {
	 *        Calculator calc = new Calculator();
	 *        int result = calc.add(12,13);
	 *        assertEquals(25, result, "12+13 should equal 25");
	 *                    expected , actual
	 *                    check -> expected == actual
	 *     }
	 *  }
	 *  
	 *  Test case : respresents a specific scenario or condition to be tested
	 *  test cases are implemented as methods within a test class, annotated with @Test
	 *  
	 *  Provided by Junit - API
	 *  
	 *  Annotations :
	 *  @Test -> Marks a method as a unit Test
	 *  @BeforeEach -> Executes a method before each test method in a class
	 *  @AfterEach -> Executes a method after each test method in a class
	 *  @BeforeAll -> Executes a method once before all test methods in a class
	 *  @AfterAll -> Executes a method once after all tests methods in a class
	 *  
	 *  Assertions :
	 *  -> are used within test methods to verify outcome.
	 *  assertEquals(expected, actual) : Check if two values are equal
	 *  assertTrue(condition) : Check if a condition is true
	 *  assertNotNull(object) : Check if object is not null
	 *  assertThrows(expectedException, executable) : Verify that a specific exception is thrown
	 *  
	 *  Assumptions :
	 *  -> skip tests under certain conditions
	 *  Assumptions.assumeTrue(...)
	 *   
	 *  Junit Test Runners : Junit includes test runners responsibe for executing test cases and reporting results
	 *  
	 *  Earlt bug detection
	 *  improved code quality and maintainance
	 *  
	 *  Junit - Suite Test
	 *  -> run a group of test classes together as one suite
	 *  -> logical groupings, integration test sets
	 *  -> Junit5 -> @Suite
	 *  
	 *  Junit - Parameterized Test
	 *  -> run same test logic with multiple inputs. Reduce duplication and edge cases
	 *  Junit 5 provides @ParamterizedTest - @ValueSource @MethodSource
	 *  define input set, the test run once per input - assert expect outcome per input
	 *  
	 *  
	 *  
	 *  
	 *  
	 *  
	 *  
	 *  
	 *  
	 *  
	 *  
	 *  
	 *  
	 *  
	 */
	
 
}
 
 