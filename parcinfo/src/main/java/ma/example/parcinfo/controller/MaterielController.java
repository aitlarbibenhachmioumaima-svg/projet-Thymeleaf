package ma.example.parcinfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ma.example.parcinfo.entity.Materiel;
import ma.example.parcinfo.repository.MaterielRepository;

@Controller
public class MaterielController {

    @Autowired
    private MaterielRepository materielRepository;

    // LIST + FILTRAGE
    @GetMapping("/materiels")
    public String list(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String etat,
            Model model) {

        if (type == null) type = "";
        if (etat == null) etat = "";

        model.addAttribute("materiels",
                materielRepository.findByTypeContainingAndEtatContaining(type, etat));

        return "index-materiel";
    }

    // FORM ADD
    @GetMapping("/materiel/add")
    public String form(Model model) {
        model.addAttribute("materiel", new Materiel());
        return "add-materiel";
    }

    // SAVE
    @PostMapping("/materiel/save")
    public String save(Materiel materiel) {
        materielRepository.save(materiel);
        return "redirect:/materiels";
    }

    // DELETE
    @GetMapping("/materiel/delete/{id}")
    public String delete(@PathVariable Long id) {
        materielRepository.deleteById(id);
        return "redirect:/materiels";
    }

    // EDIT
    @GetMapping("/materiel/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Materiel materiel = materielRepository.findById(id).orElse(null);
        model.addAttribute("materiel", materiel);
        return "add-materiel";
    }
}