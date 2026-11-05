package br.com.fechadura.controller;

import br.com.fechadura.model.Usuario;
import br.com.fechadura.service.AcessoService;
import br.com.fechadura.service.FechaduraService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PainelController {
    private final FechaduraService fechadura;
    private final AcessoService acessos;
    public PainelController(FechaduraService fechadura, AcessoService acessos) {
        this.fechadura = fechadura; this.acessos = acessos;
    }
    private Usuario usuario(HttpSession session) { return (Usuario) session.getAttribute("usuario"); }

    @GetMapping("/painel")
    public String painel(HttpSession session, Model model) {
        Usuario u = usuario(session);
        if (u == null) return "redirect:/login";
        model.addAttribute("usuario", u);
        boolean bloqueada = fechadura.isBloqueada();
        model.addAttribute("bloqueada", bloqueada);
        model.addAttribute("orientacaoEstado", bloqueada
                ? "A fechadura está protegida. Desbloqueie somente se o acesso for autorizado."
                : "A fechadura está liberada. Lembre-se de bloqueá-la após concluir o acesso.");
        model.addAttribute("registros", acessos.listar().stream().limit(5).toList());
        return "painel";
    }
    @PostMapping("/fechadura/desbloquear")
    public String desbloquear(HttpSession session) {
        Usuario u = usuario(session);
        if (u == null) return "redirect:/login";
        fechadura.desbloquear();
        acessos.registrar(u.getNomeUsuario(), "AUTORIZADO", "Fechadura simulada desbloqueada");
        return "redirect:/painel";
    }
    @PostMapping("/fechadura/bloquear")
    public String bloquear(HttpSession session) {
        Usuario u = usuario(session);
        if (u == null) return "redirect:/login";
        fechadura.bloquear();
        acessos.registrar(u.getNomeUsuario(), "AUTORIZADO", "Fechadura simulada bloqueada");
        return "redirect:/painel";
    }
}
