package br.com.fechadura.service;

import br.com.fechadura.model.Usuario;
import br.com.fechadura.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UsuarioService(UsuarioRepository repository) { this.repository = repository; }
    public Optional<Usuario> autenticar(String login, String senha) {
        return repository.findByNomeUsuario(login)
                .filter(Usuario::isAtivo)
                .filter(u -> encoder.matches(senha, u.getSenhaHash()));
    }
    public List<Usuario> listar() { return repository.findAll(); }
    public void cadastrar(String nome, String login, String senha, String perfil) {
        if (repository.existsByNomeUsuario(login)) throw new IllegalArgumentException("Esse usuário já existe.");
        if (senha == null || senha.length() < 6) throw new IllegalArgumentException("A senha deve ter pelo menos 6 caracteres.");
        repository.save(new Usuario(login, nome, encoder.encode(senha), perfil));
    }
    public void alterarAtivo(Long id, boolean ativo) {
        Usuario usuario = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
        usuario.setAtivo(ativo);
        repository.save(usuario);
    }
    public void excluir(Long id) {
        Usuario usuario = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
        if ("admin".equals(usuario.getNomeUsuario())) throw new IllegalArgumentException("A conta administrativa inicial não pode ser removida.");
        repository.delete(usuario);
    }
    public void inicializarDados(UsuarioRepository repo) {
        if (!repo.existsByNomeUsuario("admin"))
            repo.save(new Usuario("admin", "Administrador", encoder.encode("admin123"), "ADMIN"));
        if (!repo.existsByNomeUsuario("maria"))
            repo.save(new Usuario("maria", "Maria - Usuária de teste", encoder.encode("123456"), "USUARIO"));
    }
}
