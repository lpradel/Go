/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package go;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sopr055
 */
public class PositionTest {

    public PositionTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testPosition() {
    	System.out.println("Position");
        Position pos = new Position(3, 5);
        
        if ( pos.gibX() != 3 || pos.gibY() != 5 )
        {
        	fail("Positionsfail.");
        }
        
        Position pos2 = new Position("e3");

        if ( pos2.gibX() != 5 || pos2.gibY() != 3 )
        {
        	fail("Positionsfail 2.");
        }
    }

}