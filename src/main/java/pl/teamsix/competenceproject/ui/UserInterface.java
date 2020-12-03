package pl.teamsix.competenceproject.ui;

import org.springframework.stereotype.Component;
import pl.teamsix.competenceproject.domain.service.hotspot.HotspotService;
import pl.teamsix.competenceproject.domain.service.trace.TraceService;
import pl.teamsix.competenceproject.domain.service.user.UserService;
import pl.teamsix.competenceproject.logic.generation.HotspotsGenerator;
import pl.teamsix.competenceproject.logic.generation.TracesGenerator;
import pl.teamsix.competenceproject.logic.generation.UsersGenerator;

@Component
public class UserInterface {

    /*------------------------ FIELDS REGION ------------------------*/
    private final UserService userService;
    private final HotspotService hotspotService;
    private final TraceService traceService;

    private final UsersGenerator usersGenerator;
    private final HotspotsGenerator hotspotsGenerator;
    private final TracesGenerator tracesGenerator;

    // TODO PROBABLY HERE WILL BE FIELD RESPONSIBLE FOR DATA ANALYSIS

    /*------------------------ METHODS REGION ------------------------*/
    public UserInterface(UserService userService, HotspotService hotspotService,
                         TraceService traceService, UsersGenerator usersGenerator,
                         HotspotsGenerator hotspotsGenerator, TracesGenerator tracesGenerator) {
        this.userService = userService;
        this.hotspotService = hotspotService;
        this.traceService = traceService;
        this.usersGenerator = usersGenerator;
        this.hotspotsGenerator = hotspotsGenerator;
        this.tracesGenerator = tracesGenerator;
    }

    public void initialize() {
        // TODO HERE IMPLEMENT ALL CMD STUFF - FIELD SHOWN ABOVE ARE INJECTED USING IOC - SO THEY
        //  ARE INITIALIZED IN CTOR AUTOMATICALLY
    }
}
