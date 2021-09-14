package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface BlueprintsServicesInterface {

    void addNewBlueprint(Blueprint bp) throws BlueprintPersistenceException;

    Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException;

    Blueprint getBlueprint(String author, String name) throws BlueprintNotFoundException;

    Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException;

    Blueprint filterBlueprint(Blueprint blueprint);

    Set<Blueprint> multiFilter(Set<Blueprint> blueprints);
}
