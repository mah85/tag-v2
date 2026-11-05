package br.com.fechadura.service;

import org.springframework.stereotype.Service;

@Service
public class FechaduraService {
    private boolean bloqueada = true;

    public boolean isBloqueada() { return bloqueada; }
    public void desbloquear() { bloqueada = false; }
    public void bloquear() { bloqueada = true; }
}
