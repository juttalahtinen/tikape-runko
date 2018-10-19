package tikape.runko;

import java.io.File;
import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.KysymysDao;
import tikape.runko.database.VastausDao;
import tikape.runko.domain.Kysymys;
import tikape.runko.domain.Vastaus;

public class Main {

    public static void main(String[] args) throws Exception {
//        Database database = new Database("jdbc:sqlite:kysymykset.db");
        File tiedosto = new File ("db", "kysymykset.db");
        Database database = new Database("jdbc:sqlite:"+tiedosto.getAbsolutePath());
        database.init();

        KysymysDao kysymysDao = new KysymysDao(database);
        VastausDao vastausDao = new VastausDao(database);
        
        System.out.println("Hei maailma!");

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viesti", "tervehdys");

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/kysymykset", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("kysymykset", kysymysDao.findAll());

            return new ModelAndView(map, "kysymykset");
        }, new ThymeleafTemplateEngine());
        
        post("/kysymykset", (req, res) -> {
            String kysymys = req.queryParams("name");

            System.out.println("Lisätään " + kysymys);
            
            kysymysDao.saveOrUpdate(new Kysymys(req.queryParams("kurssi"), req.queryParams("aihe"), req.queryParams("kysymysteksti")));

            res.redirect("/kysymykset");
            return "";
        });
        
        post("/kysymykset", (req, res) -> {
            String kysymys = req.queryParams("name");

            System.out.println("Poistetaan " + kysymys);
            
            kysymysDao.delete();

            res.redirect("/kysymykset");
            return "";
        });
        

        get("/kysymykset/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            Integer kysymysId = Integer.parseInt(req.params(":id"));
            map.put("kysymys", kysymysDao.findOne(Integer.parseInt(req.params("id"))));
            map.put("vastaukset", vastausDao.findAllVastaukset(kysymysId));

            return new ModelAndView(map, "kysymys");
        }, new ThymeleafTemplateEngine());
        
        post("/kysymykset/:id", (req, res) -> {
            String vastaus = req.queryParams("name");
            Integer vastausId = Integer.parseInt(req.params(":id"));
            Integer kysymysId = Integer.parseInt(req.queryParams("kysymysId"));
            
            System.out.println("Lisätään " + vastaus);
            
            vastausDao.saveOrUpdate(new Vastaus(req.queryParams("vastausteksti"), Boolean.getBoolean(vastaus), kysymysId));
            
            res.redirect("/kysymykset/:id");
            return "";
        });
    }
}

