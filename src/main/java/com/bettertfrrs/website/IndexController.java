//package com.bettertfrrs.website;
//
//import com.bettertfrrs.db.DB;
//import com.bettertfrrs.db.dbtables.TeamsTable;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.sql.SQLException;
//
//@Controller
//public class IndexController {
//    DB db = new DB();
//    TeamsTable teamsTable = new TeamsTable(db);
//    @GetMapping(value = "/")
//    public String index(Model model) throws SQLException {
//        return "index";
//    }
//}
