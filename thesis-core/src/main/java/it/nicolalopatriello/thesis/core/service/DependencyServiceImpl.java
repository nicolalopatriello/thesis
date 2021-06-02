package it.nicolalopatriello.thesis.core.service;

import it.nicolalopatriello.thesis.common.dto.CVEDetails;
import it.nicolalopatriello.thesis.common.dto.DependencyLight;
import it.nicolalopatriello.thesis.common.dto.ProgrammingLanguage;
import it.nicolalopatriello.thesis.core.client.CveDetailsClient;
import it.nicolalopatriello.thesis.core.dto.DependencyVulnerability;
import it.nicolalopatriello.thesis.core.entities.DependencyEntity;
import it.nicolalopatriello.thesis.core.entities.VulnerabilityEntity;
import it.nicolalopatriello.thesis.core.repos.DependencyRepository;
import it.nicolalopatriello.thesis.core.repos.VulnerabilityRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Optional;


@Service
@Log4j
public class DependencyServiceImpl implements DependencyService {

    private final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    CveDetailsClient cveDetailsClient = new CveDetailsClient();

    @Autowired
    DependencyRepository dependencyRepository;

    @Autowired
    VulnerabilityRepository vulnerabilityRepository;

    @Autowired
    private PythonVulnerabilitiesServiceImpl pythonVulnerabilitiesService;

    @Override
    public void save(DependencyEntity d) {
        Optional<DependencyEntity> dep =
                dependencyRepository.findByNameAndVersionAndProgrammingLanguageAndRepositoryId(
                        d.getName(),
                        d.getVersion(),
                        d.getProgrammingLanguage(),
                        d.getRepositoryId()
                );


        dep.ifPresent(dependencyEntity -> d.setId(dependencyEntity.getId()));

        DependencyEntity dependency = dependencyRepository.save(d);

        //update vulnerabilities
        switch (d.getProgrammingLanguage()) {
            case ProgrammingLanguage.PYTHON:
                Optional<DependencyVulnerability> depVulns = pythonVulnerabilitiesService.find(
                        new DependencyLight(d.getName(), d.getVersion(), d.getProgrammingLanguage()));

                if (depVulns.isPresent()) {
                    for (String cve : depVulns.get().getCve()) {
                        VulnerabilityEntity v = new VulnerabilityEntity();
                        try {
                            CVEDetails cveDetails = cveDetailsClient.getCve(cve);
                            if (cveDetails != null) {
                                v.setCvss(cveDetails.getCvss());
                                v.setCvssVector(cveDetails.getCvssVector());
                                v.setDependencyId(dependency.getId());

                                v.setCvePublishedAt(new Timestamp(dateFormat.parse(cveDetails.getPublished()).getTime()));
                                v.setCveModifiedAt(new Timestamp(dateFormat.parse(cveDetails.getModified()).getTime()));
                                v.setCveId(cveDetails.getId());
                                v.setCvePatch(cveDetails.availablePatch());
                                v.setSummary(cveDetails.getSummary());

                                Optional<VulnerabilityEntity> vulnerabilityEntity =
                                        vulnerabilityRepository.findByCveIdAndDependencyId(
                                                v.getCveId(),
                                                v.getDependencyId()
                                        );

                                vulnerabilityEntity.ifPresent(entity -> v.setId(entity.getId()));
                                vulnerabilityRepository.save(v);
                            }
                        } catch (Exception e) {
                            log.error(e.getMessage());
                        }
                    }
                }
            default:
                break;

        }
    }

}

