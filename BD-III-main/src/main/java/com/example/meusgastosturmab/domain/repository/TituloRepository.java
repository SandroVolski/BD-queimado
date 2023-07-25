import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.meusgastosturmab.domain.model.Titulo;
import com.example.meusgastosturmab.domain.model.Usuario;

public interface TituloRepository extends JpaRepository{
    List <Titulo> findByUsuario(Usuario usuario);
}