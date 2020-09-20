package ar.com.pooflix.api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.ReplaceRootOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import ar.com.pooflix.api.entities.Actor;
import ar.com.pooflix.api.entities.Capitulo;
import ar.com.pooflix.api.entities.Director;
import ar.com.pooflix.api.entities.Genero;
import ar.com.pooflix.api.entities.Serie;
import ar.com.pooflix.api.entities.Temporada;
import ar.com.pooflix.api.models.request.SerieRequest;
import ar.com.pooflix.api.repos.PeliculaRepository;
import ar.com.pooflix.api.repos.SerieRepository;


@Service
public class SerieService {

    @Autowired
    SerieRepository serieRepo;

    @Autowired
    MongoTemplate mongoTemplate;

    public boolean crearNuevaSerie(Serie serie) {
        serieRepo.save(serie);
        return true;
    }

    public Serie crearSerie(String titulo, List<Genero> generos, Director director, List<Actor> actores,
            List<Temporada> temporadas) {
        if (existeSerie(titulo)) {
            return null;
        } else {
            Serie serie = new Serie();
            serie.setTitulo(titulo);
            serie.setGeneros(generos);
            serie.setDirector(director);
            serie.setActores(actores);
            serie.setTemporadas(temporadas);

            boolean serieCreada = crearNuevaSerie(serie);
            if (serieCreada)
                return serie;
            else
                return null;
        }

    }

    boolean existeSerie(String titulo) {
        Serie serie = serieRepo.findByTitulo(titulo);
        if (serie != null)
            return true;
        else
            return false;
    }

    public Serie buscarPorTitulo(String titulo) {
        Serie serie = serieRepo.findByTitulo(titulo);
        return serie;
    }

    public List<Serie> obtenerSerie() {
        return serieRepo.findAll();
    }

    public Serie buscarPorId(ObjectId idSerie) {
        Optional<Serie> opSerie = serieRepo.findById(idSerie);

        if (opSerie.isPresent())
            return opSerie.get();
        else
            return null;
    }

    public boolean tieneGenero(Serie serie, String genero) {
        return serie.getGeneros().stream().filter(g -> g.getNombreGenero().equals(genero)).count() > 0;
    }

    public List<Serie> buscarPorGenero(String genero) {

        return obtenerSerie().stream().filter(p -> this.tieneGenero(p, genero)).collect(Collectors.toList());
        /*
         * return obtenerPeliculas().stream() .filter(p-> p.getGeneros().stream()
         * .filter(g -> g.getNombreGenero().equals(genero)).count() >
         * 0).collect(Collectors.toList());
         */
    }

    public List<Temporada> traerTemporadasPorSerieId(ObjectId id) {

        return buscarPorId(id).getTemporadas();
    }

    public Serie modificarPelicula(Serie serie, SerieRequest modifSerie) {

        serie.setTitulo(modifSerie.titulo);
        serie.setGeneros(modifSerie.generos);
        serie.setActores(modifSerie.actores);
        serie.setDirector(modifSerie.director);
        serie.setTemporadas(modifSerie.temporadas);

        boolean serieModificada = this.crearNuevaSerie(serie);
        if (serieModificada) {
            return serie;
        } else {
            return null;
        }

    }

    public Serie calificarSerie(Serie serie, double calificacion) {

        serie.setCalificacion(calificacion);
        boolean serieCalificada = this.crearNuevaSerie(serie);
        if (serieCalificada) {
            return serie;

        } else {
            return null;
        }

    }

    public List<Serie> obtenerSeriesByActor(ObjectId actorId) {

        //Trae todas las Series.
        /*List<Serie> series = serieRepo.findAll().stream()
            .filter(s -> s.getActores().stream()
            .filter(a-> a.get_id().equals(actorId.toHexString())).count() > 0)
            .collect(Collectors.toList());*/
        
        //Busco por un metodo del repo
        //List<Serie> series = serieRepo.findByActores__id(actorId);

        //En este caso trae solo info del Actor.
        List<Serie> series = serieRepo.findSeriesByActores_IdSoloInfoActor(actorId);
        //List<Serie> series = serieRepo.findSeriesByActor_IdEntero(actorId);
        return series;
    }

