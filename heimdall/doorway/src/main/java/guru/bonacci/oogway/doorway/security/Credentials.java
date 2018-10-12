package guru.bonacci.oogway.doorway.security;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Credentials {

    private String username;
    private String password;
}


