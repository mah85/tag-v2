package br.com.fechadura.controller;

import br.com.fechadura.model.Usuario;
import br.com.fechadura.service.AcessoService;
import br.com.fechadura.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {
    private final UsuarioService usuarios;
    private final AcessoService acessos;
    public LoginController(UsuarioService usuarios, AcessoService acessos) {
        this.usuarios = usuarios; this.acessos = acessos;
    }
    @GetMapping("/")
    public String inicio(HttpSession session) {
        return session.getAttribute("usuario") == null ? "redirect:/login" : "redirect:/painel";
    }
    @GetMapping("/login")
    public String login() { return "login"; }

    @PostMapping("/login")
    public String autenticar(@RequestParam String login, @RequestParam String senha, HttpSession session, Model model) {
        var usuario = usuarios.autenticar(login, senha);
        if (usuario.isPresent()) {
            session.setAttribute("usuario", usuario.get());
            acessos.registrar(login, "AUTORIZADO", "Autenticação realizada com sucesso");
            return "redirect:/painel";
        }
        acessos.registrar(login, "NEGADO", "Credenciais inválidas ou usuário inativo");
        model.addAttribute("erro", "Usuário ou senha inválidos.");
        return "login";
    }
    @PostMapping("/sair")
    public String sair(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