    public List<Capitulo> obtenerEpisodiosSerie(ObjectId serieId) {

        return this.buscarPorId(serieId).getTemporadas().stream().map(t -> t.getCapitulos()).flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public Capitulo obtenerEpisodioPorNroEpisodio(ObjectId serieId, Integer temporadaNro, Integer episodioNumero) {

        return obtenerEpisodioPorNroEpisodioVersionPerfomante(serieId, temporadaNro, episodioNumero);
    }

    public Capitulo obtenerEpisodioPorNroEpisodioVersionPesada(ObjectId serieId, Integer temporadaNro,
            Integer episodioNumero) {

        Capitulo episodio = serieRepo.findBy_id(serieId).getTemporadas().stream()
                .filter(t -> t.getNumero() == temporadaNro).findFirst().get()
                .getCapitulos().stream()
                .filter(e -> e.getNumero() == episodioNumero).findFirst().get();

        return episodio;
    }

    public Capitulo obtenerEpisodioPorNroEpisodioVersionPerfomante(ObjectId serieId, Integer temporadaNro,
            Integer episodioNumero) {

        /*
         * 
         * Este es el PIPELINE EN JS
        [
            {
            '$match': {
                '_id': new ObjectId('5f652e4f857bbf55ecbccfe6')
                }
            },
            {
            '$project': {
                'temporadas': 1
                }
            },
            {
                '$unwind': {
                    'path': '$temporadas'
                }
            },
            {
            '$match': {
                'temporadas.numero': 5
                }
            },
            {
            '$replaceRoot': {
                'newRoot': '$temporadas'
                }
            }, 
            {
            '$unwind': {
                'path': '$episodios'
                }
            }, 
            {
            '$replaceRoot': {
                'newRoot': '$episodios'
                }
            }, 
            {
            '$match': {
                'numero': 7
                }
            }
        ]
         * 
         * 
         */

        // Pipelines
        // Stage1: filtro por la serie especifica
        MatchOperation matchSerieStage = Aggregation.match(new Criteria("_id").is(serieId));

        // Stage2: Proyectar solo temporadas
        ProjectionOperation projectTemporadaStage = Aggregation.project("temporadas");

        // Stage3: Unwind temporada (las separa)
        UnwindOperation unwindTemporadaStage = Aggregation.unwind("temporadas");

        // Stage4: Match temporada(filtra de las separadas)
        MatchOperation matchTemporadaStage = Aggregation.match(new Criteria("temporadas.numero").is(temporadaNro));

        // Stage5: Reemplaza la raiz para que tome ahora el de documentos
        ReplaceRootOperation replaceRootTemporadaStage = Aggregation.replaceRoot("temporadas");

        // Stage6: Unwind episodios (separa los episodios)
        UnwindOperation unwindEpisodiosStage = Aggregation.unwind("episodios");

        // Stage7: Reemplaza la raiz para que tome ahora el de documentos
        ReplaceRootOperation replaceRootEpisodiosStage = Aggregation.replaceRoot("episodios");
        // Stage8: Match episodio(filtra de los separados)
        MatchOperation matchEpisodioStage = Aggregation.match(new Criteria("numero").is(episodioNumero));

        Aggregation aggregation = Aggregation.newAggregation(matchSerieStage, projectTemporadaStage,
                unwindTemporadaStage, matchTemporadaStage, replaceRootTemporadaStage, unwindEpisodiosStage,
                replaceRootEpisodiosStage, matchEpisodioStage);

        AggregationResults<Capitulo> result = mongoTemplate.aggregate(aggregation, "series", Capitulo.class);

        Capitulo episodio = result.getUniqueMappedResult();
        return episodio;

    }

}