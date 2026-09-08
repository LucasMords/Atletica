package br.ifto.atletica.Controller;

import br.ifto.atletica.Entity.Modalidade;
import br.ifto.atletica.Repository.AtletaRepository;
import br.ifto.atletica.Repository.ModalidadeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Transactional
@Controller
@RequestMapping("modalidade")
public class ModalidadeController {

    private ModalidadeRepository modalidadeRepository;

    public ModalidadeController(ModalidadeRepository modalidadeRepository) {
        this.modalidadeRepository = modalidadeRepository;
    }

    @GetMapping("/form")
    public String form(Model model){
        model.addAttribute("modalidade", new Modalidade());
        return "modalidade/form";
    }

    @GetMapping("/list")
    public String list(Model model){
        model.addAttribute("modalidades", modalidadeRepository.findAll());
        return "modalidade/list";
    }

    @PostMapping("/save")
    public String save(Modalidade modalidade){
        modalidadeRepository.save(modalidade);
        return "redirect:/modalidade/list";
    }

    @PostMapping("/remove/{id}")
    public String remove(@PathVariable Long id){
        modalidadeRepository.deleteById(id);
        return "redirect:/modalidade/list";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id,Model model){
        Modalidade modalidade = modalidadeRepository.findById(id).orElseThrow();
        model.addAttribute("modalidade", modalidade);
        return "modalidade/form";
    }

    @PostMapping("/updade")
    public String update(Modalidade modalidade){
        Modalidade modalidadeBd = modalidadeRepository.findById(modalidade.getId()).orElseThrow();

        modalidadeBd.setNome(modalidade.getNome());

        return "redirect:/modalidade/list";
    }


}
