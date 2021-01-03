package pl.teamsix.competenceproject.domain.service.hotspot;

import pl.teamsix.competenceproject.domain.entity.Hotspot;
import pl.teamsix.competenceproject.domain.service.BaseService;
import pl.teamsix.competenceproject.domain.service.DeleteService;
import pl.teamsix.competenceproject.domain.service.UpdateService;

public interface HotspotService
        extends BaseService<Hotspot>, DeleteService<Hotspot>, UpdateService<Hotspot> {

}
