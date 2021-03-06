package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bll.builder.PacientBuilder;
import bll.builder.PacientDTOBuilder;
import bll.dto.AnalysisDTO;
import bll.dto.PacientDTO;
import dal.model.Analysis;
import dal.model.Pacient;
import dal.repositories.PacientRepository;
import dal.repositories.RepositoryFactory;

@Service
public class PacientBLL {
	@Autowired // not null
	private RepositoryFactory rrepo;
	
	public PacientDTO getById(int id) {
		PacientRepository prepo = (PacientRepository) rrepo.getRepository("Pacient");
		Pacient p = prepo.findById(id).get();
		PacientDTO pdto = new PacientDTOBuilder()
				.name(p.getName())
				.password(p.getPassword())
				.email(p.getEmail())
				.address(p.getAddress())
				.analysis(p.getAnalysis().stream().map(AnalysisDTO::new).collect(Collectors.toList()))
				.telephone(p.getTelephone())
				.build();
		return pdto;
	}
	
	public PacientDTO getByName(String name) {
		PacientRepository prepo = (PacientRepository) rrepo.getRepository("Pacient");
		Pacient p = prepo.findByName(name);
		PacientDTO pdto = new PacientDTOBuilder()
				.name(p.getName())
				.password(p.getPassword())
				.email(p.getEmail())
				.address(p.getAddress())
				.analysis(p.getAnalysis().stream().map(AnalysisDTO::new).collect(Collectors.toList()))
				.telephone(p.getTelephone())
				.build();
		return pdto;
	}

	public boolean login(String name, String pass) {
		PacientRepository prepo = (PacientRepository) rrepo.getRepository("Pacient");
		Pacient p = prepo.findByNameAndPassword(name, pass);
		if (p == null) {
			return false;
		}
		return true;
	}
	
	public void insert(PacientDTO p) {
		//Pacient pacient = prepo.findByName(p.getName());
		PacientRepository prepo = (PacientRepository) rrepo.getRepository("Pacient");
		Pacient pacient = new PacientBuilder()
				.name(p.getName())
				.password(p.getPassword())
				.email(p.getEmail())
				.address(p.getAddress())
				.analysis(new ArrayList<Analysis>())
				.telephone(p.getTelephone())
				.build();
		prepo.save(pacient);
	}
	
	public void update(String name, PacientDTO p) {
		PacientRepository prepo = (PacientRepository) rrepo.getRepository("Pacient");
		Pacient old = prepo.findByName(name);
		old.setAddress(p.getAddress());
		old.setEmail(p.getEmail());
		old.setPassword(p.getPassword());
		old.setTelephone(p.getTelephone());
		prepo.save(old);
	}
	
	public void delete(PacientDTO p) {
		PacientRepository prepo = (PacientRepository) rrepo.getRepository("Pacient");
		Pacient pacient = prepo.findByName(p.getName());
		prepo.delete(pacient);
	}
	
	public List<PacientDTO> findAll(){
		PacientRepository prepo = (PacientRepository) rrepo.getRepository("Pacient");
		return prepo.findAll().stream().map(PacientDTO::new).collect(Collectors.toList());
	}
}
