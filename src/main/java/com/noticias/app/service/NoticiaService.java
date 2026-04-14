package com.noticias.app.service;

import com.noticias.app.client.NewsApiClient;
import com.noticias.app.model.Noticia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticiaService {

    @Autowired
    private NewsApiClient client;

    public List<Noticia> listarNoticias(String pais) {
        return client.obtenerNoticias(pais);
    }
}   
