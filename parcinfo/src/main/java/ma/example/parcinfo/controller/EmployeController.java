package ma.example.parcinfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ma.example.parcinfo.entity.Employe;
import ma.example.parcinfo.repository.EmployeRepository;

@Controller
public class EmployeController {

    @Autowired
    private EmployeRepository employeRepository;

    // LIST
    @GetMapping("/employes")
    public String list(Model model) {
        model.addAttribute("employes", employeRepository.findAll());
        return "index-employe";
    }

    // FORM ADD
    @GetMapping("/employe/add")
    public String form(Model model) {
        model.addAttribute("employe", new Employe());
        return "add-employe";
    }

    // SAVE
    @PostMapping("/employe/save")
    public String save(Employe employe) {
        employeRepository.save(employe);
        return "redirect:/employes";
    }

    // DELETE
    @GetMapping("/employe/delete/{id}")
    public String delete(@PathVariable Long id) {
        employeRepository.deleteById(id);
        return "redirect:/employes";
    }

    // EDIT
    @GetMapping("/employe/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Employe employe = employeRepository.findById(id).orElse(null);
        model.addAttribute("employe", employe);
        return "add-employe";
    }
}