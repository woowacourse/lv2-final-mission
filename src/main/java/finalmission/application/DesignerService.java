package finalmission.application;

import finalmission.domain.designer.Designer;
import finalmission.domain.designer.DesignerRepository;
import java.time.DayOfWeek;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DesignerService {

    private final DesignerRepository designerRepository;

    public DesignerService(final DesignerRepository designerRepository) {
        this.designerRepository = designerRepository;
    }

    public Designer createDesigner(final String name, final DayOfWeek offDay) {
        Designer designer = Designer.register(name, offDay);
        return designerRepository.save(designer);
    }

    public List<Designer> getAllDesigners() {
        return designerRepository.findAll();
    }

    public void removeDesigner(final Long designerId) {
        designerRepository.deleteById(designerId);
    }
}
