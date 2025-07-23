package com.esempio.prenotazioni.controller;

import java.util.List;
import java.util.Optional;

import com.esempio.prenotazioni.model.Prenotazione;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esempio.prenotazioni.repository.PrenotazioneRepository;

@RestController
@RequestMapping("/api/prenotazioni")
@CrossOrigin(origins = "*")
public class PrenotazioneController {

	@Autowired
	private PrenotazioneRepository repo;
	
	@GetMapping
	public List<Prenotazione> getAll() {
		return repo.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<Prenotazione> GetOne(@PathVariable Long id){
		return repo.findById(id);
	}
	
	@PostMapping
	public Prenotazione create(@RequestBody Prenotazione p) {
		return repo.save(p);
	}
	
	@PutMapping("/{id}")
	public Prenotazione update(@PathVariable Long id, @RequestBody Prenotazione p) {
		p.setId(id);
		return repo.save(p);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		repo.deleteById(id);
	}	
	
	@GetMapping("/ordinamento/giorno")
	public List<Prenotazione> getAllByGiorno() {
		return repo.findAllByOrderByGiornoAscOraAsc();
	}
	
	@GetMapping("/ordinamento/nome")
	public List<Prenotazione> getAllByNome() {
		return repo.findAllByOrderByNomeAsc();
	}
}
