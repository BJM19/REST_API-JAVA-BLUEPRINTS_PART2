/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hcadavid
 */
@Component
@Qualifier("inmemory")
public class InMemoryBlueprintPersistence implements BlueprintsPersistence {

    private final Map<Tuple<String, String>, Blueprint> blueprints = new ConcurrentHashMap<>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts = new Point[]{new Point(140, 140), new Point(115, 115)};
        Point[] pts1 = new Point[]{new Point(43, 34), new Point(15, 15), new Point(15, 15)};
        Point[] pts2 = new Point[]{new Point(14, 14), new Point(11, 11), new Point(11, 11)};
        Point[] pts3 = new Point[]{new Point(10, 12), new Point(876, 654)};

        Blueprint bp = new Blueprint("_authorname_", "_bpname_ ", pts);
        Blueprint bp1 = new Blueprint("riot", "lolcito", pts1);
        Blueprint bp2 = new Blueprint("riot", "valorant", pts2);
        Blueprint bp3 = new Blueprint("konami", "pes2021", pts3);

        blueprints.put(new Tuple<>(bp.getAuthor(), bp.getName()), bp);
        blueprints.put(new Tuple<>(bp1.getAuthor(), bp1.getName()), bp1);
        blueprints.put(new Tuple<>(bp2.getAuthor(), bp2.getName()), bp2);
        blueprints.put(new Tuple<>(bp3.getAuthor(), bp3.getName()), bp3);
    }

    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(), bp.getName()))) {
            throw new BlueprintPersistenceException("The given blueprint already exists: " + bp);
        } else {
            blueprints.put(new Tuple<>(bp.getAuthor(), bp.getName()), bp);
        }
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        if ((blueprints.get(new Tuple<>(author, bprintname)) != null)) {
            return blueprints.get(new Tuple<>(author, bprintname));
        }
        throw new BlueprintNotFoundException("Blueprint no encontrado");
    }

    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> blueprintSet = new HashSet<Blueprint>();
        for (Tuple<String, String> blueprintKey : blueprints.keySet()) {
            if (blueprintKey.getElem1().equals(author)) {
                blueprintSet.add(blueprints.get(blueprintKey));
            }
        }
        if (blueprintSet.size() != 0) {
            return blueprintSet;
        }
        throw new BlueprintNotFoundException("Blueprints no encontrados");
    }

    @Override
    public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException {
        Set<Blueprint> blueprintSet = new HashSet<Blueprint>();
        for (Tuple<String, String> blueprintKey : blueprints.keySet()) {
            if (!blueprintKey.getElem1().equals("_authorname_")) {
                blueprintSet.add(blueprints.get(blueprintKey));
            }
        }
        return blueprintSet;
    }

}