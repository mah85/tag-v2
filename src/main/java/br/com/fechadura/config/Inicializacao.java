package br.com.fechadura.config;

import br.com.fechadura.repository.UsuarioRepository;
import br.com.fechadura.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Inicializacao {
    @Bean
    CommandLineRunner carregarUsuarios(UsuarioService service, UsuarioRepository repository) {
        return args -> service.inicializarDados(repository);
    }
}
