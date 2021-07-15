package com.modelo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.modelo.entities.City;
import com.modelo.entities.UF;
import com.modelo.repository.CityRepository;
import com.modelo.repository.UFRepository;

@Service
public class CityService {

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private UFRepository ufRepository;

	RestTemplate c = new RestTemplate();

	public City gravarUfLocal() {
		
		List<City> listCity = cityRepository.findAll();
		if (listCity.size() < 5000) {
			cityRepository.deleteAll(listCity);
			
			List<UF> listUF = ufRepository.findAll();

			for (UF u : listUF) {
				String url = "https://servicodados.ibge.gov.br/api/v1/localidades/estados/" + u.getIdIbge() + "/municipios";
				City[] responseEntity = c.getForObject(url, City[].class);

				for (City city : responseEntity) {
					City c = new City();
					c.setNome(city.getNome());
					c.setUf(new UF(u.getId(), null, null, null, null));
					cityRepository.save(c);
				}
			}
		}
		return null;
	}

	public List<City> findByCityUF(Long uf) {
		return cityRepository.findByUfId(uf);
	}

	public Optional<City> findByCityId(Long id) {
		return cityRepository.findById(id);
	}

}
