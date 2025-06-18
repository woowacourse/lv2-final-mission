package finalmission.application;

import finalmission.domain.design.Design;
import finalmission.domain.design.DesignRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DesignService {

    private final DesignRepository designRepository;

    public DesignService(final DesignRepository designRepository) {
        this.designRepository = designRepository;
    }

    public Design createDesign(final String name, final int price, final int turnaroundTime) {
        Design design = Design.register(name, price, turnaroundTime);
        return designRepository.save(design);
    }

    public List<Design> getAllDesigns() {
        return designRepository.findAll();
    }

    public void removeDesign(final Long designId) {
        designRepository.deleteById(designId);
    }
}
