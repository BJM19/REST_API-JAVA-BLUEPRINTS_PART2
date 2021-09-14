package edu.eci.arsw.blueprints.filters.impl;

import edu.eci.arsw.blueprints.filters.Filter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@Qualifier("subsampling")
public class SubsamplingFilter implements Filter {
    @Override
    public Blueprint filterBlueprint(Blueprint blueprint) {
        List<Point> list = blueprint.getPoints();
        List<Point> update = new ArrayList<Point>();

        for (int i = 0; i < list.size(); i++) {
            if (i % 2 == 0) {
                update.add(list.get(i));
            }
        }

        blueprint.updatePoint(update);

        return blueprint;
    }

    @Override
    public Set<Blueprint> multiFilter(Set<Blueprint> blueprints) {
        for (Blueprint bl : blueprints) {
            filterBlueprint(bl);
        }
        return blueprints;
    }
}
