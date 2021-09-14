package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.filters.Filter;
import edu.eci.arsw.blueprints.filters.impl.RedundancyFiltering;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RendundancyFilteringTest {

    private Filter filter;
    private List<Blueprint> list;

    @Before
    public void setVariable() {
        filter = new RedundancyFiltering();

        Point[] pts0 = new Point[]{new Point(40, 40), new Point(15, 15), new Point(15, 15)};
        Blueprint bp0 = new Blueprint("mack", "mypaint", pts0);

        Point[] pts1 = new Point[]{new Point(40, 40), new Point(40, 40), new Point(40, 40), new Point(15, 15)};
        Blueprint bp1 = new Blueprint("riot", "lolcito", pts1);


        Point[] pts2 = new Point[]{new Point(40, 40), new Point(15, 15)};
        Blueprint bp2 = new Blueprint("riot", "valorant", pts2);

        list = new ArrayList<Blueprint>();
        list.add(bp0);
        list.add(bp1);
        list.add(bp2);
    }


    @Test
    public void uniqueFilterTest() {
        Blueprint bl = list.get(0);
        List<Point> list1 = bl.getPoints();
        Blueprint bl2 = filter.filterBlueprint(bl);
        List<Point> list2 = bl2.getPoints();
        Assert.assertFalse(list1.equals(list2));
    }

    @Test
    public void multiFilterTest() {
        List<Point> b1 = list.get(0).getPoints();
        List<Point> b2 = list.get(1).getPoints();
        List<Point> b3 = list.get(2).getPoints();
        Set<Blueprint> setB = new HashSet<Blueprint>(list);
        Set<Blueprint> setResponse = filter.multiFilter(setB);
        List<Blueprint> listb = new ArrayList<>(setResponse);
        List<Point> r1 = listb.get(0).getPoints();
        List<Point> r2 = listb.get(1).getPoints();
        List<Point> r3 = listb.get(2).getPoints();
        Assert.assertFalse(b2.equals(r2));
    }

}