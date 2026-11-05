package br.com.fechadura.service;

import br.com.fechadura.model.Acesso;
import br.com.fechadura.repository.AcessoRepository;
import org.springframework.stereotype.Service;

@Service
public class AcessoService {
    private final AcessoRepository repository;
    public AcessoService(AcessoRepository repository) { this.repository = repository; }
    public void registrar(String usuario, String resultado, String detalhe) {
        repository.save(new Acesso(usuario, resultado, detalhe));
    }
    public java.util.List<Acesso> listar() { return repository.findAllByOrderByDataHoraDesc(); }
}
