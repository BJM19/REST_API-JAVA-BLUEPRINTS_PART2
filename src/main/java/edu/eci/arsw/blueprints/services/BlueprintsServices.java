/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.filters.Filter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author hcadavid
 */
@Service("blueprintsServices")
public class BlueprintsServices implements BlueprintsServicesInterface {

    @Autowired
    @Qualifier("inmemory")
    BlueprintsPersistence bpp;

    @Autowired
    @Qualifier("redundancy")
    //@Qualifier("subsampling")
    Filter filter;

    @Override
    public void addNewBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        bpp.saveBlueprint(bp);
    }

    /**
     * @return the set off all blueprint
     * @throws BlueprintNotFoundException
     */
    @Override
    public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException {
        return bpp.getAllBlueprints();
    }

    /**
     * @param author blueprint's author
     * @param name   blueprint's name
     * @return the blueprint of the given name created by the given author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    @Override
    public Blueprint getBlueprint(String author, String name) throws BlueprintNotFoundException {
        return bpp.getBlueprint(author, name);
    }

    /**
     * @param author blueprint's author
     * @return all the blueprints of the given author
     * @throws BlueprintNotFoundException if the given author doesn't exist
     */
    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        return bpp.getBlueprintsByAuthor(author);
    }

    /**
     * @param blueprint blueprint
     * @return filtered blueprint
     */
    @Override
    public Blueprint filterBlueprint(Blueprint blueprint) {
        return filter.filterBlueprint(blueprint);
    }

    /**
     * @param blueprints set off blueprint
     * @return filtered a set off blueprint
     */
    @Override
    public Set<Blueprint> multiFilter(Set<Blueprint> blueprints) {
        return filter.multiFilter(blueprints);
    }

}
