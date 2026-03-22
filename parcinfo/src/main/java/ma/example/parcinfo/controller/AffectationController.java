package ma.example.parcinfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ma.example.parcinfo.entity.Affectation;
import ma.example.parcinfo.repository.AffectationRepository;
import ma.example.parcinfo.repository.MaterielRepository;
import ma.example.parcinfo.repository.EmployeRepository;

import java.util.List;

@Controller
public class AffectationController {

    @Autowired
    private AffectationRepository affectationRepository;

    @Autowired
    private MaterielRepository materielRepository;

    @Autowired
    private EmployeRepository employeRepository;

    // LIST + FILTER
    @GetMapping("/affectations")
    public String list(
            @RequestParam(required = false) String statut,
            Model model) {

        if (statut == null) statut = "";

        model.addAttribute("affectations",
                affectationRepository.findByStatutContaining(statut));

        return "index-affectation";
    }

    // FORM ADD
    @GetMapping("/affectation/add")
    public String form(Model model) {
        model.addAttribute("affectation", new Affectation());
        model.addAttribute("materiels", materielRepository.findAll());
        model.addAttribute("employes", employeRepository.findAll());
        return "add-affectation";
    }

    // SAVE
    @PostMapping("/affectation/save")
    public String save(
            @ModelAttribute Affectation affectation,
            @RequestParam Long materiel,
            @RequestParam Long employe) {

        // récupérer les objets depuis la base
        affectation.setMateriel(materielRepository.findById(materiel).orElse(null));
        affectation.setEmploye(employeRepository.findById(employe).orElse(null));

        affectationRepository.save(affectation);
        return "redirect:/affectations";
    }

    // DELETE
    @GetMapping("/affectation/delete/{id}")
    public String delete(@PathVariable Long id) {
        affectationRepository.deleteById(id);
        return "redirect:/affectations";
    }

    @GetMapping("/stats")
    public String stats(Model model) {

        // Taux d'utilisation du matériel
        long totalMateriel = materielRepository.count();
        long activeAffectations = affectationRepository.countActive();
        double tauxUtilisation = (totalMateriel == 0) ? 0 : (activeAffectations * 100.0 / totalMateriel);

        // Nombre de matériels par état
        List<Object[]> panneParEtat = materielRepository.countByEtat();

        model.addAttribute("tauxUtilisation", tauxUtilisation);
        model.addAttribute("panneParEtat", panneParEtat);

        return "stats";
    }
}