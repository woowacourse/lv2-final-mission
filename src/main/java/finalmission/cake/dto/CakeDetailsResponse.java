package finalmission.cake.dto;

import finalmission.cake.model.Flavor;
import finalmission.cake.model.Size;
import java.util.List;

public record CakeDetailsResponse(
        List<FlavorDetails> flavors,
        List<SizeDetails> size
) {

    public static CakeDetailsResponse of(List<Flavor> flavors, List<Size> sizes) {
        List<FlavorDetails> flavorDetails = flavors.stream()
                .map(FlavorDetails::from)
                .toList();
        List<SizeDetails> sizeDetails = sizes.stream()
                .map(SizeDetails::from)
                .toList();
        return new CakeDetailsResponse(flavorDetails, sizeDetails);
    }

    record FlavorDetails(
            Long id,
            String name,
            String description,
            Integer additionalPrice
    ) {

        public static FlavorDetails from(Flavor flavor) {
            return new FlavorDetails(flavor.getId(), flavor.getName(), flavor.getDescription(),
                    flavor.getAdditionalPrice());
        }
    }

    record SizeDetails(
            Long id,
            Integer dimension,
            String description,
            Integer additionalPrice
    ) {

        public static SizeDetails from(Size size) {
            return new SizeDetails(size.getId(), size.getDimension(), size.getDescription(), size.getAdditionalPrice());
        }
    }
}
