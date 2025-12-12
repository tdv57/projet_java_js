package com.ensta.myfilmlist;
import com.ensta.myfilmlist.service.MyFilmsService;
import com.ensta.myfilmlist.service.impl.MyFilmsServiceImpl;
import com.ensta.myfilmlist.model.*;

import java.rmi.server.ServerCloneException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ensta.myfilmlist.exception.*;
import com.ensta.myfilmlist.dto.*;
import com.ensta.myfilmlist.form.*;
import com.ensta.myfilmlist.mapper.FilmMapper;
import com.ensta.myfilmlist.mapper.RealisateurMapper;
import com.ensta.myfilmlist.service.impl.*;

/**
 * Classe de tests du service MyFilmsServiceImpl.
 */
@Component
public class MyfilmlistTests{

	/** Initialisation du service pour les traitements de l'application MyFilms */
	@Autowired
	private MyFilmsService myFilmsService;

	/**
	 * Permet de tester la mise a jour du statut "celebre" d'un RealisateurDTO en fonction du nombre de films realises.
	 */
	public void updateRealisateurCelebreTest(){
		// Creation des Realisateurs
		try {
			Realisateur jamesCameron = myFilmsService.findRealisateurByNomAndPrenom("Cameron", "James");
			jamesCameron.setCelebre(false);
			// Realisateur jamesCameron = new Realisateur();
			// jamesCameron.setNom("Cameron");
			// jamesCameron.setPrenom("James");
			// jamesCameron.setDateNaissance(LocalDate.of(1954, 8, 16));

			Realisateur peterJackson = myFilmsService.findRealisateurByNomAndPrenom("Jackson", "Peter");
			peterJackson.setCelebre(false);
			// Realisateur peterJackson = new Realisateur();
			// peterJackson.setNom("Jackson");
			// peterJackson.setPrenom("Peter");
			// peterJackson.setDateNaissance(LocalDate.of(1961, 10, 31));
			// peterJackson = RealisateurMapper.convertRealisateurDTOToRealisateur(myFilmsService.createRealisateur(peterJackson));
			// Creation des films

			FilmForm avatarForm = new FilmForm();
			avatarForm.setTitre("Avatar");
			avatarForm.setDuree(162);
			avatarForm.setRealisateurId(jamesCameron.getId());
			Film avatar = FilmMapper.convertFilmFormToFilm(avatarForm);		// Affectation des films aux realisateurs

			FilmForm laCommunauteDeLAnneauForm = new FilmForm();
			laCommunauteDeLAnneauForm.setTitre("La communauté de l'anneau");
			laCommunauteDeLAnneauForm.setDuree(178);
			laCommunauteDeLAnneauForm.setRealisateurId(peterJackson.getId());
			Film laCommunauteDeLAnneau = FilmMapper.convertFilmFormToFilm(laCommunauteDeLAnneauForm);		// Affectation des films aux realisateurs

			FilmForm lesDeuxToursForm = new FilmForm();
			lesDeuxToursForm.setTitre("Les deux tours");
			lesDeuxToursForm.setDuree(179);
			lesDeuxToursForm.setRealisateurId(peterJackson.getId());
			Film lesDeuxTours = FilmMapper.convertFilmFormToFilm(lesDeuxToursForm);		// Affectation des films aux realisateurs

			FilmForm leRetourDuRoiForm = new FilmForm();
			leRetourDuRoiForm.setTitre("Le retour du roi");
			leRetourDuRoiForm.setDuree(201);
			leRetourDuRoiForm.setRealisateurId(peterJackson.getId());
			Film leRetourDuRoi = FilmMapper.convertFilmFormToFilm(leRetourDuRoiForm);		// Affectation des films aux realisateurs

			List<Film> peterJacksonFilms = new ArrayList<>();
			peterJacksonFilms.add(laCommunauteDeLAnneau);
			peterJacksonFilms.add(lesDeuxTours);
			peterJacksonFilms.add(leRetourDuRoi);
			peterJackson.setFilmRealises(peterJacksonFilms);

			List<Film> jamesCameronFilms = new ArrayList<>();
			jamesCameronFilms.add(avatar);
			jamesCameron.setFilmRealises(jamesCameronFilms);

		// Mise a jour du statut "celebre" des Realisateurs


			jamesCameron = myFilmsService.updateRealisateurCelebre(jamesCameron);
			peterJackson = myFilmsService.updateRealisateurCelebre(peterJackson);

			// Attendue : false
			System.out.println("James Cameron est-il celebre ? " + jamesCameron.isCelebre());
			// Attendue : true
			System.out.println("Peter Jackson est-il celebre ? " + peterJackson.isCelebre());
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Permet de tester le calcul de la duree totale des films.
	 */
	public void calculerDureeTotaleTest() {
		// Creation des films

		Film laCommunauteDeLAnneau = new Film();
		laCommunauteDeLAnneau.setTitre("La communauté de l'anneau");
		laCommunauteDeLAnneau.setDuree(178);

		Film lesDeuxTours = new Film();
		lesDeuxTours.setTitre("Les deux tours");
		lesDeuxTours.setDuree(179);

		Film leRetourDuRoi = new Film();
		leRetourDuRoi.setTitre("Le retour du roi");
		leRetourDuRoi.setDuree(201);

		List<Film> leSeigneurDesAnneaux = new ArrayList<>();
		leSeigneurDesAnneaux.add(laCommunauteDeLAnneau);
		leSeigneurDesAnneaux.add(lesDeuxTours);
		leSeigneurDesAnneaux.add(leRetourDuRoi);

		// Calcule de la duree totale

		long dureeTotale = myFilmsService.calculerDureeTotale(leSeigneurDesAnneaux);
		// Attendue : 558 minutes
		System.out.println("La duree totale de la trilogie \"Le Seigneur des Anneaux\" est de : " + dureeTotale + " minutes");
	}

	/**
	 * Permet de tester le calcul de la note moyenne des films.
	 */
	public void calculerNoteMoyenneTest() {
		// Creation des notes

		double[] notes = { 18, 15.5, 12 };
		List<Double> notesList = Arrays.stream(notes) // crée un DoubleStream
										.boxed()       // convertit les doubles primitifs en objets Double
										.collect(Collectors.toList());

		// Calcul de la note moyenne

		Optional<Double> noteMoyenne = myFilmsService.calculerNoteMoyenne(notesList);

		// Attendue : 15,17
		System.out.println("La note moyenne est : " + noteMoyenne.toString());
	}

	public void updateRealisateursCelebresTest() {
		try {
			Realisateur jamesCameron = myFilmsService.findRealisateurByNomAndPrenom("Cameron", "James");
			jamesCameron.setCelebre(false);
			// jamesCameron = RealisateurMapper.convertRealisateurDTOToRealisateur(myFilmsService.createRealisateur(jamesCameron));

			Realisateur peterJackson = myFilmsService.findRealisateurByNomAndPrenom("Jackson", "Peter");
			peterJackson.setCelebre(false);
			// peterJackson.setNom("Jackson");
			// peterJackson.setPrenom("Peter");
			// peterJackson.setDateNaissance(LocalDate.of(1961, 10, 31));
			// peterJackson = RealisateurMapper.convertRealisateurDTOToRealisateur(myFilmsService.createRealisateur(peterJackson));

			// Creation des films

			FilmForm avatarForm = new FilmForm();
			avatarForm.setTitre("Avatar");
			avatarForm.setDuree(162);
			avatarForm.setRealisateurId(jamesCameron.getId());
			Film avatar = FilmMapper.convertFilmFormToFilm(avatarForm);
			// Film avatar = FilmMapper.convertFilmDTOToFilm(myFilmsService.createFilm(avatarForm));

			FilmForm laCommunauteDeLAnneauForm = new FilmForm();
			laCommunauteDeLAnneauForm.setTitre("La communauté de l'anneau");
			laCommunauteDeLAnneauForm.setDuree(178);
			laCommunauteDeLAnneauForm.setRealisateurId(peterJackson.getId());
			Film laCommunauteDeLAnneau = FilmMapper.convertFilmFormToFilm(laCommunauteDeLAnneauForm);
			// Film laCommunauteDeLAnneau = FilmMapper.convertFilmDTOToFilm(myFilmsService.createFilm(laCommunauteDeLAnneauForm));

			FilmForm lesDeuxToursForm = new FilmForm();
			lesDeuxToursForm.setTitre("Les deux tours");
			lesDeuxToursForm.setDuree(179);
			lesDeuxToursForm.setRealisateurId(peterJackson.getId());
			Film lesDeuxTours = FilmMapper.convertFilmFormToFilm(lesDeuxToursForm);
			// Film lesDeuxTours = FilmMapper.convertFilmDTOToFilm(myFilmsService.createFilm(lesDeuxToursForm));

			FilmForm leRetourDuRoiForm = new FilmForm();
			leRetourDuRoiForm.setTitre("Le retour du roi");
			leRetourDuRoiForm.setDuree(201);
			leRetourDuRoiForm.setRealisateurId(peterJackson.getId());
			Film leRetourDuRoi = FilmMapper.convertFilmFormToFilm(leRetourDuRoiForm);
			// Film leRetourDuRoi = FilmMapper.convertFilmDTOToFilm(myFilmsService.createFilm(leRetourDuRoiForm));

			// Affectation des films aux realisateurs

			List<Film> peterJacksonFilms = new ArrayList<>();
			peterJacksonFilms.add(laCommunauteDeLAnneau);
			peterJacksonFilms.add(lesDeuxTours);
			peterJacksonFilms.add(leRetourDuRoi);
			peterJackson.setFilmRealises(peterJacksonFilms);

			List<Film> jamesCameronFilms = new ArrayList<>();
			jamesCameronFilms.add(avatar);
			jamesCameron.setFilmRealises(jamesCameronFilms);

			// Mise a jour du statut "celebre" des Realisateurs
			List<Realisateur> realisateurs = new ArrayList<>();
			realisateurs.add(jamesCameron);
			realisateurs.add(peterJackson);

			List <Realisateur> realisateursCelebres = myFilmsService.updateRealisateurCelebres(realisateurs);
			System.out.println(realisateursCelebres.size());
			for(Realisateur realisateurCelebre : realisateursCelebres) {
				System.out.println(realisateurCelebre.getPrenom() + realisateurCelebre.getNom() + " est célèbre");
			}
			// Attendue : false
			// Attendue : true
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Permet de tester la recuperation des films.
	 */
	public void findAllFilmsTest() {
		try {
			List<Film> films = myFilmsService.findAll();

			// Attendue : 4
			System.out.println("Combien y a-t-il de films ? " + films.size());

			films.forEach(System.out::println);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Permet de tester la creation des films.
	 */
	public void createFilmTest() {
		try {
			Realisateur realisateur = myFilmsService.findRealisateurByNomAndPrenom("Cameron", "James");
			RealisateurDTO realisateurDTO = RealisateurMapper.convertRealisateurToRealisateurDTO(realisateur);
			FilmForm titanic = new FilmForm();
			titanic.setTitre("Titanic");
			titanic.setDuree(195);
			titanic.setRealisateurId(realisateurDTO.getId());

			FilmDTO newFilm = myFilmsService.createFilm(titanic);

			System.out.println("Le nouveau film 'Titanic' possede l'id : " + newFilm.getId());

			List<Film> films = myFilmsService.findAll();

			// Attendue : 5
			System.out.println("Combien y a-t-il de films ? " + films.size());

			films.forEach(f -> System.out.println("Le realisateur du film : '" + f.getTitre() + "' est : " + f.getRealisateur()));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Permet de tester la recuperation d'un film par son identifiant.
	 */
	public void findFilmByIdTest() {
		try {
			FilmDTO avatar = FilmMapper.convertFilmToFilmDTO(myFilmsService.findFilmById(1));
			System.out.println("Le film avec l'identifiant 1 est : " + avatar);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Permet de tester la suppression d'un film avec son identifiant.
	 */
	public void deleteFilmByIdTest() {
		try {
			FilmDTO filmDTO = FilmMapper.convertFilmToFilmDTO(myFilmsService.findFilmById(5));
			System.out.println("Le film avec l'identifiant 5 est : " + filmDTO);
			myFilmsService.deleteFilm(5);
			filmDTO = FilmMapper.convertFilmToFilmDTO(myFilmsService.findFilmById(5));

			System.out.println("Suppression du film avec l'identifiant 5...");
			System.out.println("Le film avec l'identifiant 5 est : " + filmDTO);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Permet de tester la mise a jour du statut celebre d'un Realisateur.
	 */
	public void updateRealisateurCelebre() {
		try {
			RealisateurDTO realisateurDTO = RealisateurMapper.convertRealisateurToRealisateurDTO(myFilmsService.findRealisateurByNomAndPrenom("Cameron", "James"));
			// Attendue : false
			System.out.println("James Cameron est-il celebre ? " + realisateurDTO.isCelebre());

			FilmForm titanic = new FilmForm();
			titanic.setTitre("Titanic");
			titanic.setDuree(195);
			titanic.setRealisateurId(realisateurDTO.getId());

			FilmForm leHobbit = new FilmForm();
			leHobbit.setTitre("Le Hobbit : Un voyage inattendu");
			leHobbit.setDuree(169);
			leHobbit.setRealisateurId(realisateurDTO.getId());

			myFilmsService.createFilm(titanic);
			FilmDTO leHobbitDTO = myFilmsService.createFilm(leHobbit);

			System.out.println("James Cameron a realise deux nouveaux films");
			realisateurDTO = RealisateurMapper.convertRealisateurToRealisateurDTO(myFilmsService.findRealisateurByNomAndPrenom("Cameron", "James"));

			// Attendue : true
			System.out.println("James Cameron est-il celebre ? " + realisateurDTO.isCelebre());

			myFilmsService.deleteFilm(leHobbitDTO.getId());
			System.out.println("Ce n'est pas James Cameron qui a realise le Hobbit, suppression du film !");
			realisateurDTO = RealisateurMapper.convertRealisateurToRealisateurDTO(myFilmsService.findRealisateurByNomAndPrenom("Cameron", "James"));

			// Attendue : false
			System.out.println("James Cameron est-il celebre ? " + realisateurDTO.isCelebre());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
}
