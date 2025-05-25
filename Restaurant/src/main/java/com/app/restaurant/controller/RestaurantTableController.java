package com.app.restaurant.controller;

import com.app.restaurant.model.RestaurantTable;
import com.app.restaurant.service.RestaurantTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/manager")
public class RestaurantTableController {

    @Autowired
    private RestaurantTableService tableService;

    // LIST
    @GetMapping("/tables")
    public String listTables(Model model) {
        List<RestaurantTable> tables = tableService.getAllTables();
        model.addAttribute("tables", tables);
        return "manager/tables";
    }

    // SHOW CREATE FORM
    @GetMapping("/table/new")
    public String showCreateForm(Model model) {
        model.addAttribute("table", new RestaurantTable());
        return "manager/addTables";
    }

    // HANDLE CREATE
    @PostMapping("/table")
    public String createTable(@ModelAttribute("table") RestaurantTable table) {
        tableService.createTable(table);
        return "redirect:/manager/tables";
    }

    // SHOW EDIT FORM
    @GetMapping("/table/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        RestaurantTable table = tableService.getTableById(id);
        model.addAttribute("table", table);
        return "manager/addTables";
    }

    // HANDLE UPDATE
    @PostMapping("/table/{id}")
    public String updateTable(
            @PathVariable Long id,
            @ModelAttribute("table") RestaurantTable tableDetails) {
        tableService.updateTable(id, tableDetails);
        return "redirect:/manager/tables";
    }

    // DELETE
    @GetMapping("/table/{id}/delete")
    public String deleteTable(@PathVariable Long id) {
        tableService.deleteTable(id);
        return "redirect:/manager/tables";
    }
}
