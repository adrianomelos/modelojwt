package com.modelo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.modelo.entities.UF;
import com.modelo.repository.UFRepository;

@Service
public class UFService {
	
	@Autowired
	private UFRepository ufRepository;
	
	RestTemplate c = new RestTemplate();	
	
	public UF gravarUfLocal() {
		List<UF> list = ufRepository.findAll();
		if(list.size() < 27) {
			list.removeAll(list);
			
			String url = "https://servicodados.ibge.gov.br/api/v1/localidades/estados";	
			UF[] responseEntity = c.getForObject(url, UF[].class);
			
			for (UF u : responseEntity){
				UF f = new UF();
				f.setNome(u.getNome());
				f.setSigla(u.getSigla());
				f.setIdIbge(u.getId());
				ufRepository.save(f);
			}
		}
		
		return null;
	}

	public List<UF> findAllUf() {
		return ufRepository.findAll();
	}

	public Optional<UF> findByUfId(Long idUf) {
		return ufRepository.findById(idUf);
	}

}
