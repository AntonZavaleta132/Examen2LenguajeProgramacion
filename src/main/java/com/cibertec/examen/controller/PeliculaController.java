package com.cibertec.examen.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cibertec.examen.model.Genero;
import com.cibertec.examen.model.Pelicula;
import com.cibertec.examen.repository.GeneroRepository;
import com.cibertec.examen.repository.PeliculaRepository;

@Controller
@RequestMapping("/peliculas")
public class PeliculaController {

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Autowired
    private GeneroRepository generoRepository;

    // 1. LISTAR PELÍCULAS
    @GetMapping({"", "/"})
    public String listarPeliculas(Model model) {
        List<Pelicula> listaPeliculas = peliculaRepository.findAll();
        model.addAttribute("peliculas", listaPeliculas);
        return "listar"; 
    }

 
    @GetMapping("/nuevo")
    public String mostrarFormularioDeRegistro(Model model) {
        Pelicula pelicula = new Pelicula();
        List<Genero> listaGeneros = generoRepository.findAll();
        
        model.addAttribute("pelicula", pelicula);
        model.addAttribute("generos", listaGeneros);
        return "formulario"; 
    }

   
    @PostMapping("/guardar")
    public String guardarPelicula(@ModelAttribute("pelicula") Pelicula pelicula) {
        peliculaRepository.save(pelicula);
        return "redirect:/peliculas";
    }


    @GetMapping("/editar/{id}")
    public String mostrarFormularioDeEditar(@PathVariable("id") Long id, Model model) {
        Optional<Pelicula> peliculaOpt = peliculaRepository.findById(id);
        
        if (peliculaOpt.isPresent()) {
            List<Genero> listaGeneros = generoRepository.findAll();
            model.addAttribute("pelicula", peliculaOpt.get());
            model.addAttribute("generos", listaGeneros);
            return "formulario"; 
        } else {
            return "redirect:/peliculas";
        }
    }

   
    @GetMapping("/eliminar/{id}")
    public String eliminarPelicula(@PathVariable("id") Long id) {
        if (peliculaRepository.existsById(id)) {
            peliculaRepository.deleteById(id);
        }
        return "redirect:/peliculas";
    }
}