package com.pisomatch.config;

import com.pisomatch.domain.EstadoLike;
import com.pisomatch.domain.Like;
import com.pisomatch.domain.Mensaje;
import com.pisomatch.domain.PerfilConvivencia;
import com.pisomatch.domain.PreferenciasBusqueda;
import com.pisomatch.domain.Usuario;
import com.pisomatch.domain.Vivienda;
import com.pisomatch.repository.LikeRepository;
import com.pisomatch.repository.MensajeRepository;
import com.pisomatch.repository.PerfilConvivenciaRepository;
import com.pisomatch.repository.PreferenciasBusquedaRepository;
import com.pisomatch.repository.UsuarioRepository;
import com.pisomatch.repository.ViviendaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

/**
 * Carga datos de ejemplo al arrancar (usuarios con su perfil y preferencias,
 * algunas viviendas y un match con un par de mensajes) para poder probar la app
 * sin tener que crear datos a mano. Solo se ejecuta si no hay usuarios.
 */
@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seed(UsuarioRepository usuarios,
                           PerfilConvivenciaRepository perfiles,
                           PreferenciasBusquedaRepository preferencias,
                           ViviendaRepository viviendas,
                           LikeRepository likes,
                           MensajeRepository mensajes) {
        return args -> {
            if (usuarios.count() > 0) {
                return;
            }

            Usuario lucia = usuarios.save(new Usuario("Lucía", 27,
                    "https://i.pravatar.cc/500?img=5", "Diseñadora UX",
                    "Ordenada y tranquila, busco piso luminoso en el centro."));
            Usuario carlos = usuarios.save(new Usuario("Carlos", 30,
                    "https://i.pravatar.cc/500?img=12", "Desarrollador",
                    "Teletrabajo, me gusta cocinar y los planes de finde."));
            Usuario marta = usuarios.save(new Usuario("Marta", 24,
                    "https://i.pravatar.cc/500?img=20", "Estudiante de máster",
                    "Nocturna, con una gata muy sociable."));
            Usuario javier = usuarios.save(new Usuario("Javier", 33,
                    "https://i.pravatar.cc/500?img=33", "Músico",
                    "Busco gente relajada y abierta."));
            Usuario sofia = usuarios.save(new Usuario("Sofía", 28,
                    "https://i.pravatar.cc/500?img=16", "Ingeniera",
                    "Deportista y muy ordenada, madrugo para entrenar."));
            Usuario diego = usuarios.save(new Usuario("Diego", 26,
                    "https://i.pravatar.cc/500?img=51", "Cocinero",
                    "Horarios cambiantes, tengo un perro pequeño y tranquilo."));
            Usuario ana = usuarios.save(new Usuario("Ana", 31,
                    "https://i.pravatar.cc/500?img=45", "Profesora de primaria",
                    "Tranquila y previsora, busco piso cerca del centro."));
            Usuario pablo = usuarios.save(new Usuario("Pablo", 29,
                    "https://i.pravatar.cc/500?img=60", "Fisioterapeuta",
                    "Madrugo, entreno y cocino sano. Busco piso con terraza."));
            Usuario elena = usuarios.save(new Usuario("Elena", 35,
                    "https://i.pravatar.cc/500?img=9", "Arquitecta",
                    "Independiente y poco casera, pero muy respetuosa con el espacio."));
            Usuario marcos = usuarios.save(new Usuario("Marcos", 22,
                    "https://i.pravatar.cc/500?img=15", "Estudiante de ingeniería",
                    "Buscando piso para el curso, sociable y sin dramas."));
            Usuario nuria = usuarios.save(new Usuario("Nuria", 26,
                    "https://i.pravatar.cc/500?img=25", "Community manager",
                    "Teletrabajo con cascos y salgo a correr por las tardes."));
            Usuario ivan = usuarios.save(new Usuario("Iván", 32,
                    "https://i.pravatar.cc/500?img=13", "Analista de datos",
                    "Ordenado, cocino de batch y no fumo. Busco piso bien comunicado."));

            perfiles.saveAll(List.of(
                    new PerfilConvivencia(lucia.getId(), 550, "Madrid", LocalDate.of(2026, 11, 1),
                            "No fumo, madrugadora", "Busco ambiente tranquilo y limpio"),
                    new PerfilConvivencia(carlos.getId(), 500, "Madrid", LocalDate.of(2026, 11, 15),
                            "No fumo, horario flexible", "Convivencia sociable pero respetuosa"),
                    new PerfilConvivencia(marta.getId(), 450, "Madrid", LocalDate.of(2026, 12, 1),
                            "Nocturna, tengo gato", "Me da igual el desorden leve"),
                    new PerfilConvivencia(javier.getId(), 400, "Madrid", LocalDate.of(2026, 11, 20),
                            "Fumo en la terraza, nocturno", "Busco gente abierta"),
                    new PerfilConvivencia(sofia.getId(), 650, "Barcelona", LocalDate.of(2026, 11, 10),
                            "No fumo, muy ordenada", "Piso limpio y silencioso"),
                    new PerfilConvivencia(diego.getId(), 520, "Madrid", LocalDate.of(2026, 12, 5),
                            "Tengo perro, horario flexible", "Acepto mascotas"),
                    new PerfilConvivencia(ana.getId(), 600, "Valencia", LocalDate.of(2026, 11, 5),
                            "No fumo, horario de profe", "Busco calma y buen ambiente"),
                    new PerfilConvivencia(pablo.getId(), 580, "Valencia", LocalDate.of(2026, 12, 15),
                            "No fumo, deportista", "Me gusta tener la casa ordenada"),
                    new PerfilConvivencia(elena.getId(), 700, "Sevilla", LocalDate.of(2027, 1, 10),
                            "No fumo, viajo a menudo", "Prefiero independencia y respeto"),
                    new PerfilConvivencia(marcos.getId(), 420, "Madrid", LocalDate.of(2026, 9, 1),
                            "No fumo, horario de estudiante", "Ambiente joven y flexible"),
                    new PerfilConvivencia(nuria.getId(), 620, "Barcelona", LocalDate.of(2026, 11, 25),
                            "No fumo, teletrabajo", "Busco piso silencioso de día"),
                    new PerfilConvivencia(ivan.getId(), 540, "Madrid", LocalDate.of(2026, 10, 15),
                            "No fumo, madrugador", "Orden y respeto con los espacios comunes")
            ));

            preferencias.saveAll(List.of(
                    new PreferenciasBusqueda(lucia.getId(), 400, 650, "Madrid", "Sí", "No fumador", "Tranquilo", "Pocas", "Muy ordenado"),
                    new PreferenciasBusqueda(carlos.getId(), 400, 600, "Madrid", "Sin preferencia", "Sin preferencia", "Indiferente", "Moderadas", "Normal"),
                    new PreferenciasBusqueda(marta.getId(), 350, 550, "Madrid", "Sí", "No fumador", "Tranquilo", "Pocas", "Muy ordenado"),
                    new PreferenciasBusqueda(javier.getId(), 300, 550, "Madrid", "No", "No fumador", "Tranquilo", "Pocas", "Normal"),
                    new PreferenciasBusqueda(sofia.getId(), 500, 750, "Barcelona", "Sin preferencia", "Indiferente", "Sociable", "Frecuentes", "Normal"),
                    new PreferenciasBusqueda(diego.getId(), 420, 650, "Madrid", "Sí", "Sin preferencia", "Indiferente", "Moderadas", "Normal"),
                    new PreferenciasBusqueda(ana.getId(), 500, 700, "Valencia", "Sin preferencia", "No fumador", "Tranquilo", "Pocas", "Muy ordenado"),
                    new PreferenciasBusqueda(pablo.getId(), 480, 650, "Valencia", "Sí", "Indiferente", "Indiferente", "Moderadas", "Normal"),
                    new PreferenciasBusqueda(elena.getId(), 600, 800, "Sevilla", "No", "No fumador", "Tranquilo", "Pocas", "Muy ordenado"),
                    new PreferenciasBusqueda(marcos.getId(), 300, 480, "Madrid", "Sin preferencia", "No fumador", "Tranquilo", "Pocas", "Normal"),
                    new PreferenciasBusqueda(nuria.getId(), 500, 700, "Barcelona", "Sí", "No fumador", "Indiferente", "Moderadas", "Normal"),
                    new PreferenciasBusqueda(ivan.getId(), 450, 600, "Madrid", "Sin preferencia", "Indiferente", "Sociable", "Moderadas", "Normal")
            ));

            viviendas.saveAll(List.of(
                    new Vivienda(carlos.getId(), "Calle Mayor 10, 3ºB", "Centro, Madrid", 1200, 3,
                            List.of("https://picsum.photos/seed/piso1/600/400",
                                    "https://picsum.photos/seed/piso1b/600/400")),
                    new Vivienda(marta.getId(), "Av. de América 45", "Salamanca, Madrid", 1500, 4,
                            List.of("https://picsum.photos/seed/piso2/600/400")),
                    new Vivienda(sofia.getId(), "Carrer de Mallorca 120", "Eixample, Barcelona", 1400, 3,
                            List.of("https://picsum.photos/seed/piso3/600/400")),
                    new Vivienda(ana.getId(), "Carrer de la Pau 8", "Ciutat Vella, Valencia", 1100, 3,
                            List.of("https://picsum.photos/seed/piso4/600/400")),
                    new Vivienda(elena.getId(), "Calle Betis 22", "Triana, Sevilla", 1300, 4,
                            List.of("https://picsum.photos/seed/piso5/600/400",
                                    "https://picsum.photos/seed/piso5b/600/400")),
                    new Vivienda(nuria.getId(), "Carrer de Sants 200", "Sants, Barcelona", 1250, 3,
                            List.of("https://picsum.photos/seed/piso6/600/400")),
                    new Vivienda(ivan.getId(), "Calle de Alcalá 310", "Ciudad Lineal, Madrid", 1150, 2,
                            List.of("https://picsum.photos/seed/piso7/600/400"))
            ));

            // Match de ejemplo entre Lucía y Carlos, con un par de mensajes.
            likes.save(new Like(lucia.getId(), carlos.getId(), EstadoLike.MATCH));
            likes.save(new Like(carlos.getId(), lucia.getId(), EstadoLike.MATCH));
            mensajes.save(new Mensaje(lucia.getId(), carlos.getId(),
                    "¡Hola Carlos! Me encaja mucho tu perfil, ¿seguimos hablando?"));
            mensajes.save(new Mensaje(carlos.getId(), lucia.getId(),
                    "¡Hola Lucía! Claro, ¿te viene bien quedar para ver el piso?"));

            // Segundo match (Marta y Diego) para tener más de una conversación.
            likes.save(new Like(marta.getId(), diego.getId(), EstadoLike.MATCH));
            likes.save(new Like(diego.getId(), marta.getId(), EstadoLike.MATCH));
            mensajes.save(new Mensaje(marta.getId(), diego.getId(),
                    "¡Hola Diego! Tu perro se llevaría bien con mi gata, ¿hablamos?"));
            mensajes.save(new Mensaje(diego.getId(), marta.getId(),
                    "¡Hola Marta! Seguro que sí, ¿te va bien el finde para ver el piso?"));

            // Likes pendientes y descartes para que el mazo de cada usuario ya tenga historial.
            likes.save(new Like(marcos.getId(), lucia.getId(), EstadoLike.LIKE));
            likes.save(new Like(ana.getId(), carlos.getId(), EstadoLike.LIKE));
            likes.save(new Like(pablo.getId(), lucia.getId(), EstadoLike.LIKE));
            likes.save(new Like(ivan.getId(), nuria.getId(), EstadoLike.LIKE));
            likes.save(new Like(lucia.getId(), elena.getId(), EstadoLike.PASS));
            likes.save(new Like(carlos.getId(), sofia.getId(), EstadoLike.PASS));
            likes.save(new Like(sofia.getId(), marta.getId(), EstadoLike.PASS));
        };
    }
}
