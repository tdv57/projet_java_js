package com.ensta.myfilmlist;

import com.ensta.myfilmlist.dto.DirectorDTO;
import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.form.FilmForm;
import com.ensta.myfilmlist.mapper.DirectorMapper;
import com.ensta.myfilmlist.mapper.FilmMapper;
import com.ensta.myfilmlist.model.Director;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.service.MyFilmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Classe de tests du service MyFilmsServiceImpl.
 */
@Component
public class MyfilmlistTests {

    /**
     * Initialisation du service pour les traitements de l'application MyFilms
     */
    @Autowired
    private MyFilmsService myFilmsService;

	@Autowired
	private FilmMapper filmMapper;
	/**
	 * Permet de tester la mise a jour du statut "famous" d'un DirectorDTO en fonction du surnamebre de films realises.
	 */
	public void updateDirectorFamousTest(){
		// Creation des Directors
		try {
			Director jamesCameron = myFilmsService.findDirectorByNameAndSurname("Cameron", "James");
			jamesCameron.setFamous(false);
			// Director jamesCameron = new Director();
			// jamesCameron.setSurname("Cameron");
			// jamesCameron.setName("James");
			// jamesCameron.setBirthdate(LocalDate.of(1954, 8, 16));

			Director peterJackson = myFilmsService.findDirectorByNameAndSurname("Jackson", "Peter");
			peterJackson.setFamous(false);
			// Director peterJackson = new Director();
			// peterJackson.setSurname("Jackson");
			// peterJackson.setName("Peter");
			// peterJackson.setBirthdate(LocalDate.of(1961, 10, 31));
			// peterJackson = DirectorMapper.convertDirectorDTOToDirector(myFilmsService.createDirector(peterJackson));
			// Creation des films

            FilmForm avatarForm = new FilmForm();
            avatarForm.setTitle("Avatar");
            avatarForm.setDuration(162);
            avatarForm.setDirectorId(jamesCameron.getId());
            Film avatar = filmMapper.convertFilmFormToFilm(avatarForm);        // Affectation des films aux directors

            FilmForm laCommunauteDeLAnneauForm = new FilmForm();
            laCommunauteDeLAnneauForm.setTitle("La communauté de l'anneau");
            laCommunauteDeLAnneauForm.setDuration(178);
            laCommunauteDeLAnneauForm.setDirectorId(peterJackson.getId());
            Film laCommunauteDeLAnneau = filmMapper.convertFilmFormToFilm(laCommunauteDeLAnneauForm);        // Affectation des films aux directors

            FilmForm lesDeuxToursForm = new FilmForm();
            lesDeuxToursForm.setTitle("Les deux tours");
            lesDeuxToursForm.setDuration(179);
            lesDeuxToursForm.setDirectorId(peterJackson.getId());
            Film lesDeuxTours = filmMapper.convertFilmFormToFilm(lesDeuxToursForm);        // Affectation des films aux directors

            FilmForm leRetourDuRoiForm = new FilmForm();
            leRetourDuRoiForm.setTitle("Le retour du roi");
            leRetourDuRoiForm.setDuration(201);
            leRetourDuRoiForm.setDirectorId(peterJackson.getId());
            Film leRetourDuRoi = filmMapper.convertFilmFormToFilm(leRetourDuRoiForm);        // Affectation des films aux directors

            List<Film> peterJacksonFilms = new ArrayList<>();
            peterJacksonFilms.add(laCommunauteDeLAnneau);
            peterJacksonFilms.add(lesDeuxTours);
            peterJacksonFilms.add(leRetourDuRoi);
            peterJackson.setFilmsProduced(peterJacksonFilms);

            List<Film> jamesCameronFilms = new ArrayList<>();
            jamesCameronFilms.add(avatar);
            jamesCameron.setFilmsProduced(jamesCameronFilms);

            // Mise a jour du statut "famous" des Directors


            jamesCameron = myFilmsService.updateDirectorFamous(jamesCameron);
            peterJackson = myFilmsService.updateDirectorFamous(peterJackson);

            // Attendue : false
            System.out.println("James Cameron est-il famous ? " + jamesCameron.isFamous());
            // Attendue : true
            System.out.println("Peter Jackson est-il famous ? " + peterJackson.isFamous());
        } catch (ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Permet de tester le calcul de la duration totale des films.
     */
    public void calculerDurationTotaleTest() {
        // Creation des films

        Film laCommunauteDeLAnneau = new Film();
        laCommunauteDeLAnneau.setTitle("La communauté de l'anneau");
        laCommunauteDeLAnneau.setDuration(178);

        Film lesDeuxTours = new Film();
        lesDeuxTours.setTitle("Les deux tours");
        lesDeuxTours.setDuration(179);

        Film leRetourDuRoi = new Film();
        leRetourDuRoi.setTitle("Le retour du roi");
        leRetourDuRoi.setDuration(201);

        List<Film> leSeigneurDesAnneaux = new ArrayList<>();
        leSeigneurDesAnneaux.add(laCommunauteDeLAnneau);
        leSeigneurDesAnneaux.add(lesDeuxTours);
        leSeigneurDesAnneaux.add(leRetourDuRoi);

        // Calcul de la duration totale

        long durationTotale = myFilmsService.getFullWatchTime(leSeigneurDesAnneaux);
        // Attendue : 558 minutes
        System.out.println("La duration totale de la trilogie \"Le Seigneur des Anneaux\" est de : " + durationTotale + " minutes");
    }

    /**
     * Permet de tester le calcul de la note moyenne des films.
     */
    public void calculerNoteMoyenneTest() {
        // Creation des notes

        double[] notes = {18, 15.5, 12};
        List<Double> notesList = Arrays.stream(notes) // crée un DoubleStream
                .boxed()       // convertit les doubles primitifs en objets Double
                .collect(Collectors.toList());

        // Calcul de la note moyenne

        Optional<Double> noteMoyenne = myFilmsService.getMeanRating(notesList);

        // Attendue : 15,17
        System.out.println("La note moyenne est : " + noteMoyenne.toString());
    }

	public void updateDirectorsFamoussTest() {
		try {
			Director jamesCameron = myFilmsService.findDirectorByNameAndSurname("Cameron", "James");
			jamesCameron.setFamous(false);
			// jamesCameron = DirectorMapper.convertDirectorDTOToDirector(myFilmsService.createDirector(jamesCameron));

			Director peterJackson = myFilmsService.findDirectorByNameAndSurname("Jackson", "Peter");
			peterJackson.setFamous(false);
			// peterJackson.setSurname("Jackson");
			// peterJackson.setName("Peter");
			// peterJackson.setBirthdate(LocalDate.of(1961, 10, 31));
			// peterJackson = DirectorMapper.convertDirectorDTOToDirector(myFilmsService.createDirector(peterJackson));

            // Creation des films

            FilmForm avatarForm = new FilmForm();
            avatarForm.setTitle("Avatar");
            avatarForm.setDuration(162);
            avatarForm.setDirectorId(jamesCameron.getId());
            Film avatar = filmMapper.convertFilmFormToFilm(avatarForm);
            // Film avatar = FilmMapper.convertFilmDTOToFilm(myFilmsService.createFilm(avatarForm));

            FilmForm laCommunauteDeLAnneauForm = new FilmForm();
            laCommunauteDeLAnneauForm.setTitle("La communauté de l'anneau");
            laCommunauteDeLAnneauForm.setDuration(178);
            laCommunauteDeLAnneauForm.setDirectorId(peterJackson.getId());
            Film laCommunauteDeLAnneau = filmMapper.convertFilmFormToFilm(laCommunauteDeLAnneauForm);
            // Film laCommunauteDeLAnneau = FilmMapper.convertFilmDTOToFilm(myFilmsService.createFilm(laCommunauteDeLAnneauForm));

            FilmForm lesDeuxToursForm = new FilmForm();
            lesDeuxToursForm.setTitle("Les deux tours");
            lesDeuxToursForm.setDuration(179);
            lesDeuxToursForm.setDirectorId(peterJackson.getId());
            Film lesDeuxTours = filmMapper.convertFilmFormToFilm(lesDeuxToursForm);
            // Film lesDeuxTours = FilmMapper.convertFilmDTOToFilm(myFilmsService.createFilm(lesDeuxToursForm));

            FilmForm leRetourDuRoiForm = new FilmForm();
            leRetourDuRoiForm.setTitle("Le retour du roi");
            leRetourDuRoiForm.setDuration(201);
            leRetourDuRoiForm.setDirectorId(peterJackson.getId());
            Film leRetourDuRoi = filmMapper.convertFilmFormToFilm(leRetourDuRoiForm);
            // Film leRetourDuRoi = FilmMapper.convertFilmDTOToFilm(myFilmsService.createFilm(leRetourDuRoiForm));

            // Affectation des films aux directors

            List<Film> peterJacksonFilms = new ArrayList<>();
            peterJacksonFilms.add(laCommunauteDeLAnneau);
            peterJacksonFilms.add(lesDeuxTours);
            peterJacksonFilms.add(leRetourDuRoi);
            peterJackson.setFilmsProduced(peterJacksonFilms);

            List<Film> jamesCameronFilms = new ArrayList<>();
            jamesCameronFilms.add(avatar);
            jamesCameron.setFilmsProduced(jamesCameronFilms);

            // Mise a jour du statut "famous" des Directors
            List<Director> directors = new ArrayList<>();
            directors.add(jamesCameron);
            directors.add(peterJackson);

            List<Director> directorsFamouss = myFilmsService.updateDirectorsFamous(directors);
            System.out.println(directorsFamouss.size());
            for (Director directorFamous : directorsFamouss) {
                System.out.println(directorFamous.getName() + directorFamous.getSurname() + " est célèbre");
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
            System.err.println(e.getMessage());
        }
    }

	/**
	 * Permet de tester la creation des films.
	 */
	public void createFilmTest() {
		try {
			Director director = myFilmsService.findDirectorByNameAndSurname("Cameron", "James");
			DirectorDTO directorDTO = DirectorMapper.convertDirectorToDirectorDTO(director);
			FilmForm titanic = new FilmForm();
			titanic.setTitle("Titanic");
			titanic.setDuration(195);
			titanic.setDirectorId(directorDTO.getId());

            FilmDTO newFilm = myFilmsService.createFilm(titanic);

            System.out.println("Le nouveau film 'Titanic' possede l'id : " + newFilm.getId());

            List<Film> films = myFilmsService.findAll();

            // Attendue : 5
            System.out.println("Combien y a-t-il de films ? " + films.size());

            films.forEach(f -> System.out.println("Le director du film : '" + f.getTitle() + "' est : " + f.getDirector()));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    /**
     * Permet de tester la recuperation d'un film par son identifiant.
     */
    public void findFilmByIdTest() {
        try {
            FilmDTO avatar = filmMapper.convertFilmToFilmDTO(myFilmsService.findFilmById(1));
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
            FilmDTO filmDTO = filmMapper.convertFilmToFilmDTO(myFilmsService.findFilmById(5));
            System.out.println("Le film avec l'identifiant 5 est : " + filmDTO);
            myFilmsService.deleteFilm(5);
            filmDTO = filmMapper.convertFilmToFilmDTO(myFilmsService.findFilmById(5));

            System.out.println("Suppression du film avec l'identifiant 5...");
            System.out.println("Le film avec l'identifiant 5 est : " + filmDTO);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

	/**
	 * Permet de tester la mise a jour du statut famous d'un Director.
	 */
	public void updateDirectorFamous() {
		try {
			DirectorDTO directorDTO = DirectorMapper.convertDirectorToDirectorDTO(myFilmsService.findDirectorByNameAndSurname("Cameron", "James"));
			// Attendue : false
			System.out.println("James Cameron est-il famous ? " + directorDTO.isFamous());

            FilmForm titanic = new FilmForm();
            titanic.setTitle("Titanic");
            titanic.setDuration(195);
            titanic.setDirectorId(directorDTO.getId());

            FilmForm leHobbit = new FilmForm();
            leHobbit.setTitle("Le Hobbit : Un voyage inattendu");
            leHobbit.setDuration(169);
            leHobbit.setDirectorId(directorDTO.getId());

            myFilmsService.createFilm(titanic);
            FilmDTO leHobbitDTO = myFilmsService.createFilm(leHobbit);

			System.out.println("James Cameron a realise deux nouveaux films");
			directorDTO = DirectorMapper.convertDirectorToDirectorDTO(myFilmsService.findDirectorByNameAndSurname("Cameron", "James"));

            // Attendue : true
            System.out.println("James Cameron est-il famous ? " + directorDTO.isFamous());

			myFilmsService.deleteFilm(leHobbitDTO.getId());
			System.out.println("Ce n'est pas James Cameron qui a realise le Hobbit, suppression du film !");
			directorDTO = DirectorMapper.convertDirectorToDirectorDTO(myFilmsService.findDirectorByNameAndSurname("Cameron", "James"));

            // Attendue : false
            System.out.println("James Cameron est-il famous ? " + directorDTO.isFamous());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
