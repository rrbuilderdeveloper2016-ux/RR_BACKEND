package com.rrbuilders.backend.config;

import com.rrbuilders.backend.entity.Location;
import com.rrbuilders.backend.entity.Project;
import com.rrbuilders.backend.entity.Property;
import com.rrbuilders.backend.repository.LocationRepository;
import com.rrbuilders.backend.repository.ProjectRepository;
import com.rrbuilders.backend.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final PropertyRepository propertyRepository;
    private final ProjectRepository projectRepository;
    private final LocationRepository locationRepository;
    private final com.rrbuilders.backend.repository.AdminUserRepository adminUserRepository;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        seedAdminUser();
        seedLocations();
        seedProjects();
        seedProperties();
    }

    private void seedAdminUser() {
        if (adminUserRepository.count() == 0) {
            log.info("Seeding initial super admin into RRBuilder_DB...");
            com.rrbuilders.backend.entity.AdminUser admin = com.rrbuilders.backend.entity.AdminUser.builder()
                    .username("admin")
                    .email("admin@rrbuilderindore.com")
                    .password(passwordEncoder.encode("RR@Indore2026"))
                    .fullName("RR Builder Super Admin")
                    .role("ROLE_ADMIN")
                    .enabled(true)
                    .build();
            adminUserRepository.save(admin);
            log.info("Default Admin seeded: username 'admin', email 'admin@rrbuilderindore.com'");
        }
    }

    private void seedLocations() {
        if (locationRepository.count() == 0) {
            log.info("Seeding initial Indore real estate locations into RRBuilder_DB...");
            List<Location> locations = List.of(
                    Location.builder().name("Super Corridor").slug("super-corridor").propertyCountText("12 Properties").featured(true).description("Emerging tech and commercial hub near TCS and Infosys campuses.").build(),
                    Location.builder().name("Vijay Nagar").slug("vijay-nagar").propertyCountText("18 Properties").featured(true).description("Indore's bustling premium commercial and shopping center.").build(),
                    Location.builder().name("Bicholi Mardana").slug("bicholi-mardana").propertyCountText("9 Properties").featured(true).description("Upscale residential belt known for luxury villas and greenery.").build(),
                    Location.builder().name("Rau").slug("rau").propertyCountText("14 Properties").featured(false).description("Fast growing education and industrial bypass sector.").build(),
                    Location.builder().name("Bhawarkuan").slug("bhawarkuan").propertyCountText("11 Properties").featured(false).description("Central student and vibrant mixed-use locality.").build(),
                    Location.builder().name("AB Road").slug("ab-road").propertyCountText("16 Properties").featured(true).description("The arterial highway spine with landmark corporate offices.").build(),
                    Location.builder().name("Scheme No. 140").slug("scheme-no-140").propertyCountText("8 Properties").featured(false).description("Prime master-planned development area on Eastern Ring Road.").build(),
                    Location.builder().name("Nipania").slug("nipania").propertyCountText("13 Properties").featured(true).description("High-demand residential enclave with peaceful lifestyle.").build()
            );
            locationRepository.saveAll(locations);
            log.info("Seeded {} locations.", locations.size());
        }
    }

    private void seedProjects() {
        if (projectRepository.count() == 0) {
            log.info("Seeding initial projects into RRBuilder_DB...");
            List<String> amenities = List.of(
                    "Landscaped gardens", "24×7 security", "Covered parking",
                    "Children's play area", "Power backup", "Community spaces"
            );

            List<Project> projects = List.of(
                    Project.builder()
                            .id("rr-shree-residency")
                            .name("RR Shree Residency")
                            .location("Super Corridor, Indore")
                            .type("2 & 3 BHK Apartments")
                            .category("Residential")
                            .status("Ongoing")
                            .price("₹42 Lakh onwards")
                            .description("Contemporary homes with thoughtful amenities near Indore's fastest-growing corridor.")
                            .crop("crop-project-1")
                            .amenities(amenities)
                            .build(),
                    Project.builder()
                            .id("rr-green-villas")
                            .name("RR Green Villas")
                            .location("Bicholi Mardana, Indore")
                            .type("3 & 4 BHK Villas")
                            .category("Residential")
                            .status("Upcoming")
                            .price("₹1.25 Cr onwards")
                            .description("Private villas shaped around landscaped greens, natural light and family living.")
                            .crop("crop-project-2")
                            .amenities(amenities)
                            .build(),
                    Project.builder()
                            .id("rr-business-hub")
                            .name("RR Business Hub")
                            .location("Vijay Nagar, Indore")
                            .type("Shops & Office Spaces")
                            .category("Commercial")
                            .status("Ongoing")
                            .price("₹55 Lakh onwards")
                            .description("A high-visibility business address with efficient floor plates and modern facilities.")
                            .crop("crop-project-3")
                            .amenities(amenities)
                            .build(),
                    Project.builder()
                            .id("rr-premium-plots")
                            .name("RR Premium Plots")
                            .location("Rau, Indore")
                            .type("Residential Plots")
                            .category("Plots")
                            .status("Ongoing")
                            .price("₹28 Lakh onwards")
                            .description("Well-planned residential plots with strong connectivity and essential infrastructure.")
                            .crop("crop-project-1")
                            .amenities(amenities)
                            .build(),
                    Project.builder()
                            .id("rr-orchid-heights")
                            .name("RR Orchid Heights")
                            .location("Nipania, Indore")
                            .type("2 & 3 BHK Apartments")
                            .category("Residential")
                            .status("Completed")
                            .price("₹48 Lakh onwards")
                            .description("Move-in-ready apartments combining modern convenience with a calm neighbourhood.")
                            .crop("crop-project-2")
                            .amenities(amenities)
                            .build(),
                    Project.builder()
                            .id("rr-corporate-square")
                            .name("RR Corporate Square")
                            .location("AB Road, Indore")
                            .type("Premium Offices")
                            .category("Commercial")
                            .status("Completed")
                            .price("₹62 Lakh onwards")
                            .description("Professional workspaces designed for ambitious businesses on a landmark corridor.")
                            .crop("crop-project-3")
                            .amenities(amenities)
                            .build()
            );
            projectRepository.saveAll(projects);
            log.info("Seeded {} projects.", projects.size());
        }
    }

    private void seedProperties() {
        if (propertyRepository.count() == 0) {
            log.info("Seeding initial properties into RRBuilder_DB...");
            List<String> amenities = List.of(
                    "Landscaped gardens", "24×7 security", "Covered parking",
                    "Children's play area", "Power backup", "Community spaces"
            );

            List<Property> properties = List.of(
                    Property.builder()
                            .id("rr-shree-residency")
                            .name("RR Shree Residency")
                            .location("Super Corridor, Indore")
                            .locationSlug("super-corridor")
                            .bhk("2 & 3 BHK")
                            .area("1200–1650 sq.ft.")
                            .price("₹42 Lakh onwards")
                            .type("Apartment")
                            .status("Ongoing")
                            .featured(true)
                            .crop("crop-project-1")
                            .bedrooms("2 & 3 BHK")
                            .bathrooms("2–3")
                            .parking("Covered")
                            .possession("December 2026")
                            .description("Contemporary homes with thoughtful amenities near Indore's fastest-growing corridor.")
                            .amenities(amenities)
                            .build(),
                    Property.builder()
                            .id("rr-green-villas")
                            .name("RR Green Villas")
                            .location("Bicholi Mardana, Indore")
                            .locationSlug("bicholi-mardana")
                            .bhk("3 & 4 BHK")
                            .area("2200–2800 sq.ft.")
                            .price("₹1.25 Cr onwards")
                            .type("Villa")
                            .status("Upcoming")
                            .featured(true)
                            .crop("crop-project-2")
                            .bedrooms("3 & 4 BHK")
                            .bathrooms("4")
                            .parking("2 Covered")
                            .possession("Mid 2027")
                            .description("Private villas shaped around landscaped greens, natural light and family living.")
                            .amenities(amenities)
                            .build(),
                    Property.builder()
                            .id("rr-business-hub")
                            .name("RR Business Hub")
                            .location("Vijay Nagar, Indore")
                            .locationSlug("vijay-nagar")
                            .bhk("Shops & Offices")
                            .area("650–1800 sq.ft.")
                            .price("₹55 Lakh onwards")
                            .type("Commercial")
                            .status("Ongoing")
                            .featured(false)
                            .crop("crop-project-3")
                            .bedrooms("N/A")
                            .bathrooms("Executive Attached")
                            .parking("Ample Visitor & Reserved")
                            .possession("Ready to Fit-out")
                            .description("A high-visibility business address with efficient floor plates and modern facilities.")
                            .amenities(amenities)
                            .build(),
                    Property.builder()
                            .id("rr-orchid-heights")
                            .name("RR Orchid Heights")
                            .location("Nipania, Indore")
                            .locationSlug("nipania")
                            .bhk("2 & 3 BHK")
                            .area("1100–1520 sq.ft.")
                            .price("₹48 Lakh onwards")
                            .type("Apartment")
                            .status("Ready to Move")
                            .featured(false)
                            .crop("crop-project-2")
                            .bedrooms("2 & 3 BHK")
                            .bathrooms("2")
                            .parking("Covered")
                            .possession("Immediate")
                            .description("Move-in-ready apartments combining modern convenience with a calm neighbourhood.")
                            .amenities(amenities)
                            .build(),
                    Property.builder()
                            .id("rr-premium-plots")
                            .name("RR Premium Plots")
                            .location("Rau, Indore")
                            .locationSlug("rau")
                            .bhk("Residential Plots")
                            .area("1000–2400 sq.ft.")
                            .price("₹28 Lakh onwards")
                            .type("Plot")
                            .status("Ongoing")
                            .featured(false)
                            .crop("crop-project-1")
                            .bedrooms("Open Plot")
                            .bathrooms("N/A")
                            .parking("Roadside & In-plot")
                            .possession("Immediate Registry")
                            .description("Well-planned residential plots with strong connectivity and essential infrastructure.")
                            .amenities(amenities)
                            .build(),
                    Property.builder()
                            .id("rr-corporate-square")
                            .name("RR Corporate Square")
                            .location("AB Road, Indore")
                            .locationSlug("ab-road")
                            .bhk("Premium Offices")
                            .area("500–1450 sq.ft.")
                            .price("₹62 Lakh onwards")
                            .type("Office")
                            .status("Completed")
                            .featured(false)
                            .crop("crop-project-3")
                            .bedrooms("N/A")
                            .bathrooms("Floor Restrooms")
                            .parking("Basement Multi-level")
                            .possession("Immediate")
                            .description("Professional workspaces designed for ambitious businesses on a landmark corridor.")
                            .amenities(amenities)
                            .build()
            );
            propertyRepository.saveAll(properties);
            log.info("Seeded {} properties.", properties.size());
        }
    }
}
