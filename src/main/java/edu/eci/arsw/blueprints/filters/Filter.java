package edu.eci.arsw.blueprints.filters;

import edu.eci.arsw.blueprints.model.Blueprint;

import java.util.Set;


public interface Filter {

    /**
     * @param blueprint blueprint
     * @return filtered blueprint
     */
    Blueprint filterBlueprint(Blueprint blueprint);

    /**
     * @param blueprints set off blueprint
     * @return filtered a set off blueprint
     */
    Set<Blueprint> multiFilter(Set<Blueprint> blueprints);
}
