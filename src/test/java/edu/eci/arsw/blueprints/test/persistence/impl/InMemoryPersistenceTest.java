/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author hcadavid
 */
public class InMemoryPersistenceTest {

    @Test
    public void saveNewAndLoadTest() throws BlueprintPersistenceException, BlueprintNotFoundException {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        Point[] pts0 = new Point[]{new Point(40, 40), new Point(15, 15)};
        Blueprint bp0 = new Blueprint("mack", "mypaint", pts0);

        ibpp.saveBlueprint(bp0);

        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp = new Blueprint("john", "thepaint", pts);

        ibpp.saveBlueprint(bp);

        assertNotNull("Loading a previously stored blueprint returned null.", ibpp.getBlueprint(bp.getAuthor(), bp.getName()));

        assertEquals("Loading a previously stored blueprint returned a different blueprint.", ibpp.getBlueprint(bp.getAuthor(), bp.getName()), bp);
    }


    @Test
    public void saveExistingBpTest() {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp = new Blueprint("john", "thepaint", pts);

        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }

        Point[] pts2 = new Point[]{new Point(10, 10), new Point(20, 20)};
        Blueprint bp2 = new Blueprint("john", "thepaint", pts2);

        try {
            ibpp.saveBlueprint(bp2);
            fail("An exception was expected after saving a second blueprint with the same name and autor");
        } catch (BlueprintPersistenceException ex) {

        }
    }

    @Test
    public void canGetBlueprint() {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();
        //Crear blueprint
        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp = new Blueprint("john", "thepaint", pts);
        //Guardamos blueprint
        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }
        //Comparar que los blueprint son iguales
        try {
            Blueprint blueprint = ibpp.getBlueprint("john", "thepaint");
            assertEquals(blueprint, bp);
        } catch (BlueprintNotFoundException ex) {
            fail("No se encontro el bluprint correccto");
        }
    }

    @Test(expected = BlueprintNotFoundException.class)
    public void canNotGetBlueprint() throws BlueprintNotFoundException {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();
        //Crear blueprint
        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp = new Blueprint("john", "thepaint", pts);
        //Guardamos blueprint
        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }
        //Comparar que los blueprint son iguales
        Blueprint blueprint = ibpp.getBlueprint("ElMoncho", "The Alfredo's History");
    }

    @Test
    public void canGetBlueprintsByAuthor() {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();
        //Crear blueprints
        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp = new Blueprint("john", "thepaint", pts);
        Point[] pts2 = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp2 = new Blueprint("john", "thepaint2", pts);
        Point[] pts3 = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp3 = new Blueprint("john", "thepaintSequel", pts);
        //Set de blueprints
        Set<Blueprint> blueprintSet = new HashSet<Blueprint>();
        blueprintSet.add(bp);
        blueprintSet.add(bp2);
        blueprintSet.add(bp3);
        //Guardamos los blueprints
        try {
            ibpp.saveBlueprint(bp);
            ibpp.saveBlueprint(bp2);
            ibpp.saveBlueprint(bp3);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }
        //Comparar que los set blueprint son iguales
        try {
            Set<Blueprint> blueprintSetResult = ibpp.getBlueprintsByAuthor("john");
            assertEquals(blueprintSetResult, blueprintSet);
        } catch (BlueprintNotFoundException ex) {
            fail("No se encontro el bluprint correccto");
        }
    }

    @Test(expected = BlueprintNotFoundException.class)
    public void canNotGetBlueprintsByAuthor() throws BlueprintNotFoundException {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();
        //Crear blueprints
        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp = new Blueprint("john", "thepaint", pts);
        Point[] pts2 = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp2 = new Blueprint("john", "thepaint2", pts);
        Point[] pts3 = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp3 = new Blueprint("john", "thepaintSequel", pts);
        //Set de blueprints
        Set<Blueprint> blueprintSet = new HashSet<Blueprint>();
        blueprintSet.add(bp);
        blueprintSet.add(bp2);
        blueprintSet.add(bp3);
        //Guardamos los blueprints
        try {
            ibpp.saveBlueprint(bp);
            ibpp.saveBlueprint(bp2);
            ibpp.saveBlueprint(bp3);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }
        //Comparar que los set blueprint son iguales
        Set<Blueprint> blueprintSetResult = ibpp.getBlueprintsByAuthor("notJhon");
    }

}