package com.BetterTfrrsWebsite;

import DB.DB;
import DBObjects.Team;
import DBTables.TeamsTable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@Controller
public class IndexController {
    DB db = new DB();
    TeamsTable teamsTable = new TeamsTable(db);
    @GetMapping(value = "/")
    public String index(Model model) throws SQLException {
        return "index";
    }
}
