package it.nicolalopatriello.thesis.core.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.nicolalopatriello.thesis.common.dto.Dependency;
import it.nicolalopatriello.thesis.core.dto.DependencyVulnerability;
import it.nicolalopatriello.thesis.core.dto.dependecy.PythonVulnerability;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Log4j
@Service
public class PythonVulnerabilitiesServiceImpl implements PythonVulnerabilitiesService {

    @Value("${app.vulnerabilities.python.dir}")
    private String pythonVulnerabilitiesDirectory;

    @Value("${app.vulnerabilities.python.jsonfile:/data/insecure_full.json}")
    private String jsonPath;

    @Override
    public List<DependencyVulnerability> find(List<Dependency> dependencies) {
        List<DependencyVulnerability> list = Lists.newLinkedList();
        File f = new File(pythonVulnerabilitiesDirectory, jsonPath);
        if (!f.exists()) {
            return list;
        }
        Optional<PythonVulnerability> vulnerabilitiesOpt = PythonVulnerability.from(f);
        if (!vulnerabilitiesOpt.isPresent()) {
            return list;
        }
        PythonVulnerability vulnerabilities = vulnerabilitiesOpt.get();
        for (Dependency dependency : dependencies) {
            log.debug("Finding vulnerabilities for dependency: " + dependency.getName() + " v: " + dependency.getVersion());
            Set<String> set = Sets.newHashSet();
            List<PythonVulnerability.Item> obj = vulnerabilities.get(dependency.getName());
            if (obj != null) {
                log.debug("[Found!] vulnerabilities for dependency: " + dependency.getName());
                for (PythonVulnerability.Item item : obj) {
                    if (item.match(dependency.getVersion())) {
                        System.err.println(item.getCve());
                        if (item.getCve() != null)
                            set.add(item.getCve());
                    }
                }
                if (!set.isEmpty()) {
                    list.add(new DependencyVulnerability(dependency.getName(), set));
                }
            }
        }
        return list;
    }
}
