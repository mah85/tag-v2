package br.com.fechadura.controller;

import br.com.fechadura.model.Usuario;
import br.com.fechadura.service.UsuarioService;
import br.com.fechadura.service.AcessoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdministracaoController {
    private final UsuarioService usuarios;
    private final AcessoService acessos;
    public AdministracaoController(UsuarioService usuarios, AcessoService acessos) {
        this.usuarios = usuarios; this.acessos = acessos;
    }
    private boolean admin(HttpSession s) {
        Usuario u = (Usuario) s.getAttribute("usuario");
        return u != null && "ADMIN".equals(u.getPerfil());
    }
    @GetMapping("/usuarios")
    public String usuarios(HttpSession session, Model model) {
        if (!admin(session)) return "redirect:/painel";
        model.addAttribute("usuarios", usuarios.listar());
        return "usuarios";
    }
    @PostMapping("/usuarios")
    public String cadastrar(@RequestParam String nome, @RequestParam String login,
                            @RequestParam String senha, @RequestParam String perfil,
                            HttpSession session, Model model) {
        if (!admin(session)) return "redirect:/painel";
        try { usuarios.cadastrar(nome, login, senha, perfil); }
        catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("usuarios", usuarios.listar());
            return "usuarios";
        }
        acessos.registrar(((Usuario)session.getAttribute("usuario")).getNomeUsuario(),
                "ADMINISTRAÇÃO", "Cadastro do usuário " + login);
        return "redirect:/usuarios";
    }
    @PostMapping("/usuarios/{id}/status")
    public String status(@PathVariable Long id, @RequestParam boolean ativo, HttpSession session) {
        if (!admin(session)) return "redirect:/painel";
        usuarios.alterarAtivo(id, ativo);
        return "redirect:/usuarios";
    }
    @PostMapping("/usuarios/{id}/excluir")
    public String excluir(@PathVariable Long id, HttpSession session) {
        if (!admin(session)) return "redirect:/painel";
        try { usuarios.excluir(id); } catch (IllegalArgumentException ignored) {}
        return "redirect:/usuarios";
    }
    @GetMapping("/registros")
    public String registros(HttpSession session, Model model) {
        if (!admin(session)) return "redirect:/painel";
        model.addAttribute("registros", acessos.listar());
        return "registros";
    }
}
