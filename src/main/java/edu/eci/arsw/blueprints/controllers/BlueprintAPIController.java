/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import com.google.gson.Gson;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.services.BlueprintsServicesInterface;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author hcadavid
 */
@RestController
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {

    @Autowired
    @Qualifier("blueprintsServices")
    BlueprintsServicesInterface blueprintsServices;


    /*GET METHODS*/

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getBlueprints() {
        try {
            Set<Blueprint> blueprintSet = blueprintsServices.multiFilter(blueprintsServices.getAllBlueprints());
            String gsonString = this.makeStringForGson(blueprintSet);
            return new ResponseEntity<>(new Gson().toJson(gsonString), HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{author}", method = RequestMethod.GET)
    public ResponseEntity<?> getBlueprintsByAuthor(@PathVariable String author) {
        try {
            Set<Blueprint> blueprintSet = blueprintsServices.multiFilter(blueprintsServices.getBlueprintsByAuthor(author));
            String gsonString = this.makeStringForGson(blueprintSet);
            return new ResponseEntity<>(new Gson().toJson(gsonString), HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{author}/{bpname}", method = RequestMethod.GET)
    public ResponseEntity<?> getBlueprintsByAuthor(@PathVariable String author, @PathVariable String bpname) {
        try {
            Blueprint blueprint = blueprintsServices.filterBlueprint(blueprintsServices.getBlueprint(author, bpname));
            Set<Blueprint> blueprintSet = new HashSet<Blueprint>();
            blueprintSet.add(blueprint);
            String gsonString = this.makeStringForGson(blueprintSet);
            return new ResponseEntity<>(new Gson().toJson(gsonString), HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }
    }

    /*POST METHODS*/
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> manejadorPostRecursoPlanos(@RequestBody JSONObject blueprint) {
        try {
            Blueprint temp = new Blueprint(blueprint.get("Autor").toString(), blueprint.get("Nombre").toString());
            String[] list = blueprint.get("Puntos").toString().split("-");
            for (String str : list) {
                List<String> templis = Arrays.asList(str.split(","));
                String corX = templis.get(0).substring(2);
                String corY = templis.get(1).substring(2, templis.get(0).length());
                Point po = new Point(Integer.parseInt(corX), Integer.parseInt(corY));
                temp.addPoint(po);
            }
            blueprintsServices.addNewBlueprint(temp);
            return new ResponseEntity<>(HttpStatus.CREATED.getReasonPhrase(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.FORBIDDEN.getReasonPhrase(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/{author}/{bpname}", method = RequestMethod.PUT)
    public ResponseEntity<?> putBlueprintsByAuthor(@PathVariable String author, @PathVariable String bpname, @RequestBody JSONObject data) {
        try {
            Blueprint blueprint = blueprintsServices.filterBlueprint(blueprintsServices.getBlueprint(author, bpname));
            String[] list = data.get("Puntos").toString().split("-");
            blueprint.setAuthor(data.get("Autor").toString());
            blueprint.setName(data.get("Nombre").toString());
            for (String str : list) {
                List<String> templis = Arrays.asList(str.split(","));
                String corX = templis.get(0).substring(2);
                String corY = templis.get(1).substring(2, templis.get(0).length());
                Point po = new Point(Integer.parseInt(corX), Integer.parseInt(corY));
                blueprint.addPoint(po);
            }
            Set<Blueprint> blueprintSet = new HashSet<Blueprint>();
            blueprintSet.add(blueprint);
            String gsonString = this.makeStringForGson(blueprintSet);
            return new ResponseEntity<>(new Gson().toJson(gsonString), HttpStatus.CREATED);
        } catch (BlueprintNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST);
        }
    }

    /*Extra methods*/
    private String makeStringForGson(Set<Blueprint> blueprints) {
        List<Blueprint> blueprintList = new ArrayList<>(blueprints);
        String blueprintStrings = "{\"blueprints\" : ";
        for (Blueprint blueprint : blueprintList) {
            String autor = blueprint.getAuthor();
            String nombre = blueprint.getName();
            String puntos = blueprint.getPointsString();
            blueprintStrings += "{\"Autor\": \"" + autor + "\", \"Nombre\": \"" + nombre + "\", \"Puntos\": \"" + puntos + "\"}";
        }
        blueprintStrings += "}";
        return blueprintStrings;
    }

}

