package br.com.wirecard.tests;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	OrdersTest.class,
	PaymentsTest.class
})
public class SuitTest {

}
