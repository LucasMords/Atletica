package br.ifto.atletica.Controller;

import br.ifto.atletica.Entity.Atleta;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Transactional
@Controller
@RequestMapping("atleta")
public class AtletaController {

    private final AtletaRepository atletaRepository;
    private final ModalidadeRepository modalidadeRepository;

    public AtletaController(AtletaRepository atletaRepository,
                            ModalidadeRepository modalidadeRepository) {
        this.atletaRepository = atletaRepository;
        this.modalidadeRepository = modalidadeRepository;
    }

    @GetMapping("/form")
    public String form(Model model) {
        adicionarModalidade(model);
        model.addAttribute("atleta", new Atleta());
        return "atleta/form";
    }

    @GetMapping("/list")
    public String listar(@RequestParam(required = false) Long modalidadeId, Model model) {
        Modalidade modalidadeSelecionada = null;
        List<Atleta> atletas;

        if (modalidadeId == null) {
            atletas = atletaRepository.findAllByOrderByNomeAsc();
        } else {
            modalidadeSelecionada = modalidadeRepository.findById(modalidadeId).orElseThrow();
            atletas = atletaRepository.findByModalidade_IdOrderByNomeAsc(modalidadeId);
        }

        prepararLista(model, atletas, modalidadeSelecionada);
        return "atleta/list";
    }

    @PostMapping("/save")
    public String save(Atleta atleta){
        atletaRepository.save(atleta);
        return "redirect:/atleta/list";
    }

    @PostMapping("/remove/{id}")
    public String remove(@PathVariable Long id){
        atletaRepository.deleteById(id);
        return "redirect:/atleta/list";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        adicionarModalidade(model);
        Atleta atleta = atletaRepository.findById(id).orElseThrow();
        model.addAttribute("atleta",atleta);
        return "atleta/form";
    }

    @PostMapping("/update")
    public String update(Atleta atleta) {
        Atleta atletaBd = atletaRepository.findById(atleta.getId())
                .orElseThrow();

        atletaBd.setNome(atleta.getNome());
        atletaBd.setMatricula(atleta.getMatricula());
        atletaBd.setDataNascimento(atleta.getDataNascimento());
        atletaBd.setModalidade(atleta.getModalidade());
        atletaBd.setCelular(atleta.getCelular());

        atletaRepository.save(atletaBd);
        return "redirect:/atleta/list";
    }

    private void prepararLista(Model model, List<Atleta> atletas, Modalidade modalidadeSelecionada) {
        model.addAttribute("atletas", atletas);
        model.addAttribute("modalidades", modalidadeRepository.findAll());
        model.addAttribute("modalidadeSelecionada", modalidadeSelecionada);
        model.addAttribute("filtroAtivo", modalidadeSelecionada != null);
        model.addAttribute("totalAtletas", atletaRepository.count());
        model.addAttribute("totalModalidades", modalidadeRepository.count());
    }

    private void adicionarModalidade(Model model){
        model.addAttribute("modalidades", modalidadeRepository.findAll());
    }


}
